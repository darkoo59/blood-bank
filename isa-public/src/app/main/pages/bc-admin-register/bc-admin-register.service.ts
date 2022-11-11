import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

export interface BCARegisterDTO {
  name: string
  surname: string
  password: string
  email: string
}

@Injectable({
  providedIn: 'root'
})
export class BCAdminRegisterService {

constructor(private http: HttpClient) { }

  registerBCAdmin(dto: BCARegisterDTO) : Observable<any> {
    return this.http.post(`${environment.apiUrl}/bc-admin`, dto);
  }

}
