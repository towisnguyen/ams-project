import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'project'
})
export class ProjectPipe implements PipeTransform {

  projects = ["Mega", "Aura", "Workplace", "AMS"]

  transform(value: number, ...args: unknown[]): unknown {
    return this.projects[value];
  }

}
