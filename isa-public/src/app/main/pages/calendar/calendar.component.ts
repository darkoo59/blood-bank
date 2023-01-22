import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ActionEventArgs, EventSettingsModel, ScheduleComponent, WorkHoursModel } from '@syncfusion/ej2-angular-schedule';
import { catchError, forkJoin, take, tap } from 'rxjs';
import { AvailableAppointment } from 'src/app/model/available-appointment';
import { CalendarService } from './calendar.service';
import { WorkingHoursDTO } from './dto/working-hours-dto';
import { CreateAvailableAppointmentDTO } from './dto/create-available-appointment-dto';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Appointment } from 'src/app/model/appointment.model';
import { LoadingService } from 'src/app/services/loading.service';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss']
})
export class CalendarComponent implements OnInit {

  constructor(private m_CalendarService: CalendarService, 
              private m_SnackBar: MatSnackBar,
              private m_Router: Router,
              private m_LoadingService: LoadingService) { }
  
  @ViewChild("scheduler") public m_Scheduler: ScheduleComponent | undefined;
  workingHours:WorkingHoursDTO = {startTime: '08:00', endTime: '21:00'}
  workingDays:[] = []
  availableAppointments:AvailableAppointment[] = []

  public eventSettings: EventSettingsModel = {}
  public views: Array<string> = ['Day','WorkWeek','Week','Month', 'Year']
  public scheduleHours: WorkHoursModel = {}
  m_IsBCPresent = true;
  private m_AvailableApps$ = this.m_CalendarService.getAvailableAppointments();
  private m_Apps$ = this.m_CalendarService.getAppointments();
  m_FetchAllApps$ = forkJoin([this.m_AvailableApps$, this.m_Apps$]).pipe(
    tap(([available, app]) => this.convertAppointmentDTO(available, app)),
    catchError(err => {
      console.log(err);
      this.m_IsBCPresent = false;
      throw err;
    })
  );

  ngOnInit() {
    this.m_CalendarService.getBranchCenterWorkingHours().pipe(take(1)).subscribe(data => {
      this.setWorkingHours(data)
    });
    this.m_CalendarService.getBranchCenterWorkingDays().pipe(take(1)).subscribe(data => {
      this.setWorkingDays(data)
    });
  }

  convertAppointmentDTO(available: any, apps: Appointment[]) {
    for(let appointment of available){
      this.availableAppointments.push({
        Id: appointment.id, 
        StartTime: new Date(appointment.start), 
        EndTime: new Date(appointment.end), 
        Subject: appointment.title,
        Free: true
      } as AvailableAppointment)
    }
    for(let appointment of apps){
      this.availableAppointments.push({
        Id: appointment.id,
        StartTime: appointment.begin,
        EndTime: appointment.end,
        Subject: appointment.title + " - " + appointment.user.firstname + " " + appointment.user.lastname,
        Free: false,
      } as AvailableAppointment)
    }

    this.eventSettings = {
      dataSource: this.availableAppointments,
      allowEditing: false,
      allowDeleting: false,

      fields: {
        id: 'Id',
        subject: { name: 'Subject' },
        startTime: { name: 'StartTime' },
        endTime: { name: 'EndTime' },
      }
    }
  }

  appointmentSelected(e: any){
    const event = e.event;
    if(!event.Free)
      this.m_Router.navigate(['/appointment', event.Id]);
  }

  onActionBegin(args: ActionEventArgs): void {
    if(args.requestType == 'eventCreate' && !(args.data as any).final) {
      this.m_LoadingService.setLoading = true;
      args.cancel = true;
      const appointmentToCreate: CreateAvailableAppointmentDTO = {
        title: args.data?.at(0).Subject,
        start: args.data?.at(0).StartTime,
        end: args.data?.at(0).EndTime
      }
      this.m_CalendarService.createAvailableAppointment(appointmentToCreate).pipe(take(1))
      .subscribe((_:any) => {
        if(args.data) {
          let data: any = args.data as any;
          data['final'] = true;
          this.m_Scheduler?.addEvent(args.data as any);
          this.m_LoadingService.setLoading = false;
          this.m_SnackBar.open(`Successfully added available appointment`, 'Close', { duration: 7000 })
        }
      })
    }
  }

  setWorkingHours(hours: any){
    this.workingHours.startTime = hours.startTime.substring(0,5)
    this.workingHours.endTime = hours.endTime.substring(0,5)
    this.scheduleHours = { highlight: true, start: this.workingHours.startTime, end: this.workingHours.endTime};
  }

  setWorkingDays(days: any){
    this.workingDays = days;
  }

  getWorkingHoursStart(){
    return this.workingHours.startTime.substring(0,5)
  }

  getWorkingHoursEnd(){
    return this.workingHours.endTime.substring(0,5)
  }
}
