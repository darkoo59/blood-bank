import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { MaterialModule } from "src/app/core/material.module";
import { NavSectionComponent } from "./nav-section/nav-section.component";
import { NavComponent } from "./nav.component";

@NgModule({
  declarations: [NavComponent, NavSectionComponent],
  imports: [
    CommonModule,
    RouterModule,
    MaterialModule
  ],
  exports: [NavComponent]
})
export class NavModule {}