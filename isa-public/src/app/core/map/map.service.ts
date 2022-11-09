import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { LatLng } from "leaflet";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MapService {
  constructor(private m_Http: HttpClient) { }

  fetchData(latlng: LatLng): Observable<any> {
    return this.m_Http.get(`https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${latlng.lat}&lon=${latlng.lng}`);
  }
}