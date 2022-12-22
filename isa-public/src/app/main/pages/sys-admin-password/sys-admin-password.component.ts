import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, UntypedFormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, EMPTY } from 'rxjs';
import { SysAdminPasswordService } from './sys-admin-password.service';

@Component({
  selector: 'app-sys-admin-password',
  templateUrl: './sys-admin-password.component.html',
  styleUrls: ['./sys-admin-password.component.css']
})
export class SysAdminPasswordComponent implements OnInit {

  constructor(private m_SysAdminPasswordService: SysAdminPasswordService, private m_Router: Router) { }

  ngOnInit() {
  }

  form: UntypedFormGroup = new UntypedFormGroup({
    'sa-password': new FormControl(null, [Validators.required, Validators.minLength(8)]),
    'sa-confirm': new FormControl(null, [Validators.required, Validators.minLength(8)])
  }, [SysAdminPasswordComponent.MatchValidator('sa-password', 'sa-confirm')])

  m_Errors: string[] = [];

  onSubmit() : void {
    this.m_Errors.length = 0;
    this.form.updateValueAndValidity();
    if(!this.form.valid) return;

    const password = this.form.get('sa-password')?.value;

    this.m_SysAdminPasswordService.changeAdminPassword(password)
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
      }))
      .subscribe(data => {
        this.m_Router.navigate(['/bc-all']);
      })
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