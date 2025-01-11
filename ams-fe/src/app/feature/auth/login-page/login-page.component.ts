import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { JwtAuthenticationControllerService } from '../../../api/jwtAuthenticationController.service';
import { UserControllerService } from '../../../api/userController.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
})
export class LoginPageComponent {
  token: any;
  role: any;
  status: any;
  totalItems: any;
  index: any;
  data: any;

  constructor(
    private jwtAuthenticationControllerService: JwtAuthenticationControllerService,
    private userControllerService: UserControllerService,
    private router: Router
  ) {}

  login(loginForm: NgForm) {
    localStorage.setItem('access-Token', '');
    this.jwtAuthenticationControllerService
      .createAuthenticationTokenUsingPOST(loginForm.value)
      .subscribe((response) => {
        console.log(response);
        this.token = response.token;
        localStorage.setItem('access-Token', this.token);
        if (!this.token) {
          alert('Incorrect User or password... Please contact Administrator.');
        } else {
          this.jwtAuthenticationControllerService
            .getUserInfoUsingGET()
            .subscribe((response) => {
              console.log(response);
              this.data = response;
              this.status = this.data.status;
              this.role = this.data.role;
              localStorage.setItem('status', this.data.status);
              localStorage.setItem('role', this.data.role);
              if (this.status === 'ACTIVE') {
                if (this.role === 'ADMIN') {
                  this.router.navigate(['home']);
                }
                if (this.role === 'USER') {
                  this.router.navigate(['home/users/allusers']);
                }
                if (this.role === 'EDITOR') {
                  this.router.navigate(['home']);
                }
              } else {
                alert(
                  'Your account has been deactivated... Please contact Administrator.'
                );
              }
            });
        }
      });
  }
}
