import { Component, OnDestroy, OnInit} from "@angular/core";
import { FormControl, UntypedFormGroup, Validators } from "@angular/forms";
import { LatLng } from "leaflet";
import { catchError, EMPTY, of, Subject, switchMap, tap } from "rxjs";
import { User } from "src/app/model/user.model";
import { UserService } from "src/app/services/user.service";
import { MatSnackBar } from '@angular/material/snack-bar'
import { ThisReceiver } from "@angular/compiler";
import { LoadingService } from "src/app/services/loading.service";
import { Router } from "@angular/router";

@Component({
  templateUrl: './profile-overview.component.html',
  styleUrls: ['./profile-overview.component.css']
})
export class ProfileOverviewComponent implements OnDestroy,OnInit {
  userData!: User;
  mapInput: LatLng | null = new LatLng(45.2549038, 19.8382191);
  userToUpdate!: User;
  errors: string[] = [];
  addressData!: any;
  m_Error$ = this.userService.m_Error$;

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

  // m_Submit$: Subject<any> = new Subject().pipe(switchMap(_ => {
  //   this.userService.clearError();
  //   if (!this.form.valid) return EMPTY;
  //   const data = {...this.form.getRawValue(), ...this.form.getRawValue()}
  //   const dto: User = {
  //     id: this.userData.id,
  //     firstname: data.firstname,
  //     lastname: data.lastname,
  //     email: data.email,
  //     address: this.addressData,
  //     phone: data.phoneNumber,
  //     nationalId: data.nationalId,
  //     sex: data.sex,
  //     occupation: data.occupation,
  //     information: data.information
  //   }

  //   return this.userService.update(dto).pipe(tap(_ => {
  //     this.m_SnackBar.open(`Profile successfully updated`, 'close', { duration: 4000 });
  //     this.m_Router.navigate(['/home']);
  //   }));
  // })) as Subject<any>;

  constructor(private userService: UserService, private m_SnackBar: MatSnackBar, private m_LoadingService: LoadingService,
    private m_Router: Router){
}

  ngOnInit(): void {
    this.userService.fetchUserData();
    this.userService.m_Data$.subscribe((data: any) => {
    this.userData = data;
    this.addressData = data.address;
    if(this.addressData != null){
      this.mapInput = new LatLng(this.addressData.lat, this.addressData.lng)
    }
  })
}

  ngOnDestroy(): void {
    this.userService.clearError();
  }

  onSubmit(): void{
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
    this.userService.update(dto).pipe()
    .subscribe((_:any) => {
      this.m_SnackBar.open(`Successfully updated user informations`, 'Close', { duration: 7000 })
      location.reload()
    })
  }

}
