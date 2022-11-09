import { Component } from "@angular/core";
import { BCDashboardService } from "../bc-dashboard.service";

@Component({
  selector: "app-bc-view",
  templateUrl: "./bc-view.component.html",
  styleUrls: ["./bc-view.component.scss"]
})
export class BCViewComponent {
  m_BCData$ = this.m_BCDashboardService.m_BCData$;

  constructor(private m_BCDashboardService: BCDashboardService) { }
}