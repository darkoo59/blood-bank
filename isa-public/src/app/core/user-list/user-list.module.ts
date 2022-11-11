import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { MaterialModule } from "../material.module";
import { UserListComponent } from "./user-list.component";

@NgModule({
  declarations: [UserListComponent],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [UserListComponent]
})
export class UserListModule {}