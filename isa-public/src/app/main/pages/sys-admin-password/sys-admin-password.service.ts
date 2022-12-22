import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, EMPTY, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SysAdminPasswordService {

constructor(private http: HttpClient) { }

  changeAdminPassword(password: string) : Observable<any> {
    return this.http.patch(`${environment.apiUrl}/person/admin-password`, password)
    .pipe(catchError(err => { return EMPTY; }));
  }

}
