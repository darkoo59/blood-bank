import { Component } from "@angular/core";
import { UntypedFormControl, UntypedFormGroup, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { Router } from "@angular/router";
import { catchError, EMPTY, of } from "rxjs";
import { LoadingService } from "src/app/services/loading.service";
import { UserService } from "src/app/services/user.service";
import { AuthService, LoginDTO } from "../../services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent{
  m_Form: UntypedFormGroup = this.formInstance
  m_Error: string | null = null
  
  constructor(private m_AuthService : AuthService, 
              private m_SnackBar: MatSnackBar, 
              private m_Router: Router,
              private m_UserService: UserService,
              private m_LoadingService: LoadingService){ }

  onSubmit() {
    this.m_Error = null;
    Object.keys(this.m_Form.controls).forEach(field => {
      const control = this.m_Form.get(field); 
      control?.markAsTouched({ onlySelf: true });
    })

    if (!this.m_Form.valid) return;

    const dto: LoginDTO = this.m_Form.getRawValue()

    this.m_LoadingService.setLoading = true
    this.m_AuthService.login(dto)
      .pipe(catchError(res => {
        if (res.error) {
          this.m_Error = res.error.errorMessage
          this.m_LoadingService.setLoading = false
        }
        return EMPTY
      }))
      .subscribe(userData => {
        this.m_LoadingService.setLoading = false;
        this.m_SnackBar.open(`Successfully logged in`, 'Close', { duration: 4000 })
        this.m_Router.navigate(['/home'])
        this.m_UserService.checkIfPasswordChanged(userData);
      });
  }

  get formInstance(): UntypedFormGroup {
    return new UntypedFormGroup({
      'email': new UntypedFormControl(null, [Validators.required, Validators.email]),
      'password': new UntypedFormControl(null, [Validators.required, Validators.minLength(8)])
    })
  }
}