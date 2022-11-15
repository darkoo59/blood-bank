import { Component } from "@angular/core";
import { UntypedFormControl, UntypedFormGroup, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { catchError, of } from "rxjs";
import { AuthService, LoginDTO } from "../services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent{
  m_Form: UntypedFormGroup = this.formInstance;
  m_Errors: string[] = [];
  
  constructor(private m_AuthService : AuthService, private m_SnackBar: MatSnackBar, private m_Router: Router){ }

  onSubmit() {
    this.m_Errors.length = 0;

    const dto: LoginDTO = this.m_Form.getRawValue()

    this.m_AuthService.login(dto)
      .pipe(catchError(res => {
        console.log(res)
        const errors = res.error.errors

        if (!errors) {
          this.m_Errors.push(res.error)
          return of()
        }

        for (let e in errors) {
          this.m_Errors.push(errors[e])
        }
        return of()
      }))
      .subscribe(_ => {
        this.m_SnackBar.open(`Successfully logged in`, 'Close', { duration: 4000 })
        this.m_Router.navigate(['/home'])
      });
  }

  get formInstance(): UntypedFormGroup {
    return new UntypedFormGroup({
      'email': new UntypedFormControl(null, [Validators.required, Validators.email]),
      'password': new UntypedFormControl(null, [Validators.required, Validators.minLength(8)])
    })
  }
}