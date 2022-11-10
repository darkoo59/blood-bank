import { Component, OnInit } from '@angular/core';
import { FormControl, UntypedFormGroup } from '@angular/forms';
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
  form: UntypedFormGroup = new UntypedFormGroup({
    'search-input': new FormControl(null)
  })
  pageEvent!: PageEvent;

  constructor(private centersService: BcAllService) { }

  ngOnInit() {
    this.getCenters({page: "0", size:"3"});
    this.centersService.findAll().pipe(take(1)).subscribe(data => {
      this.branchCenters = data;
    });
  }

  getCenters(request:any) {
    this.centersService.findAllByPages(request).pipe(take(1)).subscribe( data => {
      this.centersPageInfo = data;
    })
  }

  searchEntered()
  {
    let request
    if(this.pageEvent != null)
      request = { name: this.form.get('search-input')?.value , size: this.pageEvent.pageSize.toString(),
            page: this.pageEvent.pageIndex.toString()}
    else
      request = { name: this.form.get('search-input')?.value }
    this.getCenters(request)
  }

  nextPage(event: PageEvent){
    this.pageEvent = event
    const request = { page: "0", size: "3", name: ""};
    request['page'] = event.pageIndex.toString();
    request['size'] = event.pageSize.toString();

    this.getCenters(request);
  }
}
