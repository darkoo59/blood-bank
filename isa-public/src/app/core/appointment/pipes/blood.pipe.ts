import { Pipe, PipeTransform } from '@angular/core';
@Pipe({name: 'bloodToString'})
export class BloodPipe implements PipeTransform {
  static typesEnum: string[] = ['APositive', 'ANegative', 'BPositive', 'BNegative', 'ABPositive', 'ABNegative', 'OPositive', 'ONegative'];
  static types: string[] = ['A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-'];
  transform(bloodType: number): string {
    return BloodPipe.types[bloodType];
  }
}