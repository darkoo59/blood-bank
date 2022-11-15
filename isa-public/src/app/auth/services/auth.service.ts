import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable, switchMap } from "rxjs";
import { Address } from "src/app/model/address.model";
import { environment } from "src/environments/environment";

export interface RegisterDTO {
  firstname: string,
  lastname: string,
  email: string,
  password: string,
  confirmPassword: string,
  address: Address,
  phone: string,
  nationalId: string,
  sex: string,
  occupation: string,
  information: string
}

export interface LoginDTO {
  email: string,
  password: string
}

@Injectable({
  providedIn: 'root'
})


export class AuthService { 
  constructor(private m_Http : HttpClient) {}

  register(registerDTO: RegisterDTO): Observable<any> {
    return this.m_Http.post(`${environment.apiUrl}/user/register`, registerDTO).pipe(
      map((res: any) => {
        console.log(res);
      })
    );
  }

  login(loginDTO: LoginDTO): Observable<any> {
    return this.m_Http.post(`${environment.apiUrl}/user/login`, loginDTO).pipe(
      map((res: any) => {
        console.log(res);
      })
    );
  }
}