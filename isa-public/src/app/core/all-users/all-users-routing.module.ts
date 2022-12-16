import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AllUsersComponent } from "./all-users.component";

const routes: Routes = [
  {
    path: '', component: AllUsersComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AllUsersRoutingModule { }
