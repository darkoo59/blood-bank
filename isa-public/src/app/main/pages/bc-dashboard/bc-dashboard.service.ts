import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, catchError, EMPTY, exhaustMap, Observable, take, tap } from "rxjs";
import { Address } from "src/app/model/address.model";
import { BranchCenter } from "src/app/model/branch-center.model";
import { environment } from "src/environments/environment";

export interface BCUpdateDTO {
  id: number;
  name: string;
  description: string;
  address: Address;
}

@Injectable({
  providedIn: 'root'
})
export class BCDashboardService {
  private m_BCDataSubject: BehaviorSubject<BranchCenter | null> = new BehaviorSubject<BranchCenter | null>(null);
  private m_ErrorSubject: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null);

  public m_BCData$: Observable<BranchCenter | null> = this.m_BCDataSubject.asObservable();
  public m_Error$: Observable<string | null> = this.m_ErrorSubject.asObservable();

  set setBCData(bc_data: BranchCenter | null) {
    this.m_BCDataSubject.next(bc_data);
  }

  set setErrors(error: string | null) {
    this.m_ErrorSubject.next(error);
  }

  constructor(private m_Http: HttpClient) {
    //temp
    this.fetchBCData().subscribe();
  }

  fetchBCData(): Observable<any> {
    return this.addErrorHandler(this.m_Http.get(`${environment.apiUrl}/bc-admin/bc`).pipe(
      take(1),
      tap((res: any) => {
        this.setBCData = res;
      })
    ));
  }

  patchBCData(dto: BCUpdateDTO): Observable<any> {
    return this.addErrorHandler(this.m_Http.patch(`${environment.apiUrl}/branch-center`, dto).pipe(
      exhaustMap(_ => this.fetchBCData())
    ));
  }

  private addErrorHandler(obs: Observable<any>): Observable<any> {
    return obs.pipe(
      catchError(res => {
        console.log(res);
        const error = res.error;
        if (error && error.message) {
          this.setErrors = error.message;
        }
        return EMPTY;
      })
    );
  }
}