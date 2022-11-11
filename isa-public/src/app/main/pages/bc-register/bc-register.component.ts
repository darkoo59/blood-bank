import { Component, OnInit } from '@angular/core';
import { FormControl,UntypedFormGroup, Validators } from '@angular/forms';
import { catchError, EMPTY } from 'rxjs';
import { BCRegisterDTO, BCRegisterService } from './bc-register.service';

@Component({
  selector: 'app-bc-register',
  templateUrl: './bc-register.component.html',
  styleUrls: ['./bc-register.component.css']
})
export class BCRegisterComponent implements OnInit {

  constructor(private m_BCRegisterService: BCRegisterService) { }

  ngOnInit() {
  }

  form: UntypedFormGroup = new UntypedFormGroup({
    'bc-name': new FormControl(null, Validators.required),
    'bc-country': new FormControl(null, [Validators.required, Validators.pattern("^[a-zA-Z ]+$")]),
    'bc-city': new FormControl(null, [Validators.required, Validators.pattern("^[a-zA-Z ]+$")]),
    'bc-street': new FormControl(null, [Validators.required, Validators.pattern("^[a-zA-Z ]+$")]),
    'bc-number': new FormControl(null, Validators.required),
    'bc-description': new FormControl(null)
  })

  m_Errors: string[] = [];

  onSubmit() : void {

    this.m_Errors.length = 0;
     const dto: BCRegisterDTO = {
      name: this.form.get('bc-name')?.value,
      country: this.form.get('bc-country')?.value,
      city: this.form.get('bc-city')?.value,
      street: this.form.get('bc-street')?.value,
      number: this.form.get('bc-number')?.value,
      description: this.form.get('bc-description')?.value
    }

    
    this.form.updateValueAndValidity(); 
    if (!this.form.valid) return;

    this.m_BCRegisterService.registerCenter(dto)
      .pipe(catchError(res => {
        console.log(res);
        const error = res.error;
        if (error && error.message) {
          this.m_Errors.push(error.message);
          return EMPTY;
        }
        this.m_Errors.push(error);
        return EMPTY;
      }))
      .subscribe();

  }

}
