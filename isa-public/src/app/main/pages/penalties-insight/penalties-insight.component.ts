import { Component, OnInit } from '@angular/core';
import { take } from 'rxjs';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-penalties-insight',
  templateUrl: './penalties-insight.component.html',
  styleUrls: ['./penalties-insight.component.scss']
})
export class PenaltiesInsightComponent implements OnInit {

  penalties: number = 0

  constructor(private m_UserService: UserService) { }

  ngOnInit() {
    this.m_UserService.m_Data$.pipe(take(1)).subscribe(data => {
      this.m_UserService.getUserPenalties(data?.id).pipe(take(1)).subscribe(resp => {;
        this.penalties = resp
      })
    })
  }

  hasPenalties() {
    if (this.penalties > 0) return true
    return false
  }

  getFirstDayOfNextMonth() {
    var now = new Date()
    var year = now.getFullYear()
    var month = now.getMonth() + 1
    if (month > 11) {
      month = 0
      year++
    }
    var firstDay = new Date(year, month, 1)
    var day = firstDay.getDate()
    var month = firstDay.getMonth() + 1
    return day + "." + month + "." + year + "."
  }
}
