import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Questionnaire } from 'src/app/model/questionnaire.model';
import { GenericDataService } from 'src/app/services/generic-data.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class QuestionnaireService extends GenericDataService<Questionnaire>{
  constructor(private m_Http: HttpClient) { super() }

  getMaleQuestions() : Observable<any> {
    return this.m_Http.get(`${environment.apiUrl}/questionnaire/get/male/basic`)
  }

  getFemaleQuestions() : Observable<any> {
    return this.m_Http.get(`${environment.apiUrl}/questionnaire/get/female/basic`)
  }

  getAnsweredQuestionnaireByUserId(id: number | undefined) : Observable<any> {
    return this.m_Http.get(`${environment.apiUrl}/questionnaire/get/user/`+id)
  }

  submitAnswers(dto: any) : Observable<any> {
    return this.m_Http.post(`${environment.apiUrl}/questionnaire/submit`, dto)
  }
}
