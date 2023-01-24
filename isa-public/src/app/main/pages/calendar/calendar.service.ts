import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, EMPTY, Observable } from 'rxjs';
import { AvailableAppointment } from 'src/app/model/available-appointment';
import { environment } from 'src/environments/environment';
import { AvailableAppointmentDto } from './dto/available-appointment-dto';
import { CreateAvailableAppointmentDTO } from './dto/create-available-appointment-dto';
import { WorkingHoursDTO } from './dto/working-hours-dto';

@Injectable({
  providedIn: 'root'
})
export class CalendarService {

  constructor(private http: HttpClient) { }

  getBranchCenterWorkingHours() : Observable<WorkingHoursDTO> {
    return this.http.get<WorkingHoursDTO>(`${environment.apiUrl}/branch-center/working-hours`);
  }

  getBranchCenterWorkingDays() : Observable<[]> {
    return this.http.get<[]>(`${environment.apiUrl}/branch-center/working-days`);
  }

  getAvailableAppointments() : Observable<AvailableAppointmentDto[]> {
    return this.http.get<AvailableAppointmentDto[]>(`${environment.apiUrl}/available-appointment`);
  }

  createAvailableAppointment(dto: CreateAvailableAppointmentDTO) {
    return this.http.post(`${environment.apiUrl}/available-appointment`, dto);
  }

  getAppointments(): Observable<any>{
    return this.http.get(`${environment.apiUrl}/appointment`);
  }

}
