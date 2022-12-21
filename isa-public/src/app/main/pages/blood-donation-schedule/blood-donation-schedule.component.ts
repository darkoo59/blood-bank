import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { take } from 'rxjs';
import { BloodDonationScheduleService } from './blood-donation-schedule.service';

interface WorkingHoursDTO {
  start: string; // start time of the working hours, in the format "HH:mm"
  end: string; // end time of the working hours, in the format "HH:mm"
}

@Component({
  selector: 'app-blood-donation-schedule',
  templateUrl: './blood-donation-schedule.component.html',
  styleUrls: ['./blood-donation-schedule.component.scss']
})
export class BloodDonationScheduleComponent implements OnInit {

  constructor(private m_Router: Router, private m_ScheduleService: BloodDonationScheduleService) {
    alert('darko')
   }
  workingHours!:WorkingHoursDTO

  ngOnInit() {
    this.m_ScheduleService.getBranchCenterWorkingHours().pipe(take(1)).subscribe(data => {
      this.workingHours = data;
    });
  }

}
