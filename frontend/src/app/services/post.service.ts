import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ICreatePostResponse } from '../interfaces/i-create-post-response';
import { IGetPostResponse } from '../interfaces/i-get-post-response';
import { ISaveResponse } from '../interfaces/i-save-response';
import { ILikeResponse } from '../interfaces/i-like-response';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  constructor(private http: HttpClient) {}

  create(desc: string, files: File[]): Observable<ICreatePostResponse> {
    let url = 'http://localhost:8080/post';
    const formData = new FormData();
    formData.append('description', desc);
    files.forEach((file, index) => {
      formData.append('files', file);
    });
    return this.http.post<ICreatePostResponse>(url, formData);
  }

  delete(postId: string): Observable<void> {
    let url = `http://localhost:8080/post/${postId}`;
    return this.http.delete<void>(url);
  }

  getSavedPosts(): Observable<IGetPostResponse[]> {
    let url = 'http://localhost:8080/saved';

    return this.http.get<IGetPostResponse[]>(url);
  }

  getByUser(userId: string): Observable<IGetPostResponse[]> {
    let url = `http://localhost:8080/post/getByUser/${userId}`;

    return this.http.get<IGetPostResponse[]>(url);
  }

  getAll(): Observable<IGetPostResponse[]> {
    let url = 'http://localhost:8080/post/getAll';

    return this.http.get<IGetPostResponse[]>(url);
  }

  get(id: string): Observable<IGetPostResponse> {
    let url = `http://localhost:8080/post/get/${id}`;

    return this.http.get<IGetPostResponse>(url);
  }

  likeAction(
    action: string,
    postId: string
  ): Observable<ILikeResponse> | undefined {
    let url;
    if (action == 'like') {
      url = `http://localhost:8080/liked/${postId}`;
      return this.http.post<ILikeResponse>(url, {});
    }
    if (action == 'unlike') {
      url = `http://localhost:8080/liked/${postId}`;
      return this.http.delete<ILikeResponse>(url);
    }

    return undefined;
  }

  saveAction(
    action: string,
    postId: string
  ): Observable<ISaveResponse> | undefined {
    let url;
    if (action == 'save') {
      url = `http://localhost:8080/saved/${postId}/save`;
      return this.http.post<ISaveResponse>(url, {});
    }
    if (action == 'unsave') {
      url = `http://localhost:8080/saved/${postId}/unsave`;
      return this.http.delete<ISaveResponse>(url);
    }
    return undefined;
  }
}
