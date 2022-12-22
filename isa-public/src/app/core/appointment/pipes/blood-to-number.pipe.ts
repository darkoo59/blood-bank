import { Pipe, PipeTransform } from '@angular/core';
@Pipe({name: 'bloodToNumber'})
export class BloodToNumberPipe implements PipeTransform {
  static s_TypesEnum: string[] = ['APositive', 'ANegative', 'BPositive', 'BNegative', 'ABPositive', 'ABNegative', 'OPositive', 'ONegative'];

  transform(bloodType: string): number {
    const types = BloodToNumberPipe.s_TypesEnum;

    for(let i = 0; i < types.length; i++) 
      if(types[i] === bloodType) return i;
    
    return -1;
  }
}