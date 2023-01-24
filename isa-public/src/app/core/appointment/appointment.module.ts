import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { NgLetModule } from "ng-let";
import { MaterialModule } from "src/app/shared/material.module";
import { PageLoaderModule } from "src/app/shared/page-loader/page-loader.module";
import { AppointmentRoutingModule } from "./appointment-routing.module";
import { AppointmentComponent } from "./appointment.component";
import { AppointmentDetailsComponent } from "./pages/appointment-details/appointment-details.component";
import { CreateDonationComponent } from "./pages/create-donation/create-donation.component";
import { BloodToNumberPipe } from "./pipes/blood-to-number.pipe";
import { BloodPipe } from "./pipes/blood.pipe";
import { AppointmentService } from "./services/appointment.service";

@NgModule({
  providers: [AppointmentService, BloodToNumberPipe, BloodPipe],
  declarations: [
    AppointmentComponent,
    AppointmentDetailsComponent,
    CreateDonationComponent,
    BloodPipe
  ],
  imports: [
    CommonModule,
    NgLetModule,
    AppointmentRoutingModule,
    MaterialModule,
    PageLoaderModule,
    ReactiveFormsModule
  ],
  exports: []
})
export class AppointmentModule { }