import { Component, OnInit } from '@angular/core';
import { FormControl, UntypedFormGroup } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { take } from 'rxjs';
import { BranchCenter } from 'src/app/model/branch-center.model';
import { BranchCenterPage } from 'src/app/model/classes/branch-center-page';
import { __values } from 'tslib';
import { BcAllService } from './bc-all.service';
import { Comment } from 'src/app/model/comment.model';

@Component({
  selector: 'app-bc-all',
  templateUrl: './bc-all.component.html',
  styleUrls: ['./bc-all.component.scss']
})

export class BCAllComponent implements OnInit{

  branchCenters!: BranchCenter[];
  allCountries!: String[];
  allCities!: String[];
  selectedCountry!: string;
  selectedCity!: string;
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
    this.centersService.findAllCountries().pipe(take(1)).subscribe(data => {
      this.allCountries = data;
    });
    this.centersService.findAllCities().pipe(take(1)).subscribe(data => {
      this.allCities = data;
    });
  }

  getCenters(request:any) {
    this.centersService.findAllByPages(request).pipe(take(1)).subscribe( data => {
      this.centersPageInfo = data;
    })
  }

  // searchEntered()
  // {
  //   let request
  //   if(this.pageEvent != null)
  //     request = { name: this.form.get('search-input')?.value ,country: this.selectedCountry,
  //     city: this.selectedCity, size: this.pageEvent.pageSize.toString(),
  //           page: this.pageEvent.pageIndex.toString()}
  //   else
  //     request = { name: this.form.get('search-input')?.value,country: this.selectedCountry,
  //     city: this.selectedCity }
  //   this.getCenters(request)
  // }

  searchCenters()
  {
    let request
    if(this.pageEvent != null)
      request = { name: this.form.get('search-input')?.value ,country: this.selectedCountry,
      city: this.selectedCity, size: this.pageEvent.pageSize.toString(),
            page: this.pageEvent.pageIndex.toString()}
    else
      request = { name: this.form.get('search-input')?.value,country: this.selectedCountry,
      city: this.selectedCity }
    this.getCenters(request)
  }

  calculateAverage(comments: Comment[]): number {
    let sum = 0.0;
    for (let com of comments) {
      sum += com.grade;
    }
    return sum / comments.length;
  }

  // onFilterChange()
  // {
  //   let request
  //   if(this.pageEvent != null)
  //     request = { name: this.form.get('search-input')?.value ,country: this.selectedCountry,
  //     city: this.selectedCity, size: this.pageEvent.pageSize.toString(),
  //           page: this.pageEvent.pageIndex.toString()}
  //   else
  //     request = { name: this.form.get('search-input')?.value, country: this.selectedCountry,
  //     city: this.selectedCity }

  //   this.getCenters(request)
  // }

  nextPage(event: PageEvent){
    this.pageEvent = event
    const request = { page: "0", size: "3", name: "", country: "", city: ""};
    request['page'] = event.pageIndex.toString();
    request['size'] = event.pageSize.toString();
    request['name'] = this.form.get('search-input')?.value;
    request['country'] = this.selectedCountry;
    request['city'] = this.selectedCity;

    this.getCenters(request);
  }

  resetForm(){
    this.form.reset()
    this.selectedCity = ""
    this.selectedCountry = ""
    this.getCenters('')
  }
}
