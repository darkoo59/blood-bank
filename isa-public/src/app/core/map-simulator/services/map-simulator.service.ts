import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { BehaviorSubject } from "rxjs";
import { LatLng } from "leaflet";

@Injectable()
export class MapSimulatorService {
  private stompClient: any = null;

  m_Current$: BehaviorSubject<LatLng | null> = new BehaviorSubject<LatLng | null>(null);
  m_Source$: BehaviorSubject<LatLng | null> = new BehaviorSubject<LatLng | null>(null);
  m_Destination$: BehaviorSubject<LatLng | null> = new BehaviorSubject<LatLng | null>(null);

  connect(): void {
    const socket = new SockJS(`${environment.apiUrl}/map-sim`);
    this.stompClient = Stomp.over(socket);
    this.stompClient.debug = null
    this.stompClient.connect({}, (_: any) => {
      this.stompClient.subscribe('/start/location', (res: any) => {
        const lm = JSON.parse(res.body);
        console.log(lm);

        this.m_Current$.next(new LatLng(lm.current.lat, lm.current.lng));
        this.m_Source$.next(new LatLng(lm.source.lat, lm.source.lng));
        this.m_Destination$.next(new LatLng(lm.destination.lat, lm.destination.lng));
      }, { id: "123"});
    });
  }
  
  disconnect(): void {
    this.stompClient.unsubscribe("123");
  }
}