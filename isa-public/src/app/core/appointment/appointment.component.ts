import { Component } from "@angular/core";
import { tap } from "rxjs";
import { AppointmentService } from "./services/appointment.service";

@Component({
  selector: 'appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.scss']
})
export class AppointmentComponent {
  m_FetchData$ = this.m_AppointmentService.fetchAppointment(1).pipe(tap(_ => this.m_Loading = false));
  m_Data$ = this.m_AppointmentService.m_Data$;
  m_Error$ = this.m_AppointmentService.m_Error$;
  m_Loading = true;

  constructor(private m_AppointmentService: AppointmentService) {}

}