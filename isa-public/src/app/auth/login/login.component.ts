import { Component } from "@angular/core";
import { UntypedFormControl, UntypedFormGroup, Validators } from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent{
  m_Form: UntypedFormGroup = this.formInstance;
  m_Errors: string[] = [];
  
  constructor(){ }

  onSubmit() {
    this.m_Errors.length = 0;
  }

  get formInstance(): UntypedFormGroup {
    return new UntypedFormGroup({
      'email': new UntypedFormControl(null, [Validators.required, Validators.email]),
      'password': new UntypedFormControl(null, [Validators.required, Validators.minLength(8)])
    })
  }
}