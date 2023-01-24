import { Component, OnDestroy } from "@angular/core";
import { AbstractControl, UntypedFormControl, UntypedFormGroup, ValidationErrors, ValidatorFn, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { EMPTY, Subject, switchMap, tap } from "rxjs";
import { ChangeUserPasswordDTO, UserService } from "src/app/services/user.service";

@Component({
  templateUrl: './profile-auth.component.html',
  styleUrls: ['./profile-auth.component.scss']
})
export class ProfileAuthComponent implements OnDestroy {
  m_PasswordForm: UntypedFormGroup = new UntypedFormGroup({
    'oldPassword': new UntypedFormControl(null, [Validators.required]),
    'newPassword': new UntypedFormControl(null, [Validators.required]),
    'confirmPassword': new UntypedFormControl(null, [Validators.required])
  }, [ProfileAuthComponent.MatchValidator('newPassword', 'confirmPassword')]);;
  m_Error$ = this.m_UserService.m_Error$;

  m_Submit$: Subject<any> = new Subject().pipe(switchMap(_ => {
    this.m_UserService.clearError();
    if (!this.m_PasswordForm.valid) return EMPTY;
    const raw = this.m_PasswordForm.getRawValue();
    const dto: ChangeUserPasswordDTO = { oldPassword: raw['oldPassword'], newPassword: raw['newPassword'] }

    return this.m_UserService.changeUserPassword(dto).pipe(tap(_ => {
      this.m_SnackBar.open(`Password changed successfully`, 'close', { duration: 4000 });
      this.m_Router.navigate(['/profile/overview']);
    }));
  })) as Subject<any>;

  constructor(private m_UserService: UserService, private m_Router: Router, private m_SnackBar: MatSnackBar) { }

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

  ngOnDestroy(): void {
    this.m_UserService.clearError();
  }
}