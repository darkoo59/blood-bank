import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { exhaustMap, Observable, take, tap } from "rxjs";
import { Address } from "src/app/model/address.model";
import { BranchCenter } from "src/app/model/branch-center.model";
import { GenericDataService } from "src/app/services/generic-data.service";
import { environment } from "src/environments/environment";

export interface BCUpdateDTO {
  id: number;
  name: string;
  description: string;
  address: Address;
}

@Injectable()
export class BCDashboardService extends GenericDataService<BranchCenter> {

  constructor(private m_Http: HttpClient) { super() }

  fetchBCData(): Observable<any> {
    return this.addErrorHandler(this.m_Http.get(`${environment.apiUrl}/bc-admin/bc`).pipe(
      take(1),
      tap((res: any) => {
        this.setData = res;
      })
    ));
  }

  patchBCData(dto: BCUpdateDTO): Observable<any> {
    return this.addErrorHandler(this.m_Http.patch(`${environment.apiUrl}/bc-admin/bc`, dto).pipe(
      exhaustMap(_ => this.fetchBCData())
    ));
  }
}