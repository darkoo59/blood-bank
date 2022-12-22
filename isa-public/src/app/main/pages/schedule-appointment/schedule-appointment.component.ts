import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ScheduleAppointmentService } from './schedule-appointment.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { BranchCenter } from 'src/app/model/branch-center.model';
import { take } from 'rxjs';
import { Comment } from 'src/app/model/comment.model';

@Component({
  selector: 'app-scedule-appointment',
  templateUrl: './schedule-appointment.component.html',
  styleUrls: ['./schedule-appointment.component.scss']
})
export class ScheduleAppointmentComponent implements OnInit {

  dateForm: UntypedFormGroup = new UntypedFormGroup({
    'selectedDate': new FormControl(null, Validators.required),
  })
  selectedDate: string;
  minimumSelectedDateTime: string
  isDateSelected: boolean = false;
  branchCenters: BranchCenter[] = [];
  constructor(private m_ScheduleAppointmentService: ScheduleAppointmentService, private m_Router: Router, private m_SnackBar: MatSnackBar) {
    let date = new Date()
    date.setDate(date.getDate() + 1)
    this.selectedDate = date.toISOString().slice(0,16)
    this.minimumSelectedDateTime = date.toISOString().slice(0,16)
  }

  ngOnInit() {
  }

  onDateChange(event:any): void {
    this.selectedDate = event.target.value
  }

  next(){
    this.m_ScheduleAppointmentService.getAvailableBranchCenters(this.selectedDate).pipe(take(1)).subscribe(data => {
      this.branchCenters = data;
    });
    if(this.branchCenters.length == 0){
      this.m_SnackBar.open(`There isn't available appointment at any branch center for selected date!`, 'Close', { duration: 7000 })
    }
    else
      this.isDateSelected = true
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
}
