import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, catchError, EMPTY, Observable, switchMap, tap } from "rxjs";
import { Address } from "src/app/model/address.model";
import { UserService } from "src/app/services/user.service";
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
  private m_AccessTokenSubject$ = new BehaviorSubject<string | null>(null);
  public m_AccessToken$ = this.m_AccessTokenSubject$.asObservable();

  set setAccessToken(token: string | null) {
    this.m_AccessTokenSubject$.next(token);
  }
  clearAccessToken(): void {
    this.m_AccessTokenSubject$.next(null);
  }

  constructor(private m_Http: HttpClient, private m_UserService: UserService) { }

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
      tap((res: any) => this.setAccessToken = res.accessToken),
      switchMap(_ => {
        console.log(_);
        return this.m_UserService.fetchUserData()
      })
    );
  }

  logout(): Observable<any> {
    return this.m_Http.post(`${environment.apiUrl}/user/logout`, '').pipe(
      catchError(_ => EMPTY),
      tap(_ => {
        this.clearAccessToken()
        this.m_UserService.resetData();
      })
    );
  }
}