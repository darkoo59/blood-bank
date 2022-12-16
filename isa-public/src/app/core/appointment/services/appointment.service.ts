import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Appointment } from "src/app/model/appointment.model";
import { GenericDataService } from "src/app/services/generic-data.service";
import { environment } from "src/environments/environment";

@Injectable()
export class AppointmentService extends GenericDataService<Appointment> {

  constructor(private m_Http: HttpClient) { super() }

  fetchAppointment(id: number): Observable<any> {
    return this.addErrorReader(this.m_Http.get(`${environment.apiUrl}/appointment/${id}`));
  }
}