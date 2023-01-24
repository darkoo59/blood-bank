import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BCAdmin } from 'src/app/model/bc-admin.model';
import { BranchCenter } from 'src/app/model/branch-center.model';
import { environment } from 'src/environments/environment';

export interface BCAAssignDTO {
  bcAdminEmail: string;
  centerId: number;
}


@Injectable({
  providedIn: 'root'
})
export class BCAdminAssignService {

constructor(private http: HttpClient) { }

  public findAll(): Observable<BranchCenter[]> {
    return this.http.get<BranchCenter[]>(`${environment.apiUrl}/branch-center/all`);
  }

  public findUnassinged() : Observable<BCAdmin[]> {
    return this.http.get<BCAdmin[]>(`${environment.apiUrl}/bc-admin/unassigned`);
  }

  assignAdminToCenter(dto: BCAAssignDTO) : Observable<any> {
    return this.http.patch(`${environment.apiUrl}/bc-admin/assign`, dto);
  }

}
