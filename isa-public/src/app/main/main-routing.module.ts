import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { MainComponent } from './main.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { BCDashboardComponent } from './pages/bc-dashboard/bc-dashboard.component';
import { BCRegisterComponent } from './pages/bc-register/bc-register.component';
import { BCAllComponent } from './pages/bc-all/bc-all.component';

const routes: Routes = [
  {
    path: '', component: MainComponent, children: [
      { path: 'home', component: HomeComponent },
      {
        path: 'bc-dashboard',
        loadChildren: () => import('./pages/bc-dashboard/bc-dashboard.module').then(m => m.BCDashboardModule),
      },
      { path: 'bc-register', component: BCRegisterComponent},
      { path: '', pathMatch: 'full', redirectTo: 'home' },
      { path: 'bc-all', component: BCAllComponent},
      { path: '**', component: PageNotFoundComponent },

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule { }
