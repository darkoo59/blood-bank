import { Component, EventEmitter, Output } from "@angular/core";
import { Icon, icon, latLng, LatLng, marker, Marker, tileLayer } from "leaflet";

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html'
})
export class MapComponent {
  @Output() locationChanged: EventEmitter<LatLng> = new EventEmitter();

  m_Center: LatLng = new LatLng(0, 0);
  m_Icon: Icon = icon({
    iconSize: [25, 41],
    iconAnchor: [13, 41],
    iconUrl: 'assets/marker-icon.png',
    iconRetinaUrl: 'assets/marker-icon-2x.png',
    shadowUrl: 'assets/marker-shadow.png'
  });

  m_Marker: Marker = new Marker(new LatLng(0, 0), { icon: this.m_Icon });

  m_Options = {
    layers: [
      tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 18, attribution: '...' })
    ],
    zoom: 5,
    center: latLng(46.879966, -121.726909)
  };

  handleEvent(event: any): void {
    this.m_Center = event.latlng;
    this.m_Marker = marker(this.m_Center, { icon: this.m_Icon });
    this.locationChanged.emit(event.latlng);
  }
}