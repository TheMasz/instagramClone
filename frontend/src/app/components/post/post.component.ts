import { NgClass, NgFor, NgIf } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { IGetPostResponse } from '../../interfaces/i-get-post-response';
import { ModalPostComponent } from '../modal-post/modal-post.component';
import { TooltipsComponent } from '../tooltips/tooltips.component';
import { CommentFormComponent } from '../comment-form/comment-form.component';
import { formatDate } from '../../utils/formater';
import { ModalConfirmDeleteComponent } from '../modal-confirm-delete/modal-confirm-delete.component';
import { IProfileResponse } from '../../interfaces/i-profile-response';
import { UserService } from '../../services/user.service';
import { getFirstCharacter } from '../../utils/formater';

@Component({
  selector: 'app-post',
  standalone: true,
  imports: [
    MatIconModule,
    NgFor,
    NgIf,
    ModalPostComponent,
    TooltipsComponent,
    CommentFormComponent,
    ModalConfirmDeleteComponent,
    NgClass
  ],
  templateUrl: './post.component.html',
  styleUrl: './post.component.scss',
})
export class PostComponent {
  constructor(private userService: UserService) {}

  getFirstCharacter=getFirstCharacter;

  isModalPostOpen = false;

  @Input() post!: IGetPostResponse;

  formatDate = formatDate;

  user!: IProfileResponse;

  isModalCreateOpen = false;

  currentIndex = 0;

  activePostId: string | null = null;
  postId!: string;

  isDeleteModal = false;

  ngOnInit() {
    this.userService.getMyProfile().subscribe({
      next: (res) => {
        this.user = res;
      },
      error: (err) => {
        console.log(err);
        alert(err.error.message);
      },
    });
  }

  closeModalDelete() {
    this.isDeleteModal = false;
  }

  openModalDelete() {
    this.isDeleteModal = true;
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

  openModalPost() {
    this.isModalPostOpen = true;
  }

  closeModalPost() {
    this.isModalPostOpen = false;
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
