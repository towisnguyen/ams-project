import { ChangeDetectorRef, Component } from '@angular/core';
import { Router, ActivatedRoute, ActivationEnd } from '@angular/router';
import { AddUserComponent } from '../add-user/add-user.component';
import { MatDialog } from '@angular/material/dialog';
import { CreateRequestComponent } from '../create-request/create-request.component';
import { AddDeviceComponent } from '../add-device/add-device.component';
import { filter } from 'rxjs';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent {
  public showAMS: boolean = false;
  public showDash: boolean = false;
  public showPA: boolean = false;
  public showUsers: boolean = false;
  public showRequests: boolean = false;
  public showHome: boolean = false;
  public isOpened: boolean = false;
  public isSubMenuOpened: boolean = false;
  public onSelectSidebar = '';
  currentRole: any;
  public items = [
    { title: 'All Assets' },
    { title: 'Create New Assets' },
    {
      title: 'Sub Items',
      subMenu: true,
      submenuItems: [{ title: 'Assets 1' }, { title: 'Assets 2' }],
    },
  ];

  constructor(
    private router: Router,
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private changeDetectorRef: ChangeDetectorRef
  ) {
    this.router.events
      .pipe(filter((routerEvent) => routerEvent instanceof ActivationEnd))
      .subscribe((routerEvent: ActivationEnd | any) => {
        const data = routerEvent.snapshot.data;
        if (data?.onSelect) {
          this.displaySidebarBlock(data.onSelect);
        }
        if (data?.onSelectSidebar) {
          this.onSelectSidebar = data.onSelectSidebar;
          const checklist =
            document.getElementsByClassName('myTabSelectSideBar');
          for (let i = 0; i < checklist.length; i++) {
            var item = checklist[i] as HTMLInputElement;
            item.style.backgroundColor = '';
            if (item.id === data.onSelectSidebar) {
              item.style.backgroundColor = 'lightblue';
            }
          }
        }
      });
  }

  ngOnInit() {
    this.currentRole = localStorage.getItem('role');
  }

  ngAfterViewInit() {
    setTimeout(() => {
      if (this.onSelectSidebar) {
        const checklist = document.getElementsByClassName('myTabSelectSideBar');
        for (let i = 0; i < checklist.length; i++) {
          var item = checklist[i] as HTMLInputElement;
          item.style.backgroundColor = '';
          if (item.id === this.onSelectSidebar) {
            item.style.backgroundColor = 'lightblue';
          }
        }
      }
    }, 0);
  }

  public displaySidebarBlock(dataOnSelect: string) {
    const checklist = document.getElementsByClassName('showSidebar');
    for (let i = 0; i < checklist.length; i++) {
      var item = checklist[i] as HTMLInputElement;
      item.style.display = 'none';
    }
    switch (dataOnSelect) {
      case 'Dashboards':
        document.getElementById('showDash').style.display = 'block';
        break;
      case 'Assets':
        document.getElementById('showAMS').style.display = 'block';
        break;
      case 'Requests':
        document.getElementById('showRequests').style.display = 'block';
        break;
      case 'Users Management':
        document.getElementById('showUsers').style.display = 'block';
        break;
      case 'Projects':
        document.getElementById('showProject').style.display = 'block';
        break;
      case 'Home':
        document.getElementById('showHome').style.display = 'block';
        break;
      default:
        document.getElementById('showHome').style.display = 'block';
        break;
    }
  }

  public highlightSideBarTab() {}

  public openLeftSide() {
    this.isOpened = !this.isOpened;
  }
  public openSubMenu() {
    this.isSubMenuOpened = !this.isSubMenuOpened;
  }

  public closeLeftSide() {
    this.isOpened = false;
  }

  public navToPath(path?: string): void {
    switch (path) {
      case 'All Dashboard':
        this.router.navigate(['/home/dashboard']);
        break;
      case 'Assets Dashboard':
        this.router.navigate(['/home/dashboard/assetsdashboard']);
        break;
      case 'Projects Dashboard':
        this.router.navigate(['/home/dashboard/projectsdashboard']);
        break;
      case 'All Assets':
        this.router.navigate(['/home/assets/allassets']);
        break;
      case 'Create New Assets':
        this.dialog.open(AddDeviceComponent);
        break;
      case 'All Users':
        this.router.navigate(['/home/users/allusers']);
        break;
      case 'Create New User':
        this.dialog.open(AddUserComponent, {
          width: '700px',
        });
        break;
      case 'All Requests':
        this.router.navigate(['/home/requests/allrequests']);
        break;
      case 'Approved':
        this.router.navigate(['/home/requests/approved']);
        break;
      case 'Closed':
        this.router.navigate(['/home/requests/closed']);
        break;
      case 'Create New Request':
        this.dialog.open(CreateRequestComponent);
        break;
      default:
        break;
    }
  }
}
