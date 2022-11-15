import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";
import { User } from "src/app/model/user.model";
import { GenericDataService } from "src/app/services/generic-data.service";
import { environment } from "src/environments/environment";

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
}