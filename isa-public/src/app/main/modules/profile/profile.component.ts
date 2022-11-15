import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { NavRoute } from "../../nav/nav.component";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent  implements OnInit {
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

  activeLink: string = this.m_Routes[0].path;

  constructor(private m_Route: ActivatedRoute) { }

  ngOnInit() {
    this.activeLink = this.m_Route.snapshot.firstChild?.url[0].path!;
  }

  changeTab(path: string): void {
    this.activeLink = path
  }
}