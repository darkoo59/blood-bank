import { Component } from "@angular/core";
import { BCDashboardService } from "./bc-dashboard.service";

@Component({
  selector: "app-bc-dashboard",
  templateUrl: "./bc-dashboard.component.html",
  styleUrls: ["./bc-dashboard.component.scss"]
})
export class BCDashboardComponent {
  m_BCData$ = this.m_BCDashboardService.fetchBCData();

  constructor(private m_BCDashboardService: BCDashboardService) { }
}