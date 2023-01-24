import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, EMPTY, Observable } from 'rxjs';
import { BranchCenter } from 'src/app/model/branch-center.model';
import { environment } from 'src/environments/environment';
import { UserScheduleAppointmentDTO } from './dto/user-schedule-appointment-dto';

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

  getSortedByRatingBranchCenters(centersList: any[], sortBy: string, ascending: boolean) : Observable<BranchCenter[]> {
    return this.http.post<BranchCenter[]>(`${environment.apiUrl}/branch-center/sorted`,{centersList,sortBy, ascending});
  }

  isUserCapableForBloodDonation(userId:number|undefined) : Observable<boolean> {
    return this.http.get<boolean>(`${environment.apiUrl}/appointment/is-capable-for-blood-donation/${userId}`);
  }

  userScheduleAppointment(dto: UserScheduleAppointmentDTO) : Observable<any> {
    return this.http.post(`${environment.apiUrl}/appointment/user-schedule`, dto).pipe(catchError(err => { return EMPTY; }));
  }
}
