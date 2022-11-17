import { HttpClient, HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, switchMap, throwError } from "rxjs";
import { environment } from "src/environments/environment";
import { AuthService } from "./auth.service";

@Injectable()
export class Interceptor implements HttpInterceptor {
    static accessToken = ''
    private refresh = false

    constructor(private m_Http: HttpClient, private m_AuthService: AuthService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const request = req.clone({
            setHeaders: {
                Authorization: `Bearer ${Interceptor.accessToken}`
            }
        });
        return next.handle(request).pipe(catchError((err: HttpErrorResponse) => {
            if (err.status === 403 && !this.refresh) {
                this.refresh = true

                return this.m_Http.post(`${environment.apiUrl}/user/token/refresh`, {}, { withCredentials: true }).pipe(
                    switchMap((res: any) => {
                        Interceptor.accessToken = res.accessToken
                        this.m_AuthService.setAccessToken = res.accessToken;

                        return next.handle(req.clone({
                            setHeaders: {
                                Authorization: `Bearer ${Interceptor.accessToken}`
                            }
                        }))
                    })
                )
            }
            this.refresh = false
            return throwError(() => err)
        }))
    }
}