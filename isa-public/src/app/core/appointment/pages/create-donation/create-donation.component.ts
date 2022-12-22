import { Component } from "@angular/core";
import { UntypedFormGroup, UntypedFormControl, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ActivatedRoute, Router } from "@angular/router";
import { switchMap, catchError, tap, Observable, Subject, EMPTY, take } from "rxjs";
import { Appointment } from "src/app/model/appointment.model";
import { AppointmentService, CreateDonationDTO } from "../../services/appointment.service";

@Component({
  templateUrl: './create-donation.component.html',
  styleUrls: ['./create-donation.component.scss']
})
export class CreateDonationComponent { 
  m_Types: string[] = ['A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-'];

  m_Data$: Observable<Appointment | null> = this.m_AppointmentService.m_Data$.pipe(
    tap(d => {
      if(d?.donation != null){
        this.m_SnackBar.open(`Appointment has already been finished.`, 'Close', { duration: 4000 })
        this.m_Router.navigate(['..'], { relativeTo: this.m_Route });
      }
    })
  );

  m_Form = new UntypedFormGroup({
    'bloodType': new UntypedFormControl(null, Validators.required),
    'note': new UntypedFormControl(null),
    'copperSulfate': new UntypedFormControl(null),
    'hemoglobin': new UntypedFormControl(null),
    'normal': new UntypedFormControl(null),
    'low': new UntypedFormControl(null),
    'lungs': new UntypedFormControl(null),
    'heart': new UntypedFormControl(null),
    'tt': new UntypedFormControl(null),
    'tb': new UntypedFormControl(null),
    'bloodAmount': new UntypedFormControl(null, Validators.required),
  })

  constructor(private m_AppointmentService: AppointmentService, 
              private m_Router: Router,
              private m_SnackBar: MatSnackBar,
              private m_Route: ActivatedRoute) {}

  m_Start$ = new Subject<any>().pipe(
    switchMap(_ => this.m_Data$.pipe(
      take(1),
      switchMap((app: Appointment | null) => this.m_AppointmentService.startAppointment(app?.id!).pipe(
        catchError(_ => EMPTY),
        switchMap(_ => this.m_AppointmentService.fetchAppointment(app?.id!).pipe(
          catchError(_ => EMPTY)))
      )),
    ))
  ) as Subject<any>;

  m_Report$ = new Subject<any>().pipe(
    switchMap(_ => this.m_Data$.pipe(
      take(1),
      switchMap((app: Appointment | null) => this.m_AppointmentService.deleteAppointment(app?.id!).pipe(
        catchError(_ => EMPTY),
        tap(_ => {
          this.m_SnackBar.open(`User reported successfully`, 'Close', { duration: 4000 })
          this.m_Router.navigate(['/calendar'])
        })
      ))
    ))
  ) as Subject<any>;

  m_CreateDonation$ = new Subject<any>().pipe(
    switchMap(_ => this.m_Data$.pipe(
      take(1),
      switchMap((app: Appointment | null) => {
        this.m_AppointmentService.clearError();
        if(this.m_Form.invalid) return EMPTY;
        let data: CreateDonationDTO = this.m_Form.getRawValue();
        data.appointmentId = app?.id!;
        return this.m_AppointmentService.createDonation(data).pipe(
          switchMap(_ => this.m_AppointmentService.fetchAppointment(app?.id!).pipe(
            tap(_ => {
              this.m_SnackBar.open(`Donation created successfully`, 'Close', { duration: 4000 })
              this.m_Router.navigate(['..'], { relativeTo: this.m_Route })
            }),
            catchError(_ => EMPTY))
          ),
          catchError(_ => EMPTY),
        );
      })
    ))
  ) as Subject<any>;

  getAddress(app: Appointment | null) {
    return app?.user.address;
  }
}