import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, EMPTY, Observable } from 'rxjs';
import { Address } from 'src/app/model/address.model';
import { environment } from 'src/environments/environment';

export interface BCRegisterDTO {
  name: string
  description: string
  address: Address
}


@Injectable({
  providedIn: 'root'
})
export class BCRegisterService {

  constructor(private http: HttpClient) { }

  registerCenter(dto: BCRegisterDTO) : Observable<any> {
    return this.http.post(`${environment.apiUrl}/branch-center`, dto).pipe(catchError(err => { return EMPTY; }));
  }

}
