import { Component, OnDestroy } from "@angular/core";
import { FormControl, UntypedFormGroup } from "@angular/forms";
import { Observable, take, tap } from "rxjs";
import { User } from "src/app/model/user.model";
import { UserService } from "./services/user.service";

@Component({
  selector: 'all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.scss']
})
export class AllUsersComponent implements OnDestroy {
  m_Error$: Observable<string | null> = this.m_UserService.m_Error$.pipe(tap(_ => this.m_Loading = false));
  m_FetchAllUsers$ = this.m_UserService.fetchAllUsers().pipe(tap(_ => this.m_Loading = false));

  m_Users$: Observable<User[] | null> = this.m_UserService.m_Data$;
  m_Loading: boolean = true;

  form: UntypedFormGroup = new UntypedFormGroup({
    'search-input': new FormControl(null)
  })

  constructor(private m_UserService: UserService) { }

  ngOnDestroy(): void {
    this.m_UserService.resetData();
  }

  searchEntered() {
    const searchParam = this.form.get('search-input')?.value;
    this.m_UserService.findSearched(searchParam).pipe().subscribe() 

  }
}