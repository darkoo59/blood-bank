import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class BCDashboardService {

  constructor(private m_Http: HttpClient) { }

  fetchBCData(): Observable<any> {
    return this.m_Http.get(`${environment.apiUrl}/bc-admin/bc`);
  }
}