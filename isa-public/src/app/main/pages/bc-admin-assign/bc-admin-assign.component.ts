import { Component, OnInit } from '@angular/core';
import { FormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, EMPTY, take } from 'rxjs';
import { BCAdmin } from 'src/app/model/bc-admin.model';
import { BranchCenter } from 'src/app/model/branch-center.model';
import { BCAAssignDTO, BCAdminAssignService } from './bc-admin-assign.service';

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

  constructor(private service: BCAdminAssignService, private m_Router: Router) { }

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

  m_Errors: string[] = [];

  onSubmit() : void {
    this.m_Errors.length = 0;
    
    this.form.updateValueAndValidity();
    if (!this.form.valid) return;

    const branchCenter = this.form.get('bca-center')?.value;
    const bcAdmin = this.form.get('bca-admin')?.value;
    const dto: BCAAssignDTO = {
      bcAdminEmail: bcAdmin.email,
      centerId: branchCenter.id
    }

    this.service.assignAdminToCenter(dto)
      .pipe(catchError(res => {
        console.log(res);
        this.m_Router.navigate(['/bc-all'])
        const error = res.error;
        if (error && error.message) {
          this.m_Errors.push(error.message);
          return EMPTY;
        }
        this.m_Errors.push(error);
        return EMPTY;
      })).subscribe(data => {
        this.m_Router.navigate(['/bc-all'])
      });
  
  }

}
