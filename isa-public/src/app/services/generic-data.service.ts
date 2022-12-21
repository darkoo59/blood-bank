import { BehaviorSubject, catchError, EMPTY, Observable, switchMap, of, throwError } from "rxjs";

export abstract class GenericDataService<DataType> {
  protected m_DataSubject: BehaviorSubject<DataType | null> = new BehaviorSubject<DataType | null>(null);
  public m_Data$: Observable<DataType | null> = this.m_DataSubject.asObservable();

  protected m_ErrorSubject: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null);
  public m_Error$: Observable<string | null> = this.m_ErrorSubject.asObservable().pipe(
    switchMap(err => {
      return err ? of(err) : EMPTY
    })
  );

  set setData(data: DataType | null) {
    this.m_DataSubject.next(data);
  }
  clearData(): void {
    this.m_DataSubject.next(null);
  }

  set setError(error: string | null) {
    this.m_ErrorSubject.next(error);
  }
  clearError(): void {
    this.m_ErrorSubject.next(null);
  }

  resetData(): void {
    this.clearData();
    this.clearError();
  }

  //method that intercepts errors and fills error buffer with error message, it completes the stream
  protected addErrorHandler(obs: Observable<any>) {
    return obs.pipe(
      catchError(res => {
        console.log(res);
        const error = res.error;
        if (error && error.message) {
          this.setError = error.message;
        }else if(res.message){
          this.setError = res.message;
        }
        return EMPTY;
      })
    );
  }

  //same as regular handler except it doesn't complete the stream
  protected addErrorReader(obs: Observable<any>) {
    return obs.pipe(
      catchError(res => {
        console.log(res);
        const error = res.error;
        let msg = "";
        if (error && error.message) {
          msg = error.message;
        }else if(res.message){
          msg = res.message;
        }
        this.setError = msg;
        throw msg;
      })
    );
  }
}