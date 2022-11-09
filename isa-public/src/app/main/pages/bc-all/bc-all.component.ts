import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { take } from 'rxjs';
import { BranchCenter } from 'src/app/model/branch-center.model';
import { BranchCenterPage } from 'src/app/model/classes/branch-center-page';
import { BcAllService } from './bc-all.service';

@Component({
  selector: 'app-bc-all',
  templateUrl: './bc-all.component.html',
  styleUrls: ['./bc-all.component.scss']
})

export class BCAllComponent implements OnInit{

  branchCenters!: BranchCenter[];
  centersPageInfo!: BranchCenterPage;

  constructor(private centersService: BcAllService) { }

  ngOnInit() {
    this.getCenters({page: "0", size:"1"});
    this.centersService.findAll().pipe(take(1)).subscribe(data => {
      this.branchCenters = data;
    });
  }

  getCenters(request:any) {
    this.centersService.findAllByPages(request).pipe(take(1)).subscribe( data => {
      this.centersPageInfo = data;
    })
  }

  nextPage(event: PageEvent){
    const request = { page: "0", size: "3", name: ""};
    request['page'] = event.pageIndex.toString();
    request['size'] = event.pageSize.toString();

    this.getCenters(request);
  }
}
