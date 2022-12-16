import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { NgLetModule } from "ng-let";
import { MaterialModule } from "src/app/shared/material.module";
import { PageLoaderModule } from "src/app/shared/page-loader/page-loader.module";
import { UserListModule } from "src/app/shared/user-list/user-list.module";
import { AllUsersRoutingModule } from "./all-users-routing.module";
import { AllUsersComponent } from "./all-users.component";
import { ReactiveFormsModule } from '@angular/forms';
import { AllUserService } from "./services/all-user.service";

@NgModule({
  providers: [AllUserService],
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