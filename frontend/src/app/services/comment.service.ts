import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ICommentResponse } from '../interfaces/i-comment-response';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  constructor(private http: HttpClient) {}

  create(postId: string, message: string): Observable<ICommentResponse> {
    let url = `http://localhost:8080/comment`;
    let body = {
      postId: postId,
      message: message,
    };
    return this.http.post<ICommentResponse>(url, body);
  }

  delete(commentId: string): Observable<any> {
    let url = `http://localhost:8080/comment/${commentId}`;
    return this.http.delete(url);
  }
}
