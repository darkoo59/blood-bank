import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NgLetModule } from "ng-let";
import { MaterialModule } from "src/app/shared/material.module";
import { AuthRoutingModule } from "./auth-routing.module";

import { AuthComponent } from "./auth.component";
import { LoginComponent } from "./components/login/login.component";
import { RegisterComponent } from "./components/register/register.component";

@NgModule({
  declarations: [
    AuthComponent,
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    CommonModule, 
    AuthRoutingModule,
    MaterialModule,
    NgLetModule,
    ReactiveFormsModule,
    CommonModule,
    FormsModule
  ]
})
export class AuthModule {}