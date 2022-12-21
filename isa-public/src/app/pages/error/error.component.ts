import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {

  queryParamsSubscription: Subscription = new Subscription()
  errorMessage: string = ''
  countDown: number = 0
  isConfirmed: boolean = false

  constructor(private m_Route: ActivatedRoute, private m_Router: Router) { }

  ngOnInit() {
    this.queryParamsSubscription = this.m_Route.queryParams.subscribe(params => {
      this.errorMessage = params['message']
    })
    if (this.errorMessage.includes('confirmed')) {
      this.isConfirmed = true;
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

  ngOnDestroy() {
    this.queryParamsSubscription.unsubscribe();
  }

}
