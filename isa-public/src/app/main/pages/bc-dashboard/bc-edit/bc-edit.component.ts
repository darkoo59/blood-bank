import { Component } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { LatLng } from "leaflet";
import { exhaustMap, Subject, tap } from "rxjs";
import { Address } from "src/app/model/address.model";
import { BranchCenter } from "src/app/model/branch-center.model";
import { BCDashboardService, BCUpdateDTO } from "../bc-dashboard.service";

@Component({
  selector: "app-bc-edit",
  templateUrl: "./bc-edit.component.html",
  styleUrls: ["./bc-edit.component.scss"]
})
export class BCEditComponent {
  m_BCData$ = this.m_BCDashboardService.m_BCData$.pipe(
    tap((data: BranchCenter | null) => {
      this.setData(data);
    })
  );

  m_Update$: Subject<BCUpdateDTO> = new Subject<BCUpdateDTO>().pipe(
    exhaustMap((dto: BCUpdateDTO) => {
      return this.m_BCDashboardService.patchBCData(dto).pipe(
        tap(_ => this.m_Router.navigate(['/bc-dashboard']))
      );
    })
  ) as Subject<BCUpdateDTO>;

  m_Form: FormGroup | null = null;
  m_Address: Address | null = null;
  m_MapInput: LatLng | null = null;
  m_LocationEditActive: boolean = false;

  constructor(private m_BCDashboardService: BCDashboardService, private m_Router: Router) { }

  setData(data: BranchCenter | null): void {
    if (data == null) return;

    this.m_Form = new FormGroup({
      'id': new FormControl({ value: data.id, disabled: true }),
      'name': new FormControl(data.name, Validators.required),
      'description': new FormControl(data.description),
    });
    if(data.address.lat && data.address.lng){
      this.m_MapInput = new LatLng(data.address.lat, data.address.lng);
    }
    this.m_Address = data.address;
    console.log(data.address);
  }

  onSubmit(): void {
    if (!this.m_Address) return;
    const dto: BCUpdateDTO = this.m_Form?.getRawValue();
    dto.address = this.m_Address;
    
    console.log(dto);

    this.m_Update$.next(dto);
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