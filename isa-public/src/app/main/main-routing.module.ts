import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { MainComponent } from './main.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { BCDashboardComponent } from './pages/bc-dashboard/bc-dashboard.component';
import { BCRegisterComponent } from './pages/bc-register/bc-register.component';
import { BCAdminRegisterComponent } from './pages/bc-admin-register/bc-admin-register.component';

const routes: Routes = [
  {
    path: '', component: MainComponent, children: [
      { path: 'home', component: HomeComponent },
      { path: 'bc-dashboard', component: BCDashboardComponent },
      { path: 'bc-register', component: BCRegisterComponent},
      { path: 'bc-admin-register', component: BCAdminRegisterComponent},
      { path: '', pathMatch: 'full', redirectTo: 'home' },
      { path: '**', component: PageNotFoundComponent }
      
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule { }
