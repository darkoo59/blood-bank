import { Component } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Observable, tap } from "rxjs";
import { Appointment } from "src/app/model/appointment.model";
import { AppointmentService } from "../../services/appointment.service";

@Component({
  templateUrl: './appointment-details.component.html',
  styleUrls: ['./appointment-details.component.scss']
})
export class AppointmentDetailsComponent {
  m_Data$: Observable<Appointment | null> = this.m_AppointmentService.m_Data$.pipe(tap(_ => console.log(_)));

  constructor(private m_AppointmentService: AppointmentService,
              private m_Router: Router,
              private m_Route: ActivatedRoute) { }

  donate() {
    this.m_Router.navigate(['donation'], { relativeTo: this.m_Route });
  }

  getAddress(app: Appointment | null) {
    return app?.user.address;
  }
}