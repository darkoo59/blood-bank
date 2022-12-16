import { Component, EventEmitter, Output, SimpleChanges, OnChanges, Input, NgZone } from "@angular/core";
import { Icon, icon, LatLng, marker, Marker, tileLayer } from "leaflet";
import { Subject, switchMap, tap } from "rxjs";
import { MapService } from "./map.service";

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html'
})
export class MapComponent implements OnChanges {
  @Output() locationLatLng: EventEmitter<LatLng> = new EventEmitter();
  @Output() locationData: EventEmitter<LatLng> = new EventEmitter();

  @Input() m_Location: LatLng = new LatLng(45.25636, 19.84731);
  @Input() m_Editable: boolean = false;
  @Input() m_Height: number = 500;

  m_Center: LatLng = this.m_Location;
  m_Icon: Icon = icon({
    iconSize: [25, 41],
    iconAnchor: [13, 41],
    iconUrl: 'assets/marker-icon.png',
    iconRetinaUrl: 'assets/marker-icon-2x.png',
    shadowUrl: 'assets/marker-shadow.png'
  });

  m_Marker: Marker | null = null;

  m_Options = {
    layers: [
      tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 18, attribution: '...' })
    ],
    zoom: 13,
    center: this.m_Center
  };

  m_Data$: Subject<any> = new Subject<any>().pipe(
    switchMap(latlng => this.m_MapService.fetchData(latlng)),
    tap(data => this.locationData.emit(data))
  ) as Subject<any>;

  constructor(private m_MapService: MapService) { }
  
  ngOnChanges(changes: SimpleChanges) {
    if(changes['m_Location']){
      this.m_Center.lat = this.m_Location.lat;
      this.m_Center.lng = this.m_Location.lng;
      this.m_Marker = marker(this.m_Location, { icon: this.m_Icon });
    } 
  }

  handleEvent(event: any): void {
    if (!this.m_Editable) return;
    this.m_Center = event.latlng;
    this.m_Marker = marker(this.m_Center, { icon: this.m_Icon });
    this.locationLatLng.emit(event.latlng);
    this.m_Data$.next(event.latlng);
  }
}