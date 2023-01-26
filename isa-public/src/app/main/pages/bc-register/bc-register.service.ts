import { Time } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, EMPTY, Observable } from 'rxjs';
import { Address } from 'src/app/model/address.model';
import { environment } from 'src/environments/environment';

export interface BCRegisterDTO {
  name: string
  description: string
  address: Address
  monday: boolean
  tuesday: boolean
  wednesday: boolean
  thursday: boolean
  friday: boolean
  saturday: boolean
  sunday: boolean
  startTime: Time
  endTime: Time
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
