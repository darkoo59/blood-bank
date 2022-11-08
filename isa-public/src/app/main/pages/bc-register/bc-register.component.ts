import { Component, OnInit } from '@angular/core';
import { FormControl,UntypedFormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-bc-register',
  templateUrl: './bc-register.component.html',
  styleUrls: ['./bc-register.component.css']
})
export class BCRegisterComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  form: UntypedFormGroup = new UntypedFormGroup({

  })

}
