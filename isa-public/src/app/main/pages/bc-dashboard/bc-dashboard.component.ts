import { Component } from "@angular/core";
import { Observable, tap } from "rxjs";
import { BCDashboardService } from "./bc-dashboard.service";

@Component({
  selector: "app-bc-dashboard",
  templateUrl: "./bc-dashboard.component.html",
  styleUrls: ["./bc-dashboard.component.scss"]
})
export class BCDashboardComponent {
  m_BCData$ = this.m_BCDashboardService.m_BCData$.pipe(tap(_ => this.m_Loading = false));
  m_Error$: Observable<string | null> = this.m_BCDashboardService.m_Error$.pipe(
    tap(err => {
      if(err) this.m_Loading = false;
    })
  );
  m_Loading: boolean = true;

  constructor(private m_BCDashboardService: BCDashboardService){}
}