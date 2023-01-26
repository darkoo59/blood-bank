import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Observable, tap } from "rxjs";
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
  constructor(private m_Http: HttpClient, private m_Router: Router) { super() }

  fetchUserData(): Observable<any> {
    return this.addErrorReader(this.m_Http.get(`${environment.apiUrl}/person/current`).pipe(
      tap((res: any) => {
        this.clearError();
        this.setData = res;
        this.checkIfPasswordChanged(res);
      })
    ));
  }

  checkIfPasswordChanged(userData: any) {
    for(let role of userData.roles) {
      if(role.name == 'ROLE_ADMIN' && !userData.passwordChanged){
        this.m_Router.navigate(['sys-admin-password']);
        break;
      }
    }
  }

  getUserPenalties(userId: number | undefined): Observable<any>{
    return this.addErrorHandler(this.m_Http.get(`${environment.apiUrl}/user/penalties/${userId}`, {}));
  }

  changeUserPassword(dto: ChangeUserPasswordDTO): Observable<any> {
    return this.addErrorHandler(this.m_Http.patch(`${environment.apiUrl}/person/password`, dto));
  }

  update(personToUpdate: User) : Observable<Object> {
    const body = {id: personToUpdate.id,firstname: personToUpdate.firstname, lastname: personToUpdate.lastname, email: personToUpdate.email, address: personToUpdate.address,
    phone: personToUpdate.phone, nationalId: personToUpdate.nationalId, sex: personToUpdate.sex, occupation: personToUpdate.occupation,
    information: personToUpdate.information}
    return this.m_Http.patch(`${environment.apiUrl}/person`, body);
  }

  getUserRank(id:number) : Observable<string> {
    return this.m_Http.get<string>(`${environment.apiUrl}/user/getRank/${id}`)
  }

  getUserPoints(id:number) : Observable<string> {
    return this.m_Http.get<string>(`${environment.apiUrl}/user/getPoints/${id}`)
  }
}
