import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError,EMPTY, Observable } from 'rxjs';
import { News } from 'src/app/model/news';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NewsService {

constructor(private http: HttpClient) { }

sendNews(dto: News) : Observable<any> {
  return this.http.post(`${environment.apiUrl}/news/publish`, dto).pipe(catchError(err => { return EMPTY; }));
}
}
