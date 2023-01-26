import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Appointment } from 'src/app/model/appointment.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserAppointmentsService {

  constructor(private http: HttpClient) { }

  getAllAppointmentsByUserId(userId:number|undefined) : Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${environment.apiUrl}/appointment/all-for-user/${userId}`);
  }

  getAllPastAppointmentsByUserId(userId:number|undefined) : Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${environment.apiUrl}/appointment/history/${userId}`);
  }

  cancelAppointment(id: number) {
    return this.http.patch(`${environment.apiUrl}/appointment/cancel/${id}`, {})
  }
}
