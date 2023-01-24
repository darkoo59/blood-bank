import { NgModule } from '@angular/core';

import { NgLetModule } from 'ng-let';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/shared/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { BCViewComponent } from './pages/bc-view/bc-view.component';
import { BCDashboardRoutingModule } from './bc-dashboard-routing.module';
import { MapModule } from 'src/app/shared/map/map.module';
import { PageLoaderModule } from 'src/app/shared/page-loader/page-loader.module';
import { BCDashboardComponent } from './bc-dashboard.component';
import { BCEditComponent } from './pages/bc-edit/bc-edit.component';
import { BCDashboardService } from './services/bc-dashboard.service';
import { UserListModule } from 'src/app/shared/user-list/user-list.module';
import { CommentListModule } from 'src/app/shared/comment-list/comment-list.module';

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
    PageLoaderModule,
    UserListModule,
    CommentListModule
  ]
})
export class BCDashboardModule { }
