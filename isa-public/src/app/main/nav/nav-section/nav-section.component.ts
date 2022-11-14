import { Component, Input } from "@angular/core";
import { NavRoute } from "../nav.component";

@Component({
  selector: 'nav-section',
  templateUrl: './nav-section.component.html',
  styleUrls: ['./nav-section.component.scss']
})
export class NavSectionComponent {
  @Input() m_Routes: NavRoute[] = [];
  @Input() m_Title: string | null = null;
}