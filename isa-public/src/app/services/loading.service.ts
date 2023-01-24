import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoadingService {
  private m_LoadingSubject:BehaviorSubject<boolean> = new BehaviorSubject<boolean>(true);
  public m_Loading$: Observable<boolean> = this.m_LoadingSubject.asObservable();

  set setLoading(value: boolean) {
    this.m_LoadingSubject.next(value);
  }
}