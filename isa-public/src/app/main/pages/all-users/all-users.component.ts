import { Component } from "@angular/core";
import { Observable, tap } from "rxjs";
import { User } from "src/app/model/user.model";
import { UserService } from "../../services/user.service";

@Component({
  selector: 'all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.scss']
})
export class AllUsersComponent {
  m_Error$: Observable<string | null> = this.m_UserService.m_Error$.pipe(
    tap(err => {
      if (err) this.m_Loading = false;
    }));
  m_Users$: Observable<User[]> = this.m_UserService.fetchAllUsers().pipe(tap(_ => this.m_Loading = false));

  m_Loading: boolean = true;

  constructor(private m_UserService: UserService) { }
}