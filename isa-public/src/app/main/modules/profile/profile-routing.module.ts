import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { ProfileAuthComponent } from "./pages/profile-auth/profile-auth.component";
import { ProfileOverviewComponent } from "./pages/profile-overview.component";
import { ProfileSettingsComponent } from "./pages/profile-settings.component";
import { ProfileComponent } from "./profile.component";

const routes: Routes = [
  {
    path: '', component: ProfileComponent, children: [
      { path: 'overview', component: ProfileOverviewComponent },
      { path: 'auth', component: ProfileAuthComponent },
      { path: 'settings', component: ProfileSettingsComponent },
      { path: '**', pathMatch: 'full', redirectTo: 'overview' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }