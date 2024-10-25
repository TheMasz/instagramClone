import { Component, Input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { PostService } from '../../services/post.service';
import { IGetPostResponse } from '../../interfaces/i-get-post-response';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-tooltips',
  standalone: true,
  imports: [MatIconModule, NgIf],
  templateUrl: './tooltips.component.html',
  styleUrl: './tooltips.component.scss',
})
export class TooltipsComponent {
  constructor(private postService: PostService) {}

  @Input() post!: IGetPostResponse;

  onLikeAction() {
    const action = this.post.isLikedByCurrentUser ? 'unlike' : 'like';
    this.postService.likeAction(action, this.post.id)!.subscribe({
      next: (res) => {
        this.post.isLikedByCurrentUser = res.isLikedByCurrentUser;
        this.post.likeCount = res.likeCount;
      },
      error: (err) => {
        console.log(err);
        alert(err.error.error);
      },
    });
  }

  onSaveAction() {
    const action = this.post.isSavedByCurrentUser ? 'unsave' : 'save';
    this.postService.saveAction(action, this.post.id)!.subscribe({
      next: (res) => {
        this.post.isSavedByCurrentUser = res.isSavedByCurrentUser;
      },
      error: (err) => {
        console.log(err);
        alert(err.error.error);
      },
    });
  }
}
