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
    },
    {
      path: 'bc-all',
      title: 'All Branch Centers'
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
    },
    {
      path: 'bc-admin-register',
      title: 'Register Branch Center Admin'
    }
  ];

  constructor() { }
}
