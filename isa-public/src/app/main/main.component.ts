import { Component } from '@angular/core';
import { Subject, switchMap } from 'rxjs';
import { GlobalService } from 'src/app/services/global.service';
import { AuthService } from '../auth/services/auth.service';


@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent {
  m_DarkTheme$ = this.m_GlobalService.m_DarkTheme$;
  m_UserLogged$ = this.m_AuthService.m_AccessToken$;
  m_Logout$: Subject<any> = new Subject().pipe(switchMap(_ => this.m_AuthService.logout())) as Subject<any>;

  constructor(private m_GlobalService: GlobalService, private m_AuthService: AuthService) { }

  toggleTheme(val: boolean): void {
    this.m_GlobalService.setDarkTheme = val;
  }
}
