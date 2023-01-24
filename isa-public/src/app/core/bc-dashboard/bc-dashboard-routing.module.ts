import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BCDashboardComponent } from "./bc-dashboard.component";
import { BCEditComponent } from "./pages/bc-edit/bc-edit.component";
import { BCViewComponent } from "./pages/bc-view/bc-view.component";

const routes: Routes = [
  {
    path: '', component: BCDashboardComponent, children: [
      { path: '', pathMatch: 'full', component: BCViewComponent },
      { path: 'bc-edit', component: BCEditComponent },
      { path: '**', redirectTo: '' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BCDashboardRoutingModule { }