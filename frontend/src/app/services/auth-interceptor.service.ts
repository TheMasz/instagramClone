// import {
//   HttpEvent,
//   HttpHandler,
//   HttpInterceptor,
//   HttpRequest,
// } from '@angular/common/http';
// import { Injectable } from '@angular/core';
// import { Observable } from 'rxjs';
// import { AppCookiesService } from './app-cookies.service';

// @Injectable({
//   providedIn: 'root',
// })
// export class AuthInterceptorService implements HttpInterceptor {
//   constructor(private appCookiesService: AppCookiesService) {}

//   intercept(
//     req: HttpRequest<any>,
//     next: HttpHandler
//   ): Observable<HttpEvent<any>> {
//     let token = this.appCookiesService.getAccessToken();
//     console.log('token', token);

//     if (token) {
//       let modified = req.clone({
//         headers: req.headers.set('Authorization', `Bearer ${token}`),
//       });
//       return next.handle(modified);
//     }
//     return next.handle(req);
//   }
// }

// import { HttpRequest, HttpHandlerFn, HttpEvent } from '@angular/common/http';
// import { Observable } from 'rxjs';
// import { inject } from '@angular/core';
// import { AppCookiesService } from './app-cookies.service';

// export function authInterceptor(
//   req: HttpRequest<any>,
//   next: HttpHandlerFn
// ): Observable<HttpEvent<any>> {
//   const appCookiesService = inject(AppCookiesService);
//   const token = appCookiesService.getAccessToken();

//   if (token) {
//     const modifiedReq = req.clone({
//       headers: req.headers.set('Authorization', `Bearer ${token}`),
//     });
//     return next(modifiedReq);
//   }

//   return next(req);
// }

import { HttpRequest, HttpHandlerFn, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { inject } from '@angular/core';
import { AppCookiesService } from './app-cookies.service';
import { Router } from '@angular/router';

export function authInterceptor(
  req: HttpRequest<any>,
  next: HttpHandlerFn
): Observable<HttpEvent<any>> {
  const appCookiesService = inject(AppCookiesService);
  const router = inject(Router);
  const token = appCookiesService.getAccessToken();

  if (token) {
    const modifiedReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`),
    });
    return next(modifiedReq).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401 || error.status === 403) {
          router.navigate(['/signin']);
        }
        return throwError(() => error);
      })
    );
  }

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401 || error.status === 403) {
        router.navigate(['/signin']);
      }
      return throwError(() => error);
    })
  );
}
