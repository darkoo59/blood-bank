import { Component } from "@angular/core"
import { UntypedFormControl, UntypedFormGroup, Validators } from '@angular/forms'
import { Router } from "@angular/router"
import { AuthService, RegisterDTO } from "../services/auth.service"
import { MatSnackBar } from '@angular/material/snack-bar'
import { catchError, of } from "rxjs"

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent{
  m_Form1: UntypedFormGroup = new UntypedFormGroup({
    'name': new UntypedFormControl(null, [Validators.required]),
    'surname': new UntypedFormControl(null, [Validators.required]),
    'email': new UntypedFormControl(null, [Validators.required, Validators.email]),
    'password': new UntypedFormControl(null, [Validators.required, Validators.minLength(8)]),
    'confirmPassword': new UntypedFormControl(null, [Validators.required, Validators.minLength(8)])
  })
  m_Form2: UntypedFormGroup = new UntypedFormGroup({
    'street': new UntypedFormControl(null, [Validators.required]),
    'number': new UntypedFormControl(null, [Validators.required]),
    'city': new UntypedFormControl(null, [Validators.required]),
    'country': new UntypedFormControl(null, [Validators.required]),
    'phone': new UntypedFormControl(null, [Validators.required, 
                                    Validators.pattern('^(\\+?\d{1,4}[\s-])?(?!0+\s+,?$)\\d{9,10}\s*,?$')]),
    'nationalId': new UntypedFormControl(null, [Validators.required, Validators.pattern('^[0-9]*$'), 
                                        Validators.minLength(13), Validators.maxLength(13)]),
    'sex': new UntypedFormControl(null, [Validators.required]),
    'occupation': new UntypedFormControl(null, [Validators.required]),
    'information': new UntypedFormControl(null, [Validators.required])
  })
  m_Form: UntypedFormGroup = new UntypedFormGroup({
    'form1': this.m_Form1,
    'form2': this.m_Form2
  });
  m_Errors: string[] = [];
  m_State: boolean = false;

  constructor(private m_AuthService : AuthService, private m_SnackBar: MatSnackBar, private m_Router: Router){ }

  onSubmit() {
    this.m_Errors.length = 0
    Object.keys(this.m_Form2.controls).forEach(field => {
      const control = this.m_Form2.get(field); 
      control?.markAsTouched({ onlySelf: true });
    })

    const dto: RegisterDTO = this.m_Form.getRawValue()
    if (!this.m_Form.valid) return;


    this.m_AuthService.register(dto)
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

  onSwitch() {
    this.m_State = !this.m_State
  }

  onBack() {
    this.onSwitch()
  }

  onForm1Submit() {
    if (!this.m_Form1.valid) return
    this.onSwitch()
  }
}