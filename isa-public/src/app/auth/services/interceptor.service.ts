import { HttpClient, HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, switchMap, take, throwError } from "rxjs";
import { environment } from "src/environments/environment";
import { AuthService } from "./auth.service";

@Injectable()
export class Interceptor implements HttpInterceptor {
    constructor(private m_Http: HttpClient, private m_AuthService: AuthService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if(req.url.indexOf(environment.apiUrl) == -1){
            return next.handle(req);
        }

        return this.m_AuthService.m_AccessToken$.pipe(
            take(1),
            switchMap((t: string | null) => {
                let token: string = ''
                token = t ? t : '';

                if (req.headers.has('logoutHeader')) {
                    const request = req.clone({
                        withCredentials: true
                    })
        
                    return next.handle(request).pipe(catchError((err: HttpErrorResponse) => {
                        return throwError(() => err)
                    }))
                } else {
                    const request = req.clone({
                        setHeaders: {
                            Authorization: `Bearer ${token}`
                        }
                    });
        
                    return next.handle(request).pipe(catchError((err: HttpErrorResponse) => {
                        if (err.status === 403) {
                            return this.m_Http.post(`${environment.apiUrl}/user/token/refresh`, {}, { withCredentials: true }).pipe(
                                switchMap((res: any) => {
                                    token = res.accessToken
                                    this.m_AuthService.setAccessToken = res.accessToken;
        
                                    return next.handle(req.clone({
                                        setHeaders: {
                                            Authorization: `Bearer ${token}`
                                        }
                                    }))
                                })
                            )
                        }
                        return throwError(() => err)
                    }))
                }
            })
        );
    }
}