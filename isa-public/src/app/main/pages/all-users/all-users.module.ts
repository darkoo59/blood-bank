import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { NgLetModule } from "ng-let";
import { MaterialModule } from "src/app/core/material.module";
import { PageLoaderModule } from "src/app/core/page-loader/page-loader.module";
import { UserListModule } from "src/app/core/user-list/user-list.module";
import { AllUsersRoutingModule } from "./all-users-routing.module";
import { AllUsersComponent } from "./all-users.component";

@NgModule({
  declarations: [AllUsersComponent],
  imports: [
    CommonModule,
    UserListModule,
    AllUsersRoutingModule,
    PageLoaderModule,
    MaterialModule,
    NgLetModule
  ],
  exports: [AllUsersComponent]
})
export class AllUsersModule {}