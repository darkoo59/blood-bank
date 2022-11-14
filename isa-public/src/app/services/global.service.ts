import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { LoadingService } from "./loading.service";

@Injectable({
  providedIn: 'root'
})
export class GlobalService {
  private m_DarkThemeSubject:BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  public m_DarkTheme$: Observable<boolean> = this.m_DarkThemeSubject.asObservable();
  
  constructor(private m_LoadingService: LoadingService){
    const darkTheme = localStorage.getItem('darkTheme');
    if(darkTheme == undefined || darkTheme == 'true') {
      this.setDarkTheme = true;
    }
  }

  initApp(): void {
    //TODO: send initial requests...
    this.m_LoadingService.setLoading = false;
  }

  set setDarkTheme(value: boolean) {
    this.m_DarkThemeSubject.next(value);
    localStorage.setItem('darkTheme', value+"");
  }
}