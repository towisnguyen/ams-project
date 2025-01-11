import { Component, Renderer2, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/authen/user-service.service';
import { MatDialog } from '@angular/material/dialog';
import { CreateDialogComponent } from 'src/app/feature/create-dialog/create-dialog.component';
import { ActivatedRoute, ActivationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';
import { JwtAuthenticationControllerService } from '../../../api/jwtAuthenticationController.service';
import { ManageProfileComponent } from '../manage-profile/manage-profile.component';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-headerbar',
  templateUrl: './headerbar.component.html',
  styleUrls: ['./headerbar.component.scss'],
})
export class HeaderbarComponent implements OnInit {
  public userProfile: any;
  tabOnSelect = '';
  menuOpen: boolean = false;
  menuBtnClick: boolean = false;
  isMenuNameOpened: string = '';
  isAvatarOpened: boolean = false;
  isProjectMobileOpened: boolean = false;

  constructor(
    private userServiceService: UserServiceService,
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private router: Router,
    private renderer: Renderer2,
    private jwtAuthenticationControllerService: JwtAuthenticationControllerService,
    private jwtHelper: JwtHelperService,
  ) {
    this.router.events
      .pipe(filter((routerEvent) => routerEvent instanceof ActivationEnd))
      .subscribe((routerEvent: ActivationEnd | any) => {
        const data = routerEvent.snapshot.data;
        if (data?.onSelect) {
          this.tabOnSelect = data.onSelect;
          const checklist = document.getElementsByClassName('myTabSelect');
          for (let i = 0; i < checklist.length; i++) {
            var item = checklist[i] as HTMLInputElement;
            item.style.backgroundColor = '';
            if (item.id === data.breadcrumb) {
              item.style.backgroundColor = 'lightblue';
            }
          }
        }
      });

    this.renderer.listen('window', 'click', (e: Event) => {
      if (this.isMenuNameOpened !== '' && this.menuBtnClick !== true) {
        this.isMenuNameOpened = '';
      }
      this.menuBtnClick = false;
    });
    this.isTokenExpired();
  }


  ngOnInit(): void {
    this.getUserProfile();
  }


  logout() {
    this.userServiceService.doLogout();
  }

  toggleMenu(menuName: string): void {
    this.menuBtnClick = true;
    if (this.isMenuNameOpened === menuName) {
      this.isMenuNameOpened = '';
    } else {
      this.isMenuNameOpened = menuName;
    }
  }

  toggleAvatar(): void {
    this.isAvatarOpened = !this.isAvatarOpened;
  }

  toggleMoblileButton(): void {
    var ebar = document.getElementById('ebar');
    if (ebar.classList.contains("d-none")) {
      ebar.classList.remove("d-none");
      ebar.classList.add('d-sm-block');
      ebar.classList.add('d-md-none');
    } else {
      ebar.classList.remove('d-md-none');
      ebar.classList.remove("d-sm-block");
      ebar.classList.add('d-none');
    }
  }

  getSelectTab() {
    const checklist = document.getElementsByClassName('myTabSelect');
    for (let i = 0; i < checklist.length; i++) {
      var item = checklist[i] as HTMLInputElement;
      item.style.backgroundColor = '';
      if (item.id === this.tabOnSelect) {
        item.style.backgroundColor = 'lightblue';
      }
    }
  }

  clickOutside(): void {
    this.menuBtnClick = false;
  }

  clickOutAvatar() {
    this.isAvatarOpened = false;
  }

  openDialog() {
    const dialogRef = this.dialog.open(CreateDialogComponent);

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
    });
  }

  getUserProfile() {
    this.jwtAuthenticationControllerService
      .getUserInfoUsingGET()
      .subscribe((response) => {
        this.userProfile = response;
      });
  }

  signOut() {
    localStorage.removeItem('access-Token');
    localStorage.removeItem('role');
    localStorage.removeItem('status');
    this.router.navigate(['']);
  }

  openPopupEditUserProfile() {
    this.dialog.open(ManageProfileComponent, {
      data: {
        id: this.userProfile.id,
      },
    });
  }

  navToPath(path?: string): void {
    const checklist = document.getElementsByClassName('myTabSelect');
    for (let i = 0; i < checklist.length; i++) {
      var item = checklist[i] as HTMLInputElement;
      console.log(item);
      item.style.backgroundColor = '';
      if (item.id === path) {
        item.style.backgroundColor = 'lightblue';
      }
    }
  }

  isTokenExpired() {
    var interval;
    interval = setInterval(() => {
      if (localStorage.getItem("access-Token")) {
        if(this.jwtHelper.isTokenExpired(localStorage.getItem("access-Token"))){
          alert("Auth token is expried");
          this.router.navigate(['login']);
          localStorage.removeItem("access-Token");
        }
        else{
          try {
            this.jwtAuthenticationControllerService.getUserInfoUsingGET().subscribe(
              data =>
              {
                if(data.status="ACTIVE"){
                  console.log('success', data);
                }
                else{
                  this.router.navigate(['login']);
                  localStorage.removeItem("access-Token");
                }

              }
            )
          } catch (error) {
            this.router.navigate(['login']);
            localStorage.removeItem("access-Token");
          }

          }
        }
    }, 300000);
  }

}


