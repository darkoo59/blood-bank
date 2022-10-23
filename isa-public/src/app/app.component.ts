import { Component, Renderer2 } from '@angular/core';
import { Store } from '@ngrx/store';
import { AppState } from './store';
import { selectDarkTheme } from 'src/app/store/global/global.selectors';
import { setDarkTheme } from 'src/app/store/global/global.actions';
import { tap } from 'rxjs';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'isa-front';
  darkTheme$ = this.store.select(selectDarkTheme).pipe(
    tap(darkTheme => {
      if(darkTheme) this.renderer.addClass(document.body, 'dark-theme');
      else this.renderer.removeClass(document.body, 'dark-theme');
    })
  );

  constructor(private store: Store<AppState>, private renderer: Renderer2) { }

  toggleTheme(val: boolean): void {
    this.store.dispatch(setDarkTheme({ darkTheme: val }));
    localStorage.setItem('darkTheme', val + "");
  }
}
