import { Component } from '@angular/core';
import { GlobalService } from 'src/app/services/global.service';


@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent {
  m_DarkTheme$ = this.m_GlobalService.m_DarkTheme$;
  
  constructor(private m_GlobalService: GlobalService) { }

  toggleTheme(val: boolean): void {
    this.m_GlobalService.setDarkTheme = val;
  }
}
