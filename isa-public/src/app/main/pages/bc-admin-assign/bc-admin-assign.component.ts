import { Component, OnInit } from '@angular/core';
import { FormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { take } from 'rxjs';
import { BCAdmin } from 'src/app/model/bc-admin.model';
import { BranchCenter } from 'src/app/model/branch-center.model';
import { BCAdminAssignService } from './bc-admin-assign.service';

@Component({
  selector: 'app-bc-admin-assign',
  templateUrl: './bc-admin-assign.component.html',
  styleUrls: ['./bc-admin-assign.component.css']
})
export class BCAdminAssignComponent implements OnInit {
  
  form: UntypedFormGroup = new UntypedFormGroup({
    'bca-center': new FormControl(null, Validators.required),
    'bca-admin': new FormControl(null, Validators.required)

  })

  adminList!: BCAdmin[];
  centerList!: BranchCenter[]; 

  constructor(private service: BCAdminAssignService) { }

  ngOnInit() {
    this.service.findAll().pipe(take(1))
      .subscribe(data => {
        this.centerList = data;
      });

    this.service.findUnassinged().pipe(take(1))
      .subscribe(data => {
        this.adminList = data;
      })
  }

}
