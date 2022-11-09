import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { BCDashboardService } from "../main/pages/bc-dashboard/bc-dashboard.service";
import { LoadingService } from "./loading.service";

@Injectable({
  providedIn: 'root'
})
export class GlobalService {
  private m_DarkThemeSubject:BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  public m_DarkTheme$: Observable<boolean> = this.m_DarkThemeSubject.asObservable();
  
  constructor(private m_LoadingService: LoadingService, private m_BCDashboardService: BCDashboardService){
    const darkTheme = localStorage.getItem('darkTheme');
    if(darkTheme == undefined || darkTheme == 'true') {
      this.setDarkTheme = true;
    }
  }

  initApp(): void {
    //TODO: send initial requests...

    //temp
    this.m_LoadingService.setLoading = true;
    this.m_BCDashboardService.fetchBCData().subscribe(_ => {
      this.m_LoadingService.setLoading = false;
    });
    //temp end
  }

  set setDarkTheme(value: boolean) {
    this.m_DarkThemeSubject.next(value);
    localStorage.setItem('darkTheme', value+"");
  }
}