import { Component } from '@angular/core';
import { map, Observable } from 'rxjs';
import { NavRoute } from 'src/app/model/nav-route.model';
import { Role } from 'src/app/model/role.model';
import { User } from 'src/app/model/user.model';
import { UserService } from 'src/app/services/user.service';

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

  m_CommonRoutes: NavRoute[] = [
    {
      path: 'profile',
      title: 'My Profile'
    }
  ];

  m_UserRoutes: NavRoute[] = [
    {
      path: 'questionnaire',
      title: 'Questionnaire'
    },
    {
      path: 'schedule-appointment',
      title: 'Schedule Appointment'
    }
  ];

  m_BCAdminRoutes: NavRoute[] = [
    {
      path: 'bc-dashboard',
      title: 'My Branch Center'
    },
    {
      path: 'all-users',
      title: 'All users'
    },
    {
      path: "send-news",
      title: 'Send news to hospital'
    },
    {
      path: "calendar",
      title: 'Calendar'
    }
  ];

  m_AdminRoutes: NavRoute[] = [

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
      path: 'sys-admin-register',
      title: 'Register System Admin'
    },
    {
      path: 'all-users',
      title: 'All users'
    },
    {
      path: "send-news",
      title: 'Send news to hospital'
    },
    {
      path: 'complaints',
      title: 'Complaints'
    }
  ];

  m_StaffRoutes: NavRoute[] = [];

  m_UserRoles$: Observable<Role[] | null | undefined> = this.m_UserService.m_Data$.pipe(
    map((userData: User | null | undefined) => {
      return userData ? userData.roles : null;
    })
  );

  constructor(private m_UserService: UserService) {}

  hasRole(roles: Role[] | null, role?: string | null): boolean {
    if(!roles) return false;
    if(roles && !role) return true;
    for(let r of roles){
      if(r.name == role) return true;
    }
    return false;
  }
}
