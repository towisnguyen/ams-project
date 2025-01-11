import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'firstCapitalize'
})
export class FirstCapitalizePipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): unknown {
    return null;
  }

}
