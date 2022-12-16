import { Component, Input } from "@angular/core";
import { NavRoute } from "src/app/model/nav-route.model";

@Component({
  selector: 'nav-section',
  templateUrl: './nav-section.component.html',
  styleUrls: ['./nav-section.component.scss']
})
export class NavSectionComponent {
  @Input() m_Routes: NavRoute[] = [];
  @Input() m_Title: string | null = null;
}