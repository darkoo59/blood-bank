import { Component, OnInit } from '@angular/core'
import { UntypedFormArray, UntypedFormControl, UntypedFormGroup } from '@angular/forms'
import { MatSnackBar } from '@angular/material/snack-bar'
import { Router } from '@angular/router'
import { catchError, EMPTY, take } from 'rxjs'
import { UserService } from 'src/app/services/user.service'


export interface Answer {
  questionId: number,
  checked: boolean
}

@Component({
  selector: 'app-questionnaire',
  templateUrl: './questionnaire.component.html',
  styleUrls: ['./questionnaire.component.scss']
})
export class QuestionnaireComponent implements OnInit {

  constructor(private m_UserService: UserService, private m_SnackBar: MatSnackBar, private m_Router: Router) { }

  ngOnInit() {
    this.m_UserService.m_Data$.pipe(take(1)).subscribe(data => {
      if (data?.sex === 'MALE') {
        this.m_UserService.getMaleQuestions()
        .subscribe(questions => {
          this.dataSource = questions
          for (let question of questions) {
            this.form.addControl(question.id, new UntypedFormControl(false))
          }
        })
      } else {
        this.m_UserService.getFemaleQuestions()
        .subscribe(questions => {
          this.dataSource = questions
        })
      }
    })
  }

  form: UntypedFormGroup = new UntypedFormGroup({

  })

  onSubmit() {
    const raw = this.form.getRawValue()
    let obj: any = {
      answers: []
    }
    for (let r in raw) {
      const answer: Answer = { 
        questionId: parseInt(r), 
        checked: raw[r]
      }
      obj.answers.push(answer)
    }
    this.m_UserService.m_Data$.pipe(take(1)).subscribe(data => {
      obj['userId'] = data?.id
    })
    this.m_UserService.submitAnswers(obj)
    .pipe(catchError(res => {
      console.log(res.message)
      return EMPTY;
    }))
    .subscribe(_ => {
      this.m_SnackBar.open(`Successfully submited`, 'Close', { duration: 3000 })
      this.m_Router.navigate(['/home'])
    });
  }

  displayedColumns: string[] = ['question', 'checked']
  dataSource = []
}
