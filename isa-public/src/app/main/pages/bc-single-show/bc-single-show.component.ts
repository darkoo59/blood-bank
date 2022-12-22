import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LatLng } from 'leaflet';
import { tap } from 'rxjs';
import { BCDashboardService } from 'src/app/core/bc-dashboard/services/bc-dashboard.service';
import { Comment } from "src/app/model/comment.model";
import { BcsingleService } from '../services/bcsingle.service';

@Component({
  selector: 'app-bc-single-show',
  templateUrl: './bc-single-show.component.html',
  styleUrls: ['./bc-single-show.component.scss']
})
export class BcSingleShowComponent implements OnInit {
  m_Location: LatLng | null = null;
  id: string | null = ''
  
  

  m_BCData$ = this.m_BcsingleService.fetchBCData(this.m_Route.snapshot.paramMap.get('id')).pipe(tap(d => {
    console.log(d);
    if (d?.address.lat && d?.address.lng) {
      this.m_Location = new LatLng(d?.address.lat, d?.address.lng)
    }
  }));

  constructor(private m_BcsingleService: BcsingleService, private m_Route: ActivatedRoute) { }

  ngOnInit() {
  }

  calculateAverage(comments: Comment[]): number {
    let sum = 0.0;
    for (let com of comments) {
      sum += com.grade;
    }
    return sum / comments.length;
  }
}
