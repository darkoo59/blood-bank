import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, NavigationEnd, Router } from "@angular/router";
import { NavRoute } from "../../nav/nav.component";
import { UserService } from "../../../services/user.service";
import { Observable, filter, tap } from "rxjs";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent  implements OnInit {
  m_Error$: Observable<string | null> = this.m_UserService.m_Error$;

  m_Routes: NavRoute[] = [
    {
      path: 'overview',
      title: 'Overview'
    },
    {
      path: 'auth',
      title: 'Authentication'
    },
    {
      path: 'settings',
      title: 'Settings'
    }
  ];

  m_ActiveLink: string = this.m_Routes[0].path;

  constructor(private m_Router: Router, private m_UserService: UserService) { }

  m_ActiveLink$ = this.m_Router.events.pipe(
    filter((event: any) => event instanceof NavigationEnd),
    tap((route: any) => {
      const arr = route.url.split('/');
      this.m_ActiveLink = arr[arr.length - 1];
    })
  );

  ngOnInit() {}

  changeTab(path: string): void {
    this.m_ActiveLink = path
  }
}