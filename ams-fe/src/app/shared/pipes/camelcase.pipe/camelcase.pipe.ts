import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'camelcase'
})
export class CamelcasePipe implements PipeTransform {

  transform(value: any, ...args: unknown[]): unknown {
    let new_value = value[0];
    for (let index = 1; index < value.length; index++) {
      if (value[index] === '_') {
        continue;
      }
      if(value[index] !== '_') {
        if (typeof(value[index]) !== 'string') {
          new_value += value[index]
        } else {
          if (value[index-1] === '_') {
            let variableUppercase = value[index].toUpperCase()
            new_value += variableUppercase
          } else {
            new_value += value[index]
          }
        }
      }
    }
    return new_value
  }
}
