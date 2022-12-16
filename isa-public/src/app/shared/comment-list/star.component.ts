import { Component, Input } from "@angular/core";

@Component({
  selector: 'star',
  template: `
    <mat-icon *ngIf="i_Value <= 0.33">star_border</mat-icon>
    <mat-icon *ngIf="i_Value > 0.33 && i_Value <= 0.66">star_half</mat-icon>
    <mat-icon *ngIf="i_Value > 0.66">star</mat-icon>
  `
})
export class StarComponent { 
  @Input() i_Value: number = 0;
  
}