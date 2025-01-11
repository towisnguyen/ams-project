import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'supplier'
})
export class SupplierPipe implements PipeTransform {

  suppliers = ["HP","LOGITECH","DELL","BROTHER","APPLE","SAMSUNG"]

  transform(value: number, ...args: unknown[]): unknown {
    return this.suppliers[value];
  }

}
