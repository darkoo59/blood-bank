import { Component } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { exhaustMap, Subject, tap } from "rxjs";
import { BranchCenter } from "src/app/model/branch-center.model";
import { BCDashboardService, BCUpdateDTO } from "./bc-dashboard.service";

@Component({
  selector: "app-bc-dashboard",
  templateUrl: "./bc-dashboard.component.html",
  styleUrls: ["./bc-dashboard.component.scss"]
})
export class BCDashboardComponent {
  m_BCData$ = this.m_BCDashboardService.m_BCData$.pipe(
    tap((data: BranchCenter | null) => {
      this.formInstance = data;
    })
  );

  m_Update$: Subject<BCUpdateDTO> = new Subject<BCUpdateDTO>().pipe(
    exhaustMap((dto: BCUpdateDTO) => {
      return this.m_BCDashboardService.patchBCData(dto);
    })
  ) as Subject<BCUpdateDTO>;

  m_Form: FormGroup | null = null;

  constructor(private m_BCDashboardService: BCDashboardService) { }

  set formInstance(data: BranchCenter | null) {
    if (data == null) return;

    this.m_Form = new FormGroup({
      'id': new FormControl({ value: data.id, disabled: true }),
      'name': new FormControl(data.name, Validators.required),
      'description': new FormControl(data.description),
    });
  }

  onSubmit(): void {
    const dto: BCUpdateDTO = this.m_Form?.getRawValue();
    console.log(dto);

    this.m_Update$.next(dto);
  }
}