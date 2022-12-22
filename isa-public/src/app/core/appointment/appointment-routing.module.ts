import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AppointmentComponent } from "./appointment.component";
import { AppointmentDetailsComponent } from "./pages/appointment-details/appointment-details.component";
import { CreateDonationComponent } from "./pages/create-donation/create-donation.component";

const routes: Routes = [
  {
    path: '', component: AppointmentComponent, children: [
      {
        path: '', component: AppointmentDetailsComponent
      },
      {
        path: 'donation', component: CreateDonationComponent
      },
      { path: '**', pathMatch: 'full', redirectTo: '' },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AppointmentRoutingModule { }