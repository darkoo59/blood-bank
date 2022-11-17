import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { NgLetModule } from "ng-let";
import { MaterialModule } from "src/app/core/material.module";
import { ProfileAuthComponent } from "./pages/profile-auth/profile-auth.component";
import { ProfileOverviewComponent } from "./pages/profile-overview.component";
import { ProfileSettingsComponent } from "./pages/profile-settings.component";
import { ProfileRoutingModule } from "./profile-routing.module";
import { ProfileComponent } from "./profile.component";

@NgModule({
  declarations: [ProfileComponent, ProfileOverviewComponent, ProfileAuthComponent, ProfileSettingsComponent],
  imports: [
    CommonModule,
    ProfileRoutingModule,
    MaterialModule,
    NgLetModule,
    ReactiveFormsModule
  ]
})
export class ProfileModule { }