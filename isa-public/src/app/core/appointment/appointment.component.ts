import { Component } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ActivatedRoute, Router } from "@angular/router";
import { catchError, map, of, switchMap, tap } from "rxjs";
import { AppointmentService } from "./services/appointment.service";

@Component({
  selector: 'appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.scss']
})
export class AppointmentComponent {

  private m_AppointmentId$ = this.m_Route.params.pipe(map(params => params['id']));
  m_FetchData$ = this.m_AppointmentId$.pipe(switchMap(id => {
    return this.m_AppointmentService.fetchAppointment(id).pipe(
      catchError(err => {
        this.m_SnackBar.open(err, 'Close', { duration: 4000 })
        this.m_Router.navigate(['/']);
        return of({});
      }),
      tap(_ => this.m_Loading = false)
    );
  }))

  m_Error$ = this.m_AppointmentService.m_Error$.pipe();
  m_Loading = true;

  constructor(private m_AppointmentService: AppointmentService,
              private m_SnackBar: MatSnackBar,
              private m_Router: Router,
              private m_Route: ActivatedRoute) {}
}