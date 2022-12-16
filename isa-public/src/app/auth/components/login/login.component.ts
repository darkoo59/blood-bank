import { Component } from "@angular/core";
import { UntypedFormControl, UntypedFormGroup, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { catchError, EMPTY, of } from "rxjs";
import { AuthService, LoginDTO } from "../../services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent{
  m_Form: UntypedFormGroup = this.formInstance
  m_Error: string | null = null
  
  constructor(private m_AuthService : AuthService, private m_SnackBar: MatSnackBar, private m_Router: Router){ }

  onSubmit() {
    this.m_Error = null;
    Object.keys(this.m_Form.controls).forEach(field => {
      const control = this.m_Form.get(field); 
      control?.markAsTouched({ onlySelf: true });
    })

    if (!this.m_Form.valid) return;

    const dto: LoginDTO = this.m_Form.getRawValue()

    this.m_AuthService.login(dto)
      .pipe(catchError(res => {
        if (res.error) {
          this.m_Error = res.error.errorMessage
        }
        return EMPTY
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