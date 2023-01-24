import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ScheduleAppointmentService } from './schedule-appointment.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { BranchCenter } from 'src/app/model/branch-center.model';
import { catchError, EMPTY, forkJoin, Subscription, switchMap, take } from 'rxjs';
import { Comment } from 'src/app/model/comment.model';
import { UserService } from 'src/app/services/user.service';
import { UserScheduleAppointmentDTO } from './dto/user-schedule-appointment-dto';
import { QuestionnaireService } from '../questionnaire/questionnaire.service';
import { User } from 'src/app/model/user.model';
import { TransitionCheckState } from '@angular/material/checkbox';

@Component({
  selector: 'app-scedule-appointment',
  templateUrl: './schedule-appointment.component.html',
  styleUrls: ['./schedule-appointment.component.scss']
})
export class ScheduleAppointmentComponent implements OnInit,OnDestroy {

  dateForm: UntypedFormGroup = new UntypedFormGroup({
    'selectedDate': new FormControl(null, Validators.required),
  })
  selectedDate: string;
  minimumSelectedDateTime: string
  isDateSelected: boolean = false;
  branchCenters: BranchCenter[] = [];
  isQuestionaireDone: boolean = true;
  m_Errors: string[] = [];
  constructor(private m_ScheduleAppointmentService: ScheduleAppointmentService, private m_Router: Router,
    private m_SnackBar: MatSnackBar, private m_UserService: UserService, private m_QuestionnaireService: QuestionnaireService) {
    let date = new Date()
    date.setDate(date.getDate() + 1)
    this.selectedDate = date.toISOString().slice(0,16)
    this.minimumSelectedDateTime = date.toISOString().slice(0,16)
  }

  ngOnInit() {
  }

  ngOnDestroy(){
      this.branchCentersSubscription.unsubscribe()
  }

  onDateChange(event:any): void {
    this.selectedDate = event.target.value
  }

  branchCentersSubscription: Subscription = new Subscription();

  next(){
    this.branchCentersSubscription = this.m_ScheduleAppointmentService.getAvailableBranchCenters(this.selectedDate).subscribe(data => {
      this.branchCenters = data;
      if(this.branchCenters.length == 0){
        this.m_SnackBar.open(`There isn't available appointment at any branch center for selected date!`, 'Close', { duration: 7000 })
      }
      else
        this.isDateSelected = true
    });
  }

  isNextDisabled() {
    if(this.selectedDate == null || this.selectedDate == undefined || this.selectedDate == '')
      return true
    return false
  }

  calculateAverage(comments: Comment[]): number {
    let sum = 0.0;
    for (let com of comments) {
      sum += com.grade;
    }
    return sum / comments.length;
  }

  sort(sortBy:string, order:string)
  {
    let ascending = false
    if(order == 'asc')
      ascending = true
    this.m_ScheduleAppointmentService.getSortedByRatingBranchCenters(this.branchCenters, sortBy, ascending).pipe(take(1)).subscribe(data => {
      this.branchCenters = data;
    });
  }

  loggedUser: any

  centerIsSelected(centerId:number){
    this.m_UserService.m_Data$.pipe(
      switchMap(data => {
        this.loggedUser = data
        return forkJoin([
          this.m_QuestionnaireService.getAnsweredQuestionnaireByUserId(data?.id),
          this.m_ScheduleAppointmentService.isUserCapableForBloodDonation(data?.id),
        ]).pipe(
          switchMap(([questionaireAnswer,capableAnswer]) => {
            if(questionaireAnswer == null){
              this.m_SnackBar.open(`You need to fill questionire first before procceed with appointment scheduling!`, 'Close', { duration: 7000 })
              this.m_Router.navigate(['/questionnaire'])
            }else{
              if(capableAnswer == true)
                  this.m_SnackBar.open(`Sorry, but you can't schedule appointment, because you already gave blood last 6 months`, 'Close', { duration: 7000 })
              else{
                  console.log('Can schedule appointment')
                  const dto: UserScheduleAppointmentDTO = {
                    selectedDate: this.selectedDate,
                    userId: this.loggedUser?.id,
                    branchCenterId: centerId
                  }
                  return this.m_ScheduleAppointmentService.userScheduleAppointment(dto)
                        .pipe(catchError(res => {
                          this.m_SnackBar.open(`Try again later! `+res , 'Close', { duration: 7000 })
                          this.m_Router.navigate(['/home'])
                          const error = res.error;
                          if (error && error.message) {
                            this.m_Errors.push(error.message);
                            return EMPTY;
                          }
                          this.m_Errors.push(error);
                          return EMPTY;
                        }));
            }
          }
          return EMPTY;
        })
        )
      }),

    ).subscribe(data => {
      this.m_SnackBar.open(`Appointment scheduled successfully.`, 'Close', { duration: 7000 })
      this.m_Router.navigate(['/user-appointments'])
    });
}
}
