import { HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest, HttpErrorResponse,HttpStatusCode } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, map, Observable, throwError } from 'rxjs';
import { LoginPageComponent } from '../feature/auth/login-page/login-page.component';
import { JwtAuthenticationControllerService } from '../api/jwtAuthenticationController.service';

@Injectable()
export class Interceptor implements HttpInterceptor {
  constructor() {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
    ): Observable<HttpEvent<any>> {
      const token: any = localStorage.getItem("access-Token")
      const headers = new HttpHeaders()
        .set('Authorization', 'Bearer ' + token);
      const AuthRequest = request.clone({ headers: headers });
      // return next.handle(AuthRequest)
      return next.handle(AuthRequest).pipe(
        map((event) => {
          return event;
        }),
        catchError((error: HttpErrorResponse) => {
          if (error.status == HttpStatusCode.Unauthorized) {
            console.log("Redirect to SSO again pls. TODO here.")
            // this.authService.redirectToSso('login')
          }
          return throwError(() => error);
        })
      );
  }
}
