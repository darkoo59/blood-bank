import { Component, OnInit } from '@angular/core';
import { FormControl,UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LatLng } from 'leaflet';
import { catchError, EMPTY } from 'rxjs';
import { Address } from 'src/app/model/address.model';
import { BCRegisterDTO, BCRegisterService } from './bc-register.service';

@Component({
  selector: 'app-bc-register',
  templateUrl: './bc-register.component.html',
  styleUrls: ['./bc-register.component.css']
})
export class BCRegisterComponent implements OnInit {

  constructor(private m_BCRegisterService: BCRegisterService, private m_Router: Router) { }

  ngOnInit() {
  }

  form: UntypedFormGroup = new UntypedFormGroup({
    'bc-name': new FormControl(null, Validators.required),
    'bc-description': new FormControl(null),
    'bc-monday': new FormControl(null),
    'bc-tuesday': new FormControl(null),
    'bc-wednesday': new FormControl(null),
    'bc-thursday': new FormControl(null),
    'bc-friday': new FormControl(null),
    'bc-saturday': new FormControl(null),
    'bc-sunday': new FormControl(null),
    'bc-start': new FormControl(null, Validators.required),
    'bc-end': new FormControl(null, Validators.required)
  })

  m_Errors: string[] = [];
  m_Address: Address | null = null;
  m_MapInput: LatLng | null = new LatLng(45.2549038, 19.8382191);



  onSubmit() : void {

    this.m_Errors.length = 0;

    this.form.updateValueAndValidity(); 
    if (!this.form.valid || !this.m_Address) return;
    const dto: BCRegisterDTO = {
      name: this.form.get('bc-name')?.value,
      description: this.form.get('bc-description')?.value,
      address: this.m_Address,
      monday: this.form.get('bc-monday')?.value,
      tuesday: this.form.get('bc-tuesday')?.value,
      wednesday: this.form.get('bc-wednesday')?.value,
      thursday: this.form.get('bc-thursday')?.value,
      friday: this.form.get('bc-friday')?.value,
      saturday: this.form.get('bc-saturday')?.value,
      sunday: this.form.get('bc-sunday')?.value,
      startTime: this.form.get('bc-start')?.value,
      endTime: this.form.get('bc-end')?.value
    }

    console.log(dto);

    this.m_BCRegisterService.registerCenter(dto)
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
        this.m_Router.navigate(['/bc-all'])
      });
  }

  locationHandler(data: any): void {
    if (data.error) {
      this.m_Address = null;
      return;
    }
    const address = data.address;
    this.m_Address = {
      lat: parseFloat(data.lat) || null,
      lng: parseFloat(data.lon) || null,
      city: address.city || address.town || null,
      street: address.road || null,
      country: address.country || address.state || null,
      number: address.house_number || null
    }
  }

}
