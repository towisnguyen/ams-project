import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { JwtAuthenticationControllerService } from '../api/jwtAuthenticationController.service';

@Injectable({
  providedIn: 'root'
})
export class UserTokenGuard {

  constructor(private router: Router) {

  }
  public jwtHelper: JwtHelperService = new JwtHelperService();


  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if (this.getToken()) {
        if(!this.jwtHelper.isTokenExpired(this.getToken())){
          return true;
        }
        else{
          alert("Token is expried")
          this.router.navigate(['login'])
          return false
        }
      } else {
        alert("Access not allowed!")
        this.router.navigate(['login'])
        return false
      }
  }

  getToken() {
    return localStorage.getItem("access-Token");
  }

}
