import { Pipe, PipeTransform } from '@angular/core';
@Pipe({name: 'bloodToString'})
export class BloodPipe implements PipeTransform {
  static s_Types: string[] = ['A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-'];
  transform(bloodType: number): string {
    return BloodPipe.s_Types[bloodType];
  }
}