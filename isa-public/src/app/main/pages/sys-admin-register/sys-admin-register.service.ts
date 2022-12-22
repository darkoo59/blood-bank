import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, EMPTY, Observable } from 'rxjs';
import { Address } from 'src/app/model/address.model';
import { environment } from 'src/environments/environment';

export interface SysAdminRegisterDTO {
  email: string
  firstname: string
  information: string
  occupation: string
  lastname: string
  nationalId: string
  phone: string
  sex: string
  address: Address
}


@Injectable({
  providedIn: 'root'
})
export class SysAdminRegisterService {

constructor(private http: HttpClient) { }

registerAdmin(dto: SysAdminRegisterDTO) : Observable<any> {
  return this.http.post(`${environment.apiUrl}/admin/register`, dto)
    .pipe(catchError(err => { return EMPTY; }));
}

}