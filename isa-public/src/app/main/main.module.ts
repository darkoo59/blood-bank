import { NgModule } from '@angular/core';
import { MainRoutingModule } from './main-routing.module';

import { NgLetModule } from 'ng-let';
import { MainComponent } from './main.component';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/core/material.module';
import { NavComponent } from './nav/nav.component';
import { BCRegisterComponent } from './pages/bc-register/bc-register.component';

import { BCDashboardComponent } from './pages/bc-dashboard/bc-dashboard.component';
import { ReactiveFormsModule } from '@angular/forms';
import { BCAdminRegisterComponent } from './pages/bc-admin-register/bc-admin-register.component';

@NgModule({
  declarations: [
    MainComponent,
    NavComponent,
    BCRegisterComponent,
    BCDashboardComponent,
    BCAdminRegisterComponent
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    NgLetModule,
    MaterialModule,
    ReactiveFormsModule
  ],
  providers: []
})
export class MainModule { }
