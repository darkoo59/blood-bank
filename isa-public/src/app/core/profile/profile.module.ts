import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { NgLetModule } from "ng-let";
import { MaterialModule } from "src/app/shared/material.module";
import { ProfileSettingsComponent } from "./pages/profile-settings.component";
import { ProfileRoutingModule } from "./profile-routing.module";
import { ProfileComponent } from "./profile.component";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MapModule } from 'src/app/shared/map/map.module';
import { ProfileOverviewComponent } from "./pages/profile-overview/profile-overview.component";
import { ProfileAuthComponent } from "./pages/profile-auth/profile-auth.component";

@NgModule({
  declarations: [ProfileComponent, ProfileOverviewComponent, ProfileAuthComponent, ProfileSettingsComponent],
  imports: [
    CommonModule,
    ProfileRoutingModule,
    MaterialModule,
    NgLetModule,
    FormsModule,
    ReactiveFormsModule,
    MapModule
  ]
})
export class ProfileModule { }
