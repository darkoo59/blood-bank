import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, EMPTY, Observable } from 'rxjs';
import { AvailableAppointment } from 'src/app/model/available-appointment';
import { environment } from 'src/environments/environment';
import { WorkingHoursDTO } from './working-hours-dto';

interface AvailableAppointmentDTO {
  id: number;
  start: String;
  end: String;
}

@Injectable({
  providedIn: 'root'
})
export class CalendarService {

  constructor(private http: HttpClient) { }

  getBranchCenterWorkingHours() : Observable<WorkingHoursDTO> {
    return this.http.get<WorkingHoursDTO>(`${environment.apiUrl}/branch-center/workingHours`);
  }

  getAvailableAppointments() : Observable<AvailableAppointmentDTO[]> {
    return this.http.get<AvailableAppointmentDTO[]>(`${environment.apiUrl}/available-appointment`);
  }


}
