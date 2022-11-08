import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface BCRegisterDTO {
  name: string
  country: string
  city: string
  street: string
  number: string
  description: string
}


@Injectable({
  providedIn: 'root'
})
export class BCRegisterService {

  constructor(private http: HttpClient) { }

  registerCenter(dto: BCRegisterDTO) : Observable<any> {
    console.log(">>>> " + dto.name + " " + dto.country + " " + dto.city + " " + dto.street 
                + " " + dto.number + " " + dto.description + " <<<<") 
    return this.http.post(`blabla`, dto);
  }

}
