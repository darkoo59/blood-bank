import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { MaterialModule } from "src/app/core/material.module";
import { ProfileRoutingModule } from "./profile-routing.module";
import { ProfileComponent } from "./profile.component";

@NgModule({
  declarations: [ProfileComponent],
  imports: [
    CommonModule,
    ProfileRoutingModule,
    MaterialModule
  ]
})
export class ProfileModule { }