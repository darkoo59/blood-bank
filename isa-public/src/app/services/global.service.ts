import { Injectable } from "@angular/core";
import { BehaviorSubject, catchError, EMPTY, forkJoin, Observable } from "rxjs";
import { LoadingService } from "./loading.service";
import { UserService } from "./user.service";

@Injectable({
  providedIn: 'root'
})
export class GlobalService {
  private m_DarkThemeSubject:BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  public m_DarkTheme$: Observable<boolean> = this.m_DarkThemeSubject.asObservable();
  
  constructor(private m_LoadingService: LoadingService, private m_UserService: UserService){
    const darkTheme = localStorage.getItem('darkTheme');
    if(darkTheme == undefined || darkTheme == 'true') {
      this.setDarkTheme = true;
    }
  }

  initApp(): Observable<any> {
    //TODO: send initial requests...

    const userData$ = this.m_UserService.fetchUserData();
    return forkJoin([userData$], ([userData]) => {
      console.log(userData);
      this.m_LoadingService.setLoading = false;
    }).pipe(catchError((_: any) => {
      this.m_LoadingService.setLoading = false;
      return EMPTY;
    }));
  }

  set setDarkTheme(value: boolean) {
    this.m_DarkThemeSubject.next(value);
    localStorage.setItem('darkTheme', value+"");
  }
}