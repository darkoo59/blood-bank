import { Component, Input } from "@angular/core";

@Component({
  selector: 'grade',
  template: `
    <div style="display:flex;color:var(--secondary);">
      <star [i_Value]="i_Grade"></star>
      <star [i_Value]="i_Grade - 1"></star>
      <star [i_Value]="i_Grade - 2"></star>
      <star [i_Value]="i_Grade - 3"></star>
      <star [i_Value]="i_Grade - 4"></star>
    </div>
  `
})
export class GradeComponent { 
  @Input() i_Grade: number = 0;

}