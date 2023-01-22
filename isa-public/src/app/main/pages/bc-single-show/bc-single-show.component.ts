import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { dataReady } from '@syncfusion/ej2-angular-schedule';
import { LatLng } from 'leaflet';
import { catchError, of, tap } from 'rxjs';
import { Comment } from "src/app/model/comment.model";
import { BcsingleService } from './bc-single-show.service';

@Component({
  selector: 'app-bc-single-show',
  templateUrl: './bc-single-show.component.html',
  styleUrls: ['./bc-single-show.component.scss']
})
export class BcSingleShowComponent implements OnInit {
  m_Location: LatLng | null = null;
  id: string | null = ''
  date: string = ''
  ascending: boolean = false

  m_BCData$ = this.m_BcsingleService.fetchBCData(this.m_Route.snapshot.paramMap.get('id')).pipe(tap(d => {
    console.log(d);
    if (d?.address.lat && d?.address.lng) {
      this.m_Location = new LatLng(d?.address.lat, d?.address.lng)
    }
  }));

  constructor(private m_BcsingleService: BcsingleService, private m_Route: ActivatedRoute, 
    private m_SnackBar: MatSnackBar, private m_Router: Router) { }

  ngOnInit() {
  }

  calculateAverage(comments: Comment[]): number {
    let sum = 0.0;
    for (let com of comments) {
      sum += com.grade;
    }
    return sum / comments.length;
  }

  getDate(dateString: string) {
    const dateArr = dateString.split('T')[0].split('-')
    return `${dateArr[2]}.${dateArr[1]}.${dateArr[0]}.`
  }

  getTime(dateString: string) {
    return dateString.split('T')[1]
  }

  makeAppointment(id: string) {
    this.m_BcsingleService.scheduleAppointment(id).pipe(catchError(res => {
      this.m_SnackBar.open(res.error, 'Close', { duration: 5000 })
      this.m_Router.navigate(['/user-appointments'])
      return of()
    }))
    .subscribe(_ => {
      this.m_SnackBar.open(`Successfully scheduled`, 'Close', { duration: 3000 })
      this.m_Router.navigate(['/user-appointments'])
    });
  }

  sortByDateAndTime(appointments: any[]) {
    this.ascending = !this.ascending
    appointments.sort((a, b) => {
      const dateA = new Date(a.start)
      const dateB = new Date(b.start)
      if (dateA.getDate() > dateB.getDate()) {
        return this.ascending ? -1 : 1
      } else if (dateA.getDate() < dateB.getDate()) {
        return this.ascending ? 1 : -1
      } else {
        const timeA = this.getTime(a.start)
        const timeB = this.getTime(b.start)
        return this.ascending ? timeA.localeCompare(timeB) : timeB.localeCompare(timeA)
      }
    })
  }
}
