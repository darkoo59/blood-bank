import { Component } from '@angular/core';

interface NavRoute {
  path: string;
  title: string;
}

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent {
  m_Routes: NavRoute[] = [
    {
      path: 'home',
      title: 'Home'
    }
  ];

  m_ProtectedRoutes: NavRoute[] = [
    {
      path: 'bc-dashboard',
      title: 'My Branch Center'
    },
    {
      path: 'bc-register',
      title: 'Register Branch Center'
    }
  ];

  constructor() { }
}
