import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ISigninResponse } from '../interfaces/i-signin-response';
import { ISignupResponse } from '../interfaces/i-signup-response';
import { IProfileResponse } from '../interfaces/i-profile-response';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  getProfileByUsername(username:string): Observable<IProfileResponse> {
    let url = `http://localhost:8080/user/profile/${username}`;
    return this.http.get<IProfileResponse>(url);
  }

  getMyProfile(): Observable<IProfileResponse> {
    let url = 'http://localhost:8080/user/myprofile';
    return this.http.get<IProfileResponse>(url);
  }

  getSuggestUser(): Observable<IProfileResponse[]> {
    let url = 'http://localhost:8080/user/suggest';
    return this.http.get<IProfileResponse[]>(url);
  }

  signin(
    emailOrUsername: string,
    password: string
  ): Observable<ISigninResponse> {
    let url = 'http://localhost:8080/user/signin';
    let body = {
      emailOrUsername: emailOrUsername,
      password: password,
    };

    return this.http.post<ISigninResponse>(url, body);
  }

  signup(
    email: string,
    username: string,
    password: string
  ): Observable<ISignupResponse> {
    let url = 'http://localhost:8080/user/signup';
    let body = {
      email: email,
      username: username,
      password: password,
    };

    return this.http.post<ISignupResponse>(url, body);
  }

  signout(): Observable<any> {
    let url = 'http://localhost:8080/user/signout';
    return this.http.post(url, {}, { responseType: 'text' });
  }

  activateAccount(token: string): Observable<any> {
    let url = 'http://localhost:8080/user/activate-account';
    let body = {
      token: token,
    };

    return this.http.post<any>(url, body);
  }

  resendActivationEmail(token: string): Observable<any> {
    let url = 'http://localhost:8080/user/resend-activation-email';
    let body = {
      token: token,
    };

    return this.http.post<any>(url, body);
  }

  sendResetPassword(email: string): Observable<any> {
    let url = 'http://localhost:8080/user/send-token-reset-password';
    let body = {
      email: email,
    };

    return this.http.post<any>(url, body);
  }

  resendResetPassword(resetToken: string): Observable<any> {
    let url = 'http://localhost:8080/user/resend-token-reset-password';
    let body = {
      resetToken: resetToken,
    };

    return this.http.post<any>(url, body);
  }

  resetPassword(resetToken: string, newPassword: string): Observable<any> {
    let url = 'http://localhost:8080/user/reset-password';
    let body = {
      resetToken: resetToken,
      newPassword: newPassword,
    };
    return this.http.put<any>(url, body);
  }
}
