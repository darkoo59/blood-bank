import { Component } from "@angular/core";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { tap } from "rxjs";
import { BranchCenter } from "src/app/model/branch-center.model";
import { BCDashboardService } from "./bc-dashboard.service";

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
    console.log(this.m_Form?.getRawValue());
  }
}