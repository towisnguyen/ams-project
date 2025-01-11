import { Injectable } from '@angular/core';
import { Route, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  constructor(private router: Router) { }
  doLogout() {
    localStorage.removeItem('access-Token');
    localStorage.removeItem('role');
    localStorage.removeItem('status');
    this.router.navigate(['']);
  }
}
