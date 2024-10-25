import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class AppCookiesService {

  constructor(private cookieService: CookieService) { }

  setAccessToken(token: string): void {
    this.cookieService.set('ACCESS_TOKEN', token);
  }

  getAccessToken(): string {
    return this.cookieService.get('ACCESS_TOKEN');
  }

  deleteAccessToken() {
    this.cookieService.delete('ACCESS_TOKEN');
  }

  hasAccessToken(): boolean {
    return this.cookieService.check('ACCESS_TOKEN');
  }


}
