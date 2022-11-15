import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of, Subject, tap } from "rxjs";
import { User } from "src/app/model/user.model";
import { GenericDataService } from "src/app/services/generic-data.service";
import { environment } from "src/environments/environment";

export interface ChangeUserPasswordDTO {
  oldPassword: string;
  newPassword: string;
}

@Injectable({
  providedIn: 'root',
})
export class UserService extends GenericDataService<User> {
  constructor(private m_Http: HttpClient) { super() }

  fetchUserData(): Observable<any> {
    return this.addErrorReader(this.m_Http.get(`${environment.apiUrl}/person/8`).pipe(
      tap((res: any) => {
        this.setData = res;
      })
    ));
  }

  changeUserPassword(dto: ChangeUserPasswordDTO): Observable<any> {
    return this.addErrorHandler(this.m_Http.patch(`${environment.apiUrl}/person/password`, dto));
  }
}