import { Component } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { catchError, exhaustMap, map, Observable, of, Subject, switchMap, tap } from "rxjs";
import { Appointment } from "src/app/model/appointment.model";
import { AppointmentService } from "./services/appointment.service";
import { Address } from "src/app/model/address.model";

@Component({
  selector: 'appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.scss']
})
export class AppointmentComponent {
  private m_AppointmentId = this.m_Route.params.pipe(map(params => params['id']));
  m_FetchData$ = this.m_AppointmentId.pipe(switchMap(id => {
    return this.m_AppointmentService.fetchAppointment(id).pipe(
      catchError(_ => {
        this.m_Router.navigate(['/']);
        return of({});
      }),
      tap(_ => {
        console.log(_);
        this.m_Loading = false
      })
    );
  }))
  m_Data$: Observable<Appointment | null> = this.m_AppointmentService.m_Data$;
  m_Error$ = this.m_AppointmentService.m_Error$;
  m_Loading = true;

  m_Start$ = new Subject<any>().pipe(exhaustMap(_ => 
    this.m_AppointmentId.pipe(switchMap(id => this.m_AppointmentService.startAppointment(id)))
  )) as Subject<any>;

  types: string[] = ['A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-'];

  constructor(private m_AppointmentService: AppointmentService, 
              private m_Route: ActivatedRoute,
              private m_Router: Router) {}

  getAddress(app: Appointment | null) {
    return app?.user.address;
  }
}