import { Component } from '@angular/core';

export interface NavRoute {
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

  m_UserRoutes: NavRoute[] = [];

  m_BCAdminRoutes: NavRoute[] = [
    {
      path: 'bc-dashboard',
      title: 'My Branch Center'
    }
  ];

  m_AdminRoutes: NavRoute[] = [
    {
      path: 'bc-all',
      title: 'All Branch Centers'
    },
    {
      path: 'bc-register',
      title: 'Register Branch Center'
    },
    {
      path: 'bc-admin-register',
      title: 'Register Branch Center Admin'
    },
    {
      path: 'bc-admin-assign',
      title: 'Assign Admin To Branch Center'
    },
    {
      path: 'all-users',
      title: 'All users'
    }
  ];

  m_StaffRoutes: NavRoute[] = [];

  constructor() { }
}
