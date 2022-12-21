import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, EMPTY, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable()
export class BloodDonationScheduleService {

  constructor(private http: HttpClient) { }

  getBranchCenterWorkingHours() : Observable<any> {
    return this.http.get(`${environment.apiUrl}/branch-center/workingHours`);
  }

}
