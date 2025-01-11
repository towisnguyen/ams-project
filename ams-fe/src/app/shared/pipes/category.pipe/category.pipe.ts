import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'category'
})
export class CategoryPipe implements PipeTransform {

  categories = ["SERVER","Screen", "Mouse", "Keyboard"]

  transform(value: number, ...args: unknown[]): unknown {
    return this.categories[value]
  }

}
