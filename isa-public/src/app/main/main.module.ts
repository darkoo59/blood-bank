import { NgModule } from '@angular/core';
import { MainRoutingModule } from './main-routing.module';

import { NgLetModule } from 'ng-let';
import { MainComponent } from './main.component';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/core/material.module';
import { NavComponent } from './nav/nav.component';
import { BCRegisterComponent } from './pages/bc-register/bc-register.component';

import { BCDashboardComponent } from './pages/bc-dashboard/bc-dashboard.component';

@NgModule({
  declarations: [
    MainComponent,
    NavComponent,
    BCRegisterComponent,
    BCDashboardComponent
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    NgLetModule,
    MaterialModule
  ],
  providers: []
})
export class MainModule { }
