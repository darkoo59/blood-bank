import { Component, OnInit } from "@angular/core";
import { tap } from "rxjs";
import { UserService } from "src/app/services/user.service";

@Component({
  template: `
    <div *ngLet="(m_UserData$ | async) as data">
      overview...
    </div>
  `
})
export class ProfileOverviewComponent implements OnInit {
  m_UserData$ = this.m_UserService.m_Data$.pipe(tap(data => console.log(data)));

  constructor(private m_UserService: UserService){
    
  }

  ngOnInit(): void {
    // this.m_UserService.m_Data$.subscribe(data => {
    //   console.log(data);
    // });
  }
}