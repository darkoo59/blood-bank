import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-confirmed',
  templateUrl: './confirmed.component.html',
  styleUrls: ['./confirmed.component.css']
})
export class ConfirmedComponent implements OnInit {

  countDown: number = 0

  constructor(private m_Router: Router) { }

  ngOnInit() {
    this.countDown = 6;
    const interval = setInterval(() => {
      this.countDown--;
      if (this.countDown === 0) {
        clearInterval(interval);
        this.m_Router.navigate(['/auth']);
      }
    }, 1000);
  }

}
