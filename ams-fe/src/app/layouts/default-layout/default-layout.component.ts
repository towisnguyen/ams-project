import { Component } from '@angular/core';
import {
  ActivatedRoute,
  ActivationEnd,
  NavigationEnd,
  Router,
} from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-default-layout',
  templateUrl: 'default-layout.component.html',
  styleUrls: ['./default-layout.component.scss'],
})
export class DefaultLayoutComponent {
  public showAMS: boolean = false;
  public showDash: boolean = false;
  public showPA: boolean = false;
  public showUsers: boolean = false;
  public showRequests: boolean = false;
  public showHome: boolean = false;
  public isOpened: boolean = false;
  public isSubMenuOpened: boolean = false;
  public items = [
    { title: 'All Asserts' },
    { title: 'Create New Asserts' },
    {
      title: 'Sub Items',
      subMenu: true,
      submenuItems: [{ title: 'Assets 1' }, { title: 'Assets 2' }],
    },
  ];
  constructor(private router: Router, private route: ActivatedRoute) {
  }
  public openLeftSide() {
    this.isOpened = !this.isOpened;
  }
  public openSubMenu() {
    this.isSubMenuOpened = !this.isSubMenuOpened;
  }

  public closeLeftSide() {
    this.isOpened = false;
  }
}
