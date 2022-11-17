import { Component, OnInit } from "@angular/core";
import { FormControl, UntypedFormGroup, Validators } from "@angular/forms";
import { LatLng } from "leaflet";
import { catchError, of, tap } from "rxjs";
import { User } from "src/app/model/user.model";
import { UserService } from "src/app/services/user.service";
import { MatSnackBar } from '@angular/material/snack-bar'
import { ThisReceiver } from "@angular/compiler";
import { LoadingService } from "src/app/services/loading.service";

@Component({
  templateUrl: './profile-overview.component.html',
  styleUrls: ['./profile-overview.component.css']
})
export class ProfileOverviewComponent implements OnInit {
  userData!: User;
  mapInput: LatLng | null = null;
  userToUpdate!: User;
  errors: string[] = [];
  addressData!: any;

  form: UntypedFormGroup = new UntypedFormGroup({
    'firstname': new FormControl(null, Validators.required),
    'lastname': new FormControl(null, Validators.required),
    'email': new FormControl(null),
    'phoneNumber': new FormControl(null, [Validators.required,Validators.pattern('^(\\+?\d{1,4}[\s-])?(?!0+\s+,?$)\\d{9,10}\s*,?$')]),
    'occupation': new FormControl(null, Validators.required),
    'sex': new FormControl(null, Validators.required),
    'information': new FormControl(null, Validators.required),
    'nationalId': new FormControl(null, [Validators.required, Validators.pattern('^[0-9]*$'),
    Validators.minLength(13), Validators.maxLength(13)])
  })

  constructor(private userService: UserService, private m_SnackBar: MatSnackBar, private m_LoadingService: LoadingService){

  }

  ngOnInit(): void {
    this.userService.fetchUserData();
   this.userService.m_Data$.subscribe((data: any) => {
    this.userData = data;
    this.addressData = data.address;
   });
    }

    locationHandler(data: any): void {
      if (data.error) {
        this.userData.address = null;
        return;
      }
      const address = data.address;
      if(address != null){
        this.addressData = {
          lat: parseFloat(data.lat) || null,
          lng: parseFloat(data.lon) || null,
          city: address.city || address.town || null,
          street: address.road || null,
          country: address.country || address.state || null,
          number: address.house_number || null
        }
      }
    }

    isFormValid(): boolean {
      if(this.form.valid)
        return true;
      return false;
    }

  refreshUserData(){
    this.userService.fetchUserData();
    this.userService.m_Data$.subscribe((data: any) => {
     this.userData = data;
     this.addressData = data.address;
    });
  }

  onSubmit(){
    this.errors.length = 0
    Object.keys(this.form.controls).forEach(field => {
      const control = this.form.get(field);
      control?.markAsTouched({ onlySelf: true });
    })
    if (!this.form.valid) return;
    const data = {...this.form.getRawValue(), ...this.form.getRawValue()}
    const dto: User = {
      id: this.userData.id,
      firstname: data.firstname,
      lastname: data.lastname,
      email: data.email,
      address: this.addressData,
      phone: data.phoneNumber,
      nationalId: data.nationalId,
      sex: data.sex,
      occupation: data.occupation,
      information: data.information
    }
    this.userService.update(dto).pipe(catchError(res => {
      console.log(res)
      const Errors = res.error.errors

      if (!Errors) {
        this.errors.push(res.error)
        return of()
      }

      for (let e in Errors) {
        this.errors.push(Errors[e])
      }
      return of()
    }))
    .subscribe((_:any) => {
      this.m_SnackBar.open(`Successfully updated user informations`, 'Close', { duration: 4000 })
      this.refreshUserData()
    })
    // this.userToUpdate.id = this.userData.id;
    // this.userToUpdate.address = this.userData.address;
    // this.userToUpdate.email = this.userData.email;
    // this.userToUpdate.firstname = this.userData.firstname
    // this.userToUpdate.information = this.userData.information
    // this.userToUpdate.lastname = this.userData.lastname
    // this.userToUpdate.nationalId = this.userData.nationalId;
    // this.userToUpdate.occupation = this.userData.occupation;
    // this.userToUpdate.phone = this.userData.phone;
    // this.userToUpdate.sex = this.userData.sex;
    // this.userService.update(this.userToUpdate)
  }

}
