import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface BCRegisterDTO {
  name: string
  description: string
  country: string
  city: string
  street: string
  number: string
  
}


@Injectable({
  providedIn: 'root'
})
export class BCRegisterService {

  constructor(private http: HttpClient) { }

  registerCenter(dto: BCRegisterDTO) : Observable<any> {
    return this.http.post(`${environment.apiUrl}/branch-center`, dto);
  }

}
