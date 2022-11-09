import { NgModule } from '@angular/core';

import { NgLetModule } from 'ng-let';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/core/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { BCEditComponent } from './bc-edit/bc-edit.component';
import { BCViewComponent } from './bc-view/bc-view.component';
import { BCDashboardRoutingModule } from './bc-dashboard-routing.module';
import { MapModule } from 'src/app/core/map/map.module';

@NgModule({
  declarations: [
    BCViewComponent,
    BCEditComponent
  ],
  imports: [
    CommonModule,
    BCDashboardRoutingModule,
    NgLetModule,
    MaterialModule,
    ReactiveFormsModule,
    MapModule
  ],
  providers: []
})
export class BCDashboardModule { }
