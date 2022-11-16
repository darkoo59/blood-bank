import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";
import { Address } from "src/app/model/address.model";
import { environment } from "src/environments/environment";
import { Interceptor } from "./interceptor.service";

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
    return this.m_Http.post(`${environment.apiUrl}/user/register`, registerDTO)
  }
  
  login(loginDTO: LoginDTO): Observable<any> {

    let body = new URLSearchParams()
    body.set("email", loginDTO.email)
    body.set("password", loginDTO.password)

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    })
    const options = { 
      headers: headers,
      withCredentials: true
    }

    return this.m_Http.post(`${environment.apiUrl}/user/login`, body, options).pipe(
      tap((res: any) => {
        Interceptor.accessToken = res.accessToken
      })
    );
  }

  logout() {
    this.m_Http.post(`${environment.apiUrl}/user/logout`, '')
    Interceptor.accessToken = ''
  }
}