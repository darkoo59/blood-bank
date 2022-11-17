import { Component } from "@angular/core";
import { LatLng } from "leaflet";
import { tap } from "rxjs";
import { BCDashboardService } from "../../services/bc-dashboard.service";
import { Comment } from "src/app/model/comment.model";

@Component({
  selector: "app-bc-view",
  templateUrl: "./bc-view.component.html",
  styleUrls: ["./bc-view.component.scss"]
})
export class BCViewComponent {
  m_BCData$ = this.m_BCDashboardService.m_Data$.pipe(tap(d => {
    console.log(d);
    if (d?.address.lat && d?.address.lng) {
      this.m_Location = new LatLng(d?.address.lat, d?.address.lng)
    }
  }));

  m_Location: LatLng | null = null;

  constructor(private m_BCDashboardService: BCDashboardService) { }

  calculateAverage(comments: Comment[]): number {
    let sum = 0.0;
    for (let com of comments) {
      sum += com.grade;
    }
    return sum / comments.length;
  }
}