import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { take } from 'rxjs';
import { Appointment } from 'src/app/model/appointment.model';
import { UserService } from 'src/app/services/user.service';
import { UserAppointmentsService } from '../user-appointments/user-appointments.service';

@Component({
  selector: 'app-visit-history',
  templateUrl: './visit-history.component.html',
  styleUrls: ['./visit-history.component.scss']
})
export class VisitHistoryComponent implements OnInit {

  appointments: Appointment[] = [];
  constructor(private m_UserAppointmentService: UserAppointmentsService, private m_UserService: UserService) { }

  ngOnInit() {
    this.m_UserService.m_Data$.pipe(take(1)).subscribe(data => {
      this.m_UserAppointmentService.getAllPastAppointmentsByUserId(data?.id).pipe(take(1)).subscribe(data => {
        this.appointments = data;
      });
    })
  }

  formatDate(date : Date | null) {
    if (date === null) return ""
    date = new Date(date)
    let day = date.getDate().toString().padStart(2, '0');
    let month = (date.getMonth() + 1).toString().padStart(2, '0');
    let year = date.getFullYear();
    return `${day}.${month}.${year}.`;
  }

  formatTime(date : Date | null) {
    if (date === null) return ""
    date = new Date(date)
    let hours = date.getHours().toString().padStart(2, '0');
    let minutes = date.getMinutes().toString().padStart(2, '0');
    return `${hours}:${minutes}`;
  }

}
