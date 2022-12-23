import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/user.service';
import { UserAppointmentsService } from './user-appointments.service';
import { Appointment } from 'src/app/model/appointment.model';
import { take } from 'rxjs';

@Component({
  selector: 'app-user-appointments',
  templateUrl: './user-appointments.component.html',
  styleUrls: ['./user-appointments.component.scss']
})
export class UserAppointmentsComponent implements OnInit {
  appointments: Appointment[] = [];
  constructor(private m_Router: Router, private m_SnackBar: MatSnackBar, 
    private m_UserAppointmentService: UserAppointmentsService, private m_UserService: UserService) {
  }

  ngOnInit() {
    this.m_UserService.m_Data$.pipe(take(1)).subscribe(data => {
        this.m_UserAppointmentService.getAllAppointmentsByUserId(data?.id).pipe(take(1)).subscribe(data => {
          this.appointments = data;
        });
      })
  }
}
