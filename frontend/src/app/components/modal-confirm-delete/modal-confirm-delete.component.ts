import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommentService } from '../../services/comment.service';
import { IGetPostResponse } from '../../interfaces/i-get-post-response';
import { PostService } from '../../services/post.service';

@Component({
  selector: 'app-modal-confirm-delete',
  standalone: true,
  imports: [],
  templateUrl: './modal-confirm-delete.component.html',
  styleUrl: './modal-confirm-delete.component.scss',
})
export class ModalConfirmDeleteComponent {
  constructor(
    private commentService: CommentService,
    private postService: PostService,
  ) {}

  @Input() commentId?: string;
  @Input() postId?: string;
  @Input() title!: string;
  @Input() post!: IGetPostResponse;
  @Output() closeModalDelete = new EventEmitter<void>();

  onCloseModalDelete() {
    this.closeModalDelete.emit();
  }

  submitDelete() {
    if (
      this.commentId == null ||
      (this.commentId == undefined && this.title == 'โพสต์')
    ) {
      this.postService.delete(this.post.id).subscribe({
        next: (res) => {
          this.onCloseModalDelete();
          window.location.reload();
        },
        error: (err) => {
          console.log(err);
          alert(err.error.error);
        },
      });
    } else {
      this.commentService.delete(this.commentId).subscribe({
        next: (res) => {
          this.onCloseModalDelete();
          this.post.comments = this.post.comments.filter(
            (comment) => comment.commentId !== this.commentId
          );
        },
        error: (err) => {
          console.log(err);
          alert(err.error.error);
        },
      });
    }
  }
}
