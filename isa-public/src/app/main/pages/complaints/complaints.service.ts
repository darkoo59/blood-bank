import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginComponent } from 'src/app/auth/components/login/login.component';
import { Complaint } from 'src/app/model/complaint.model';
import { environment } from 'src/environments/environment';

export interface ComplaintResponseDTO {
  id: number;
  text: string;
}

@Injectable({
  providedIn: 'root'
})
export class ComplaintsService {

constructor(private http: HttpClient) { }

getComplaints() : Observable<Complaint[]> {
  return this.http.get<Complaint[]>(`${environment.apiUrl}/complaints`)
}

respond(dto: ComplaintResponseDTO) : Observable<any> {
  return this.http.patch(`${environment.apiUrl}/complaints`, dto);
}


}
