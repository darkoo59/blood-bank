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
import { ConfirmedComponent } from './pages/email-confirmation/confirmed/confirmed.component';
import { ErrorComponent } from './pages/email-confirmation/error/error.component';

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
    ErrorComponent
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    NgLetModule,
    MaterialModule,
    ReactiveFormsModule,
    NavModule,
    MapModule,
    CommentListModule
  ],
  providers: [BcAllService]
})
export class MainModule { }
