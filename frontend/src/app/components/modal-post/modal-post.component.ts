import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IGetPostResponse } from '../../interfaces/i-get-post-response';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { TooltipsComponent } from '../tooltips/tooltips.component';
import { CommentFormComponent } from '../comment-form/comment-form.component';

import { formatDate, getFirstCharacter } from '../../utils/formater';
import { IProfileResponse } from '../../interfaces/i-profile-response';
import { UserService } from '../../services/user.service';
import { ModalConfirmDeleteComponent } from '../modal-confirm-delete/modal-confirm-delete.component';

@Component({
  selector: 'app-modal-post',
  standalone: true,
  imports: [
    NgFor,
    NgIf,
    NgClass,
    MatIconModule,
    TooltipsComponent,
    CommentFormComponent,
    ModalConfirmDeleteComponent,
  ],
  templateUrl: './modal-post.component.html',
  styleUrl: './modal-post.component.scss',
})
export class ModalPostComponent {
  constructor(private userService: UserService) {}

  getFirstCharacter = getFirstCharacter;
  formatDate = formatDate;

  @Input() post!: IGetPostResponse;
  @Output() closeModalPost = new EventEmitter<void>();

  user: IProfileResponse | undefined;

  currentIndex = 0;
  showCommentOptions = false;

  activeCommentId: string | null = null;
  commentId!: string;

  isDeleteModal = false;

  activePostId: string | null = null;
  postId!: string;


  ngOnInit() {
    this.userService.getMyProfile().subscribe({
      next: (res) => {
        this.user = res;
      },
      error: (err) => {
        console.log(err);
        alert(err.error.error);
      },
    });
  }

  openModalDelete() {
    this.isDeleteModal = true;
  }

  closeModalDelete() {
    this.isDeleteModal = false;
  }

  onCloseModalPost() {
    this.closeModalPost.emit();
  }

  prevImage() {
    this.currentIndex =
      this.currentIndex > 0
        ? this.currentIndex - 1
        : this.post.images.length - 1;
  }

  nextImage() {
    this.currentIndex =
      this.currentIndex < this.post.images.length - 1
        ? this.currentIndex + 1
        : 0;
  }

  setCurrentImage(index: number) {
    this.currentIndex = index;
  }

  toggleCommentOption(commentId: string) {
    if (this.activeCommentId === commentId) {
      this.activeCommentId = null;
    } else {
      this.activeCommentId = commentId;
    }
  }

  deleteComment() {
    if (this.activeCommentId == null || this.activeCommentId == undefined) {
      return;
    }
    this.commentId = this.activeCommentId;
    this.openModalDelete();
  }

  togglePostOption(postId: string) {
    if (this.activePostId === postId) {
      this.activePostId = null;
    } else {
      this.activePostId = postId;
    }
  }

  deletePost() {
    if (this.activePostId == null || this.activePostId == undefined) {
      return;
    }
    this.postId = this.activePostId;
    this.openModalDelete();
  }
}
