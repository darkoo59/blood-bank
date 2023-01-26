import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/user.service';
import { UserAppointmentsService } from './user-appointments.service';
import { Appointment } from 'src/app/model/appointment.model';
import { catchError, findIndex, of, take } from 'rxjs';
import { LoadingService } from 'src/app/services/loading.service';

@Component({
  selector: 'app-user-appointments',
  templateUrl: './user-appointments.component.html',
  styleUrls: ['./user-appointments.component.scss']
})
export class UserAppointmentsComponent implements OnInit {
  appointments: Appointment[] = [];
  constructor(private m_Router: Router, private m_SnackBar: MatSnackBar, 
    private m_UserAppointmentService: UserAppointmentsService, private m_UserService: UserService,
    private m_LoadingService: LoadingService) {
  }

  ngOnInit() {
    this.m_UserService.m_Data$.pipe(take(1)).subscribe(data => {
        this.m_UserAppointmentService.getAllAppointmentsByUserId(data?.id).pipe(take(1)).subscribe(data => {
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

  isCancelButtonActivated(startDate: Date | null) {
    if (startDate === null) return false
    startDate = new Date(startDate)
    let now = new Date();
    let timeDiff = startDate.getTime() - now.getTime();
    let diffDays = Math.floor(timeDiff / (1000 * 3600 * 24));
    if (diffDays >= 1) {
        return true;
    } else {
        return false;
    }
  }

  cancel(id: number) {
    this.m_LoadingService.setLoading = true
    this.m_UserAppointmentService.cancelAppointment(id).pipe(catchError(res => {
      this.m_SnackBar.open(res.error, 'Close', { duration: 5000 })
      this.m_LoadingService.setLoading = false
      return of()
    }))
    .subscribe(_ => {
      this.removeAppointmentWithId(this.appointments, id)
      this.m_SnackBar.open(`Successfully canceled`, 'Close', { duration: 3000 })
      this.m_LoadingService.setLoading = false
    });
  }

  removeAppointmentWithId(array: Appointment[], id: number) {
    let objWithIdIndex = array.findIndex((obj) => obj.id === id);
  
    if (objWithIdIndex > -1) {
      array.splice(objWithIdIndex, 1);
    }
  }
}
