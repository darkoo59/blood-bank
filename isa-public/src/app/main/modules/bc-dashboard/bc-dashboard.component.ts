import { Component, OnDestroy } from "@angular/core";
import { Observable, tap } from "rxjs";
import { BCDashboardService } from "./services/bc-dashboard.service";

@Component({
  selector: "app-bc-dashboard",
  templateUrl: "./bc-dashboard.component.html",
  styleUrls: ["./bc-dashboard.component.scss"]
})
export class BCDashboardComponent implements OnDestroy {
  m_FetchBCData$ = this.m_BCDashboardService.fetchBCData().pipe(tap(_ => this.m_Loading = false));
  m_Error$: Observable<string | null> = this.m_BCDashboardService.m_Error$.pipe(tap(_ => this.m_Loading = false));
  m_Loading: boolean = true;

  constructor(private m_BCDashboardService: BCDashboardService) { }

  ngOnDestroy(): void {
    this.m_BCDashboardService.resetData();
  }
}