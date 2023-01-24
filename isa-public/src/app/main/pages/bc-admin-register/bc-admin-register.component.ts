import { EmptyExpr } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, UntypedFormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
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
  matchFlag = false;
  constructor(private m_BCAdminRegisterService: BCAdminRegisterService, private m_Router: Router) {
  }

  ngOnInit() {
  }

  form: UntypedFormGroup = new UntypedFormGroup({
    'bca-name': new FormControl(null, Validators.required),
    'bca-surname': new FormControl(null, Validators.required),
    'bca-password': new FormControl(null, Validators.required),
    'bca-repeat': new FormControl(null, Validators.required),
    'bca-email': new FormControl(null, [Validators.required, Validators.email])
  }, [BCAdminRegisterComponent.MatchValidator('bca-password', 'bca-repeat')])  


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
      })).subscribe(data => {
        this.m_Router.navigate(['/bc-all'])});
  }

  static MatchValidator(source: string, target: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const sourceCtrl = control.get(source);
      const targetCtrl = control.get(target);
      if (sourceCtrl && targetCtrl && sourceCtrl.value !== targetCtrl.value) {
        sourceCtrl?.setErrors({ mismatch: true });
        targetCtrl?.setErrors({ mismatch: true });
        return { mismatch: true };
      }
      if (sourceCtrl?.hasError('mismatch')) sourceCtrl.updateValueAndValidity();
      if (targetCtrl?.hasError('mismatch')) targetCtrl.updateValueAndValidity();
      return null;
    };
  }

}
