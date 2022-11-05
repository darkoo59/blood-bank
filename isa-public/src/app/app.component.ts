import { Component, Renderer2 } from '@angular/core';
import { tap } from 'rxjs';
import { GlobalService } from './services/global.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  m_DarkTheme$ = this.m_GlobalService.m_darkTheme$.pipe(
    tap(darkTheme => {
      if(darkTheme) this.m_Renderer.addClass(document.body, 'dark-theme');
      else this.m_Renderer.removeClass(document.body, 'dark-theme');
    })
  );

  constructor(private m_GlobalService: GlobalService, private m_Renderer: Renderer2) { }

  toggleTheme(val: boolean): void {
    this.m_GlobalService.setDarkTheme = val;
  }
}
