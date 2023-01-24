import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { MainComponent } from './main.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { BCRegisterComponent } from './pages/bc-register/bc-register.component';
import { BCAdminRegisterComponent } from './pages/bc-admin-register/bc-admin-register.component';
import { BCAllComponent } from './pages/bc-all/bc-all.component';
import { BCAdminAssignComponent } from './pages/bc-admin-assign/bc-admin-assign.component';
import { RoleGuard } from '../auth/guards/role.guard';
import { QuestionnaireComponent } from './pages/questionnaire/questionnaire.component';
import { SendingNewsComponent } from './pages/sending-news/sending-news.component';
import { CalendarComponent } from './pages/calendar/calendar.component';
import { ScheduleAppointmentComponent } from './pages/schedule-appointment/schedule-appointment.component';
import { SysAdminRegisterComponent } from './pages/sys-admin-register/sys-admin-register.component';
import { SysAdminPasswordComponent } from './pages/sys-admin-password/sys-admin-password.component';
import { ComplaintsComponent } from './pages/complaints/complaints.component';
import { UserAppointmentsComponent } from './pages/user-appointments/user-appointments.component';
import { BcSingleShowComponent } from './pages/bc-single-show/bc-single-show.component';

const routes: Routes = [
  {
    path: '', component: MainComponent, children: [
      {
        path: 'home',
        component: HomeComponent
      },
      {
        path: 'bc-register',
        component: BCRegisterComponent,
        canActivate: [RoleGuard],
        data: { roles: ["ROLE_ADMIN"] }
      },
      {
        path: 'bc-admin-register',
        component: BCAdminRegisterComponent,
        canActivate: [RoleGuard],
        data: { roles: ["ROLE_ADMIN"] }
      },
      {
        path: 'bc-admin-assign',
        component: BCAdminAssignComponent,
        canActivate: [RoleGuard],
        data: { roles: ["ROLE_ADMIN"] }
      },
      {
        path: 'bc-all',
        component: BCAllComponent
      },
      {
        path: 'bc/:id',
        component: BcSingleShowComponent,
        canActivate: [RoleGuard],
        data: { roles: ["ROLE_USER"] }
      },
      {
        path: 'questionnaire',
        component: QuestionnaireComponent,
        canActivate: [RoleGuard],
        data: { roles: ["ROLE_USER"] }
      },
      {
        path: 'bc-dashboard',
        loadChildren: () => import('../core/bc-dashboard/bc-dashboard.module').then(m => m.BCDashboardModule),
        canActivate: [RoleGuard],
        data: { roles: ["ROLE_BCADMIN"] }
      },
      {
        path: 'all-users',
        loadChildren: () => import('../core/all-users/all-users.module').then(m => m.AllUsersModule),
        canActivate: [RoleGuard],
        data: { roles: ["ROLE_ADMIN", "ROLE_BCADMIN"] }
      },
      {
        path: 'profile',
        loadChildren: () => import('../core/profile/profile.module').then(m => m.ProfileModule),
        canActivate: [RoleGuard]
      },
      {
        path: 'send-news',
        component: SendingNewsComponent,
        canActivate: [RoleGuard],
        data: { roles: ["ROLE_ADMIN", "ROLE_BCADMIN"] }
      },
      {
        path: 'appointment/:id',
        loadChildren: () => import('../core/appointment/appointment.module').then(m => m.AppointmentModule),
        canActivate: [RoleGuard],
        data: { roles: ["ROLE_BCADMIN"] }
      },
      {
        path: 'calendar',
        component: CalendarComponent,
        canActivate: [RoleGuard],
        data: { roles: ["ROLE_BCADMIN"] }
      },
      {
        path: 'schedule-appointment',
        component: ScheduleAppointmentComponent,
        canActivate: [RoleGuard],
        data: { roles: ["ROLE_USER"]}
      },
      {
        path: 'user-appointments',
        component: UserAppointmentsComponent,
        canActivate: [RoleGuard],
        data: { roles: ["ROLE_USER"]}
      },
      {
        path: 'sys-admin-register',
        component: SysAdminRegisterComponent,
        canActivate: [RoleGuard],
        data: { roles: ["ROLE_ADMIN"]}
      },
      {
        path: 'sys-admin-password',
        component: SysAdminPasswordComponent,
        canActivate: [RoleGuard],
        data: { roles: ["ROLE_ADMIN"] }
      },
      {
        path: 'complaints',
        component: ComplaintsComponent,
        canActivate: [RoleGuard],
        data: { roles: ["ROLE_ADMIN"] }
      },
      { path: '', pathMatch: 'full', redirectTo: 'home' },
      { path: '**', component: PageNotFoundComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule { }
