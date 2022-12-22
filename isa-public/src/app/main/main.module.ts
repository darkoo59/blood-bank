import { NgModule } from '@angular/core';
import { MainRoutingModule } from './main-routing.module';

import { NgLetModule } from 'ng-let';
import { MainComponent } from './main.component';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/shared/material.module';
import { BCRegisterComponent } from './pages/bc-register/bc-register.component';

import { ReactiveFormsModule } from '@angular/forms';
import { BCAdminRegisterComponent } from './pages/bc-admin-register/bc-admin-register.component';
import { BCAllComponent } from './pages/bc-all/bc-all.component';
import { BcAllService } from './pages/bc-all/bc-all.service';
import { BCAdminAssignComponent } from './pages/bc-admin-assign/bc-admin-assign.component';
import { NavModule } from './nav/nav.module';
import { MapModule } from '../shared/map/map.module';
import { QuestionnaireComponent } from './pages/questionnaire/questionnaire.component';
import { CommentListModule } from '../shared/comment-list/comment-list.module';
import { SendingNewsComponent } from './pages/sending-news/sending-news.component';
import { CalendarComponent } from './pages/calendar/calendar.component';
import { DayService, MonthAgendaService, MonthService, RecurrenceEditorModule, ScheduleModule, WeekService, WorkWeekService, YearService } from '@syncfusion/ej2-angular-schedule';
import { BcSingleShowComponent } from './pages/bc-single-show/bc-single-show.component';
import { ConfirmedComponent } from '../pages/confirmed/confirmed.component';
import { ErrorComponent } from '../pages/error/error.component';
import { ScheduleAppointmentComponent } from './pages/schedule-appointment/schedule-appointment.component';
import { ScheduleAppointmentService } from './pages/schedule-appointment/schedule-appointment.service';
import { SysAdminRegisterComponent } from './pages/sys-admin-register/sys-admin-register.component';
import { SysAdminPasswordComponent } from './pages/sys-admin-password/sys-admin-password.component';
import { UserAppointmentsComponent } from './pages/user-appointments/user-appointments.component';

@NgModule({
  declarations: [
    MainComponent,
    BCRegisterComponent,
    BCAdminRegisterComponent,
    BCAllComponent,
    BCAdminAssignComponent,
    QuestionnaireComponent,
    SendingNewsComponent,
    ConfirmedComponent,
    ErrorComponent,
    CalendarComponent,
    BcSingleShowComponent,
    ScheduleAppointmentComponent,
    SysAdminRegisterComponent,
    SysAdminPasswordComponent,
    UserAppointmentsComponent
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    NgLetModule,
    MaterialModule,
    ReactiveFormsModule,
    NavModule,
    MapModule,
    CommentListModule,
    ScheduleModule,
    RecurrenceEditorModule
  ],
  providers: [BcAllService, DayService, WeekService, WorkWeekService, MonthService, YearService, MonthAgendaService, ScheduleAppointmentService]
})
export class MainModule { }
