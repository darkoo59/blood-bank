import { Component, Renderer2, OnInit} from '@angular/core';
import { tap } from 'rxjs';
import { GlobalService } from './services/global.service';
import { LoadingService } from './services/loading.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  m_DarkTheme$ = this.m_GlobalService.m_DarkTheme$.pipe(
    tap(darkTheme => {
      if(darkTheme) this.m_Renderer.addClass(document.body, 'dark-theme');
      else this.m_Renderer.removeClass(document.body, 'dark-theme');
    })
  );
  m_AppInit$ = this.m_GlobalService.initApp();

  m_Loading$ = this.m_LoadingService.m_Loading$;

  constructor(private m_GlobalService: GlobalService, private m_Renderer: Renderer2, private m_LoadingService: LoadingService) { }
  
  ngOnInit(): void {
    this.m_GlobalService.initApp();
  }

  toggleTheme(val: boolean): void {
    this.m_GlobalService.setDarkTheme = val;
  }
}
