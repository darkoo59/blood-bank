import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UnauthGuard } from './auth/guards/unauth.guard';
import { ConfirmedComponent } from './main/pages/email-confirmation/confirmed/confirmed.component';
import { ErrorComponent } from './main/pages/email-confirmation/error/error.component';

const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule),
    canActivate: [UnauthGuard]
  },
  {
    path: 'confirmed',
    component: ConfirmedComponent
  },
  {
    path: 'error',
    component: ErrorComponent,
    data: {
      queryParamsHandling: 'merge'
    }
  },
  {
    path: '',
    loadChildren: () => import('./main/main.module').then(m => m.MainModule),
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
