import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BranchCenter } from 'src/app/model/branch-center.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ScheduleAppointmentService {

  constructor(private http: HttpClient) { }
  getAvailableBranchCenters(date:string) : Observable<BranchCenter[]> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append("date",date);
    return this.http.get<BranchCenter[]>(`${environment.apiUrl}/branch-center/available-for-appointment-date`,{params: queryParams});
  }
}
