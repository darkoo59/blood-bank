import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable, tap } from "rxjs";
import { Appointment } from "src/app/model/appointment.model";
import { GenericDataService } from "src/app/services/generic-data.service";
import { environment } from "src/environments/environment";
import { BloodToNumberPipe } from "../pipes/blood-to-number.pipe";

export interface CreateDonationDTO {
  appointmentId: number;
  bloodType: number;
  note: string;
  copperSulfate: string;
  hemoglobin: string;
  normal: number;
  low: number;
  lungs: string;
  heart: string;
  tt: number;
  tb: number;
  bloodAmount: number;
}

@Injectable()
export class AppointmentService extends GenericDataService<Appointment> {

  constructor(private m_Http: HttpClient, private m_BloodToNumberPipe: BloodToNumberPipe) { super() }

  fetchAppointment(id: number): Observable<any> {
    return this.addErrorReader(this.m_Http.get(`${environment.apiUrl}/appointment/${id}`).pipe(
      map((app: any) => {
        if(app.donation)
          app.donation.bloodType = this.m_BloodToNumberPipe.transform(app.donation.bloodType);
        return app;
      }),
      tap((res: any) => this.setData = res)
    ));
  }

  startAppointment(id: number): Observable<any> {
    return this.addErrorReader(this.m_Http.patch(`${environment.apiUrl}/appointment/start/${id}`, {}));
  }

  createDonation(dto: CreateDonationDTO): Observable<any> {
    return this.addErrorReader(this.m_Http.post(`${environment.apiUrl}/donation`, dto));
  }
}