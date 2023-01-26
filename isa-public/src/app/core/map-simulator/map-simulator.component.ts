import { Component, OnDestroy, OnInit } from "@angular/core";
import { LatLng, Icon, icon, Marker, tileLayer, marker, Circle, circle } from "leaflet";
import { tap } from "rxjs";
import { MapSimulatorService } from "./services/map-simulator.service";

@Component({
  selector: 'map-sim',
  templateUrl: './map-simulator.component.html',
  styleUrls: ['./map-simulator.component.scss']
})
export class MapSimulatorComponent implements OnInit, OnDestroy {
  m_Height: number = 600;
  m_Location: LatLng | null = new LatLng(45.25636, 19.84731);
  m_Loading: boolean = false;

  m_Center: LatLng = this.m_Location || new LatLng(45.25636, 19.84731);
  m_Icon: Icon = icon({
    iconSize: [25, 41],
    iconAnchor: [13, 41],
    iconUrl: 'assets/marker-icon.png',
    iconRetinaUrl: 'assets/marker-icon-2x.png',
    shadowUrl: 'assets/marker-shadow.png'
  });

  m_Marker: Marker | null = null;
  m_Source: Circle | null = null;
  m_Destination: Circle | null = null;

  m_Options = {
    layers: [
      tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 18, attribution: '...' })
    ],
    zoom: 13,
    center: this.m_Center
  };

  m_Source$ = this.m_MapSimulatorService.m_Source$.pipe(
    tap((loc) => {
      if(loc) {
        this.m_Source = circle(loc, 60, { color: "green" })
        this.m_Source.bindTooltip("Blood bank");
      }
    })
  );
  m_Destination$ = this.m_MapSimulatorService.m_Destination$.pipe(
    tap((loc) => {
      if(loc) {
        this.m_Destination = circle(loc, 60, { color: "red" })
        this.m_Destination.bindTooltip("Blood delivery destination - Hospital");
      }
    })
  );

  m_Current$ = this.m_MapSimulatorService.m_Current$.pipe(
    tap(loc => {
      if (loc) {
        this.m_Location = new LatLng(loc.lat, loc.lng);
        this.m_Marker = marker(this.m_Location, { icon: this.m_Icon });
        this.m_Marker.bindTooltip("Blood current location");
      }
    })
  );

  constructor(private m_MapSimulatorService: MapSimulatorService) { }

  ngOnInit() {
    this.m_MapSimulatorService.connect();
  }

  ngOnDestroy() {
    this.m_MapSimulatorService.disconnect();
  }
}