import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CellClickEventArgs, DataBindingEventArgs, EventSettingsModel, PopupOpenEventArgs } from '@syncfusion/ej2-angular-schedule';
import { take } from 'rxjs';
import { AvailableAppointment } from 'src/app/model/available-appointment';
import { CalendarService } from './calendar.service';
import { WorkingHoursDTO } from './working-hours-dto';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss']
})
export class CalendarComponent implements OnInit {

  constructor(private m_CalendarService: CalendarService, private m_Router: Router) { }

  workingHours!:WorkingHoursDTO
  availableAppointments:AvailableAppointment[] = []

  ngOnInit() {
    this.m_CalendarService.getBranchCenterWorkingHours().pipe(take(1)).subscribe(data => {
      this.workingHours = data;
    });
    this.m_CalendarService.getAvailableAppointments().pipe(take(1)).subscribe(data => {
      this.convertAppointmentDTO(data);
    })
  }

  convertAppointmentDTO(dtos:any) {
    for(let appointment of dtos){
      this.availableAppointments.push({Id: appointment.id, StartTime: new Date(appointment.start), EndTime: new Date(appointment.end), Subject: 'Available appointment'} as AvailableAppointment)
    }
    this.eventSettings = {
      dataSource: this.availableAppointments,
      fields: {
        id: 'Id',
        subject: { name: 'Subject' },
        startTime: { name: 'StartTime' },
        endTime: { name: 'EndTime' },
      }
    }
  }

  onPopupOpen(args: PopupOpenEventArgs): void {
    if (args.type === 'Editor')  {
        args.cancel = true;
    }
}

  public eventSettings!: EventSettingsModel
  public views: Array<string> = ['Day','Week','Month', 'Year']

  onCellClick(args: CellClickEventArgs): void {
      if (args.startTime.getHours() < +this.workingHours.startTime.substring(0,2) || args.endTime.getHours() > +this.workingHours.endTime.substring(0,2)) {
        args.cancel = true;
      }
    }

  onCellDoubleClick(args: CellClickEventArgs): void {
      args.cancel = true;
    }

}
