import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable, switchMap } from "rxjs";
import { environment } from "src/environments/environment";

export interface RegisterDTO {
  name: string;
  surname: string;
  email: string;
  password: string;
  confirm_password: string;
  address: string;
  city: string;
  country: string;
  phone: string;
  id: string;
  sex: string;
}

@Injectable({
  providedIn: 'root'
})


export class AuthService { 
  constructor(private m_Http : HttpClient) {}

  register(registerDTO: RegisterDTO): Observable<any> {
    return this.m_Http.post(`${environment.apiUrl}/users/register`, registerDTO).pipe(
      map((res: any) => {
        console.log(res);
      })
    );
  }
}