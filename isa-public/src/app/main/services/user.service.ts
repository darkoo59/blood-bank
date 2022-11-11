import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, EMPTY, Observable, of, Subject } from "rxjs";
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private m_ErrorSubject: Subject<string | null> = new Subject<string | null>();
  public m_Error$: Observable<string | null> = this.m_ErrorSubject.asObservable();

  constructor(private m_Http: HttpClient) { }

  fetchAllUsers(): Observable<any> {
    this.clearError();
    return this.addErrorHandler(this.m_Http.get(`${environment.apiUrl}/user`));
  }

  private addErrorHandler(obs: Observable<any>): Observable<any> {
    return obs.pipe(
      catchError(res => {
        console.log(res);
        const error = res.error;
        if (error && error.message) {
          this.setError = error.message;
        }
        return EMPTY;
      })
    );
  }

  set setError(error: string | null) {
    this.m_ErrorSubject.next(error);
  }
  clearError(): void {
    this.setError = null;
  }
}