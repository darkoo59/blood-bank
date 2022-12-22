import { Component, OnInit } from '@angular/core';
import { FormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LatLng } from 'leaflet';
import { catchError, EMPTY } from 'rxjs';
import { Address } from 'src/app/model/address.model';
import { SysAdminRegisterDTO, SysAdminRegisterService } from './sys-admin-register.service';

@Component({
  selector: 'app-sys-admin-register',
  templateUrl: './sys-admin-register.component.html',
  styleUrls: ['./sys-admin-register.component.css']
})
export class SysAdminRegisterComponent implements OnInit {

  constructor(private m_SysAdminRegisterService: SysAdminRegisterService, private m_Router: Router) { }

  ngOnInit() {
  }

  form: UntypedFormGroup = new UntypedFormGroup({
    'sa-name': new FormControl(null, Validators.required),
    'sa-surname': new FormControl(null, Validators.required),
    'sa-email': new FormControl(null, [Validators.required, Validators.email]),
    'sa-phone': new FormControl(null, [Validators.required, Validators.pattern('^(\\+?\d{1,4}[\s-])?(?!0+\s+,?$)\\d{9,10}\s*,?$')]),
    'sa-nat-id': new FormControl(null, [Validators.required, Validators.pattern('^[0-9]*$'),
                                Validators.minLength(13), Validators.maxLength(13)]),
    'sa-sex': new FormControl(null, Validators.required),
    'sa-occupation': new FormControl(null, Validators.required),
    'sa-info': new FormControl(null, Validators.required)                            
  });

  m_Errors: string[] = [];
  m_MapInput: LatLng | null = new LatLng(45.2549038, 19.8382191);
  m_Address: Address | null = null;

  onSubmit() : void {
    this.m_Errors.length = 0;
    this.form.updateValueAndValidity();
    if(!this.form.valid || !this.m_Address) return;

    const dto: SysAdminRegisterDTO = {
      firstname: this.form.get('sa-name')?.value,
      lastname: this.form.get('sa-surname')?.value,
      email: this.form.get('sa-email')?.value,
      phone: this.form.get('sa-phone')?.value,
      nationalId: this.form.get('sa-nat-id')?.value,
      occupation: this.form.get('sa-occupation')?.value,
      information: this.form.get('sa-info')?.value,
      sex: this.form.get('sa-sex')?.value,
      address: this.m_Address
    }

    console.log(dto)

    this.m_SysAdminRegisterService.registerAdmin(dto)
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
