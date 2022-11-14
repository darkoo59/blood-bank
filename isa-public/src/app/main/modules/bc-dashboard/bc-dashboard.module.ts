import { NgModule } from '@angular/core';

import { NgLetModule } from 'ng-let';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/core/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { BCViewComponent } from './pages/bc-view/bc-view.component';
import { BCDashboardRoutingModule } from './bc-dashboard-routing.module';
import { MapModule } from 'src/app/core/map/map.module';
import { PageLoaderModule } from 'src/app/core/page-loader/page-loader.module';
import { BCDashboardComponent } from './bc-dashboard.component';
import { BCEditComponent } from './pages/bc-edit/bc-edit.component';
import { BCDashboardService } from './services/bc-dashboard.service';

@NgModule({
  providers: [BCDashboardService],
  declarations: [
    BCViewComponent,
    BCEditComponent,
    BCDashboardComponent
  ],
  imports: [
    CommonModule,
    BCDashboardRoutingModule,
    NgLetModule,
    MaterialModule,
    ReactiveFormsModule,
    MapModule,
    PageLoaderModule
  ]
})
export class BCDashboardModule { }
