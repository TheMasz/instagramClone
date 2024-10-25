import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IFollowerResponse } from '../interfaces/i-follower-response';

@Injectable({
  providedIn: 'root',
})
export class FollowerService {
  constructor(private http: HttpClient) {}

  followerAction(
    action: string,
    following: string
  ): Observable<IFollowerResponse> | undefined {
    let url;
    if (action === 'follow') {
      url = `http://localhost:8080/follower/${following}`;
      return this.http.post<IFollowerResponse>(url, {});
    }
    if (action === 'unfollow') {
      url = `http://localhost:8080/follower/${following}`;
      return this.http.delete<IFollowerResponse>(url);
    }
    return undefined;
  }
}
