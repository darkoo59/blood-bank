import { Component } from "@angular/core";
import { UntypedFormControl, UntypedFormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { catchError, EMPTY, exhaustMap, map, Observable, of, Subject, switchMap, tap } from "rxjs";
import { Appointment } from "src/app/model/appointment.model";
import { AppointmentService, CreateDonationDTO } from "./services/appointment.service";

@Component({
  selector: 'appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.scss']
})
export class AppointmentComponent {
  m_Types: string[] = ['A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-'];

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

  m_Form = new UntypedFormGroup({
    'bloodtype': new UntypedFormControl(null, Validators.required),
    'note': new UntypedFormControl(null),
    'copperSulfate': new UntypedFormControl(null),
    'hemoglobin': new UntypedFormControl(null),
    'normal': new UntypedFormControl(null),
    'low': new UntypedFormControl(null),
    'lungs': new UntypedFormControl(null),
    'heart': new UntypedFormControl(null),
    'tt': new UntypedFormControl(null),
    'tb': new UntypedFormControl(null),
    'bloodamount': new UntypedFormControl(null, Validators.required),
  })

  constructor(private m_AppointmentService: AppointmentService, 
              private m_Route: ActivatedRoute,
              private m_Router: Router) {}

  m_Start$: Subject<any> = new Subject<any>().pipe(
    exhaustMap(_ => this.m_AppointmentId.pipe(
      catchError(_ => EMPTY),
      switchMap(id => this.m_AppointmentService.startAppointment(id))
    ))
  ) as Subject<any>;

  m_CreateDonation$: Subject<any> = new Subject<any>().pipe(
    catchError(_ => EMPTY),
    switchMap(_ => {
      return this.m_Data$.pipe(switchMap((app: Appointment | null) => {
        if(!app || this.m_Form.invalid) return EMPTY;
        let data: CreateDonationDTO = this.m_Form.getRawValue();
        data.userId = app.user.id;
        return this.m_AppointmentService.createDonation(data);
      }))
    })
  ) as Subject<any>;

  getAddress(app: Appointment | null) {
    return app?.user.address;
  }
}