import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { NgLetModule } from "ng-let";
import { MaterialModule } from "src/app/core/material.module";
import { PageLoaderModule } from "src/app/core/page-loader/page-loader.module";
import { UserListModule } from "src/app/core/user-list/user-list.module";
import { AllUsersRoutingModule } from "./all-users-routing.module";
import { AllUsersComponent } from "./all-users.component";
import { UserService } from "./services/user.service";
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  providers: [UserService],
  declarations: [AllUsersComponent],
  imports: [
    CommonModule,
    UserListModule,
    AllUsersRoutingModule,
    PageLoaderModule,
    MaterialModule,
    NgLetModule,
    ReactiveFormsModule
  ]
})
export class AllUsersModule {}