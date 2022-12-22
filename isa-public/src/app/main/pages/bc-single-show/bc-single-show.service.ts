import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, take, tap } from 'rxjs';
import { BranchCenter } from 'src/app/model/branch-center.model';
import { GenericDataService } from 'src/app/services/generic-data.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BcsingleService extends GenericDataService<BranchCenter>{

  constructor(private m_Http: HttpClient) { super() }

  fetchBCData(id: string | null): Observable<any> {
    return this.addErrorHandler(this.m_Http.get(`${environment.apiUrl}/branch-center/view/`+id).pipe(
      take(1),
      tap((res: any) => {
        this.setData = res;
      })
    ));
  }
}
