import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BranchCenter } from 'src/app/model/branch-center.model';
import { BranchCenterPage } from 'src/app/model/classes/branch-center-page';

@Injectable()
export class BcAllService {
  private branchCentersUrl: string;

constructor(private http: HttpClient) {
  this.branchCentersUrl = 'http://localhost:6555/api/branch-center'
}

public findAll(): Observable<BranchCenter[]> {
  return this.http.get<BranchCenter[]>(this.branchCentersUrl+'/all');
}

public findAllByPages(request: any) : Observable<BranchCenterPage>{
  let params = new HttpParams();
  if(request.page != null && request.page != undefined){
    params = params.set('page', request.page);
  }
  if(request.size != null && request.size != undefined){
    params = params.set('size', request.size);
  }
  if(request.name != null && request.name != undefined){
    params = params.set('name',request.name);
  }
  return this.http.get<BranchCenterPage>(this.branchCentersUrl+'/all-centers-pagination', {params: params});
}

}
