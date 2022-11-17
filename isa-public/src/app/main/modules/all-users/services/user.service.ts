import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, take, tap } from "rxjs";
import { User } from "src/app/model/user.model";
import { GenericDataService } from "src/app/services/generic-data.service";
import { environment } from "src/environments/environment";

@Injectable()
export class UserService extends GenericDataService<User[]> {

  constructor(private m_Http: HttpClient) { super() }

  fetchAllUsers(): Observable<any> {
    this.clearError();
    return this.addErrorHandler(this.m_Http.get(`${environment.apiUrl}/user`).pipe(
      take(1),
      tap((res: any) => {
        this.setData = res;
      })
    ));
  }
}
