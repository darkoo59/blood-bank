import { Component } from "@angular/core"
import { UntypedFormControl, UntypedFormGroup, Validators } from '@angular/forms'
import { Router } from "@angular/router";
import { AuthService, RegisterDTO } from "../services/auth.service";
import { MatSnackBar } from '@angular/material/snack-bar'
import { catchError, of } from "rxjs";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent{
  m_Form: UntypedFormGroup = this.formInstance;
  m_Errors: string[] = [];

  constructor(private m_AuthService : AuthService, private m_SnackBar: MatSnackBar, private m_Router: Router){ }

  onSubmit() {
    this.m_Errors.length = 0;
    const dto: RegisterDTO = this.m_Form.getRawValue()
    if (!this.m_Form.valid) return;


    this.m_AuthService.register(dto)
      .pipe(catchError(res => {
        console.log(res);
        const errors = res.error.errors;

        if (!errors) {
          this.m_Errors.push(res.error);
          return of();
        }

        for (let e in errors) {
          this.m_Errors.push(errors[e]);
        }
        return of();
      }))
      .subscribe(_ => {
        this.m_SnackBar.open(`Successfully logged in`, 'Close', { duration: 4000 });
        this.m_Router.navigate(['/home']);
      });
  }

  get formInstance(): UntypedFormGroup {
    return new UntypedFormGroup({
      'name': new UntypedFormControl(null, [Validators.required]),
      'surname': new UntypedFormControl(null, [Validators.required]),
      'email': new UntypedFormControl(null, [Validators.required, Validators.email]),
      'password': new UntypedFormControl(null, [Validators.required, Validators.minLength(8)]),
      'confirm_password': new UntypedFormControl(null, [Validators.required, Validators.minLength(8)]),
      'address': new UntypedFormControl(null, [Validators.required]),
      'city': new UntypedFormControl(null, [Validators.required]),
      'country': new UntypedFormControl(null, [Validators.required]),
      'phone': new UntypedFormControl(null, [Validators.required]),
      'id': new UntypedFormControl(null, [Validators.required, Validators.pattern('^[0-9]*$'), 
                                    Validators.minLength(13), Validators.maxLength(13)]),
      'sex': new UntypedFormControl(null, [Validators.required])
    })
  }
}