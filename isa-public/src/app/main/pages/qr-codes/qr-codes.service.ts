import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class QrCodesService  {

constructor(private m_Http: HttpClient) {  }

  fetchQRCodes(id: number | undefined) : Observable<any> {
    return this.m_Http.get(`${environment.apiUrl}/appointment/qr-codes/${id}`, {responseType: 'arrayBuffer' as 'json'})
  }
}
