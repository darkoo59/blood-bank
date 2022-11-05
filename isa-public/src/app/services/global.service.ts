import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GlobalService {
  private m_darkThemeSubject:BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  
  public m_darkTheme$: Observable<boolean> = this.m_darkThemeSubject.asObservable();
  
  constructor(){
    const darkTheme = localStorage.getItem('darkTheme');
    if(darkTheme == undefined || darkTheme == 'true') {
      this.setDarkTheme = true;
    }
  }

  set setDarkTheme(value: boolean) {
    this.m_darkThemeSubject.next(value);
    localStorage.setItem('darkTheme', value+"");
  }
}