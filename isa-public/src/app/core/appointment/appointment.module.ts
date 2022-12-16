import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { NgLetModule } from "ng-let";
import { MaterialModule } from "src/app/shared/material.module";
import { PageLoaderModule } from "src/app/shared/page-loader/page-loader.module";
import { AppointmentRoutingModule } from "./appointment-routing.module";
import { AppointmentComponent } from "./appointment.component";
import { BloodPipe } from "./pipes/blood.pipe";
import { AppointmentService } from "./services/appointment.service";

@NgModule({
  providers: [AppointmentService],
  declarations: [AppointmentComponent, BloodPipe],
  imports: [
    CommonModule,
    NgLetModule,
    AppointmentRoutingModule,
    MaterialModule,
    PageLoaderModule
  ],
  exports: []
})
export class AppointmentModule { }