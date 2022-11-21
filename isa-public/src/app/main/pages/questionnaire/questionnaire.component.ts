import { Component, OnInit } from '@angular/core'
import { take } from 'rxjs'
import { UserService } from 'src/app/services/user.service'

export interface Questions {
  question: string
  checked: boolean
}



@Component({
  selector: 'app-questionnaire',
  templateUrl: './questionnaire.component.html',
  styleUrls: ['./questionnaire.component.scss']
})
export class QuestionnaireComponent implements OnInit {

  ELEMENT_DATA: Questions[] = [
    {question: 'Do you weigh less than 50 kg?', checked: false},
    {question: 'Whether you have symptoms of a cold, some illness or simply do not feel well?', checked: false},
    {question: 'Do you have skin changes (infections, rashes, fungal diseases...)?', checked: false},
    {question: 'Whether your blood pressure is too high or too low', checked: false},
    {question: 'Whether you are on therapy or it has not been at least 7 days since antibiotic therapy', checked: false},
    {question: 'Whether you are in the phase of a regular menstrual cycle', checked: false},
    {question: 'Has it not been at least 7 days since tooth extraction or minor dental intervention', checked: false},
    {question: 'Has it not been 6 months since body and skin piercing, tattoos or certain surgical interventions and blood transfusions', checked: false}
  ]
  constructor(private m_UserService: UserService) { }

  ngOnInit() {
    this.m_UserService.m_Data$.pipe(take(1)).subscribe(data => {
      if (data?.sex == 'MALE') {
        this.ELEMENT_DATA.splice(5, 1)
      }
    })
  }

  displayedColumns: string[] = ['question', 'checked']
  dataSource = this.ELEMENT_DATA
}
