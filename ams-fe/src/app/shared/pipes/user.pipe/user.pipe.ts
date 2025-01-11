import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'user'
})
export class UserPipe implements PipeTransform {

  users = ["Tuyen", "Tuan", "Dat", "Tu"]

  transform(value: number, ...args: unknown[]): unknown {
    return this.users[value];
  }

}
