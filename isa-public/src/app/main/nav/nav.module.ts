import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { NgLetModule } from "ng-let";
import { MaterialModule } from "src/app/shared/material.module";
import { NavSectionComponent } from "./nav-section/nav-section.component";
import { NavComponent } from "./nav.component";

@NgModule({
  declarations: [NavComponent, NavSectionComponent],
  imports: [
    CommonModule,
    RouterModule,
    MaterialModule,
    NgLetModule
  ],
  exports: [NavComponent]
})
export class NavModule {}