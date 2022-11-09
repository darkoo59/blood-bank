import { EmptyExpr } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { FormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { catchError, EMPTY } from 'rxjs';
import { BCAdminRegisterService, BCARegisterDTO } from './bc-admin-register.service';

@Component({
  selector: 'app-bc-admin-register',
  templateUrl: './bc-admin-register.component.html',
  styleUrls: ['./bc-admin-register.component.css']
})
export class BCAdminRegisterComponent implements OnInit {
  hide = true;
  hide2 = true;
  constructor(private m_BCAdminRegisterService: BCAdminRegisterService) { }

  ngOnInit() {
  }

  form: UntypedFormGroup = new UntypedFormGroup({
    'bca-name': new FormControl(null, Validators.required),
    'bca-surname': new FormControl(null, Validators.required),
    'bca-password': new FormControl(null, Validators.required),
    'bca-repeat': new FormControl(null, Validators.required),
    'bca-email': new FormControl(null, [Validators.required, Validators.email])
  })

  m_Errors: string[] = [];

  onSubmit() : void {
    this.m_Errors.length = 0;
    const dto: BCARegisterDTO = {
      name: this.form.get('bca-name')?.value,
      surname: this.form.get('bca-surname')?.value,
      email: this.form.get('bca-email')?.value,
      password: this.form.get('bca-password')?.value
    }

    this.form.updateValueAndValidity();
    if (!this.form.valid) return;

    this.m_BCAdminRegisterService.registerBCAdmin(dto)
      .pipe(catchError(res => {
        console.log(res);
        const error = res.error;
        if (error && error.message) {
          this.m_Errors.push(error.message);
          return EMPTY;
        }
        this.m_Errors.push(error);
        return EMPTY;
      })).subscribe();

  }

}
