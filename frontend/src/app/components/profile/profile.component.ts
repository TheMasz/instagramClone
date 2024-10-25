import { Component } from '@angular/core';
import { MainLayoutComponent } from '../../layouts/main-layout/main-layout.component';
import { PostService } from '../../services/post.service';
import { IGetPostResponse } from '../../interfaces/i-get-post-response';
import { ActivatedRoute } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { IProfileResponse } from '../../interfaces/i-profile-response';
import { UserService } from '../../services/user.service';
import { NgClass, NgFor, NgIf } from '@angular/common';
import { getFirstCharacter } from '../../utils/formater';
import { ModalPostComponent } from '../modal-post/modal-post.component';
import { Title } from '@angular/platform-browser';
import { ModalCreatePostComponent } from '../modal-create-post/modal-create-post.component';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    MainLayoutComponent,
    MatIconModule,
    NgIf,
    NgFor,
    NgClass,
    ModalPostComponent,
    ModalCreatePostComponent,
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})
export class ProfileComponent {
  constructor(
    private titleService: Title,
    private postService: PostService,
    private userService: UserService,
    private route: ActivatedRoute
  ) {}

  getFirstCharacter = getFirstCharacter;

  isModalPostOpen = false;
  isModalCreateOpen = false;

  username: string = '';

  isPost = true;
  isSaved = false;

  post!: IGetPostResponse;
  posts: IGetPostResponse[] = [];
  savePosts: IGetPostResponse[] = [];
  
  userProfile: IProfileResponse | undefined;
  curUser: IProfileResponse | undefined;

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.username = params['username'];
    });

    this.userService.getMyProfile().subscribe({
      next: (res) => {
        this.curUser = res;
      },
      error: (err) => {
        console.log(err);
        alert(err.error.error);
      },
    });

    this.postService.getByUser(this.username).subscribe({
      next: (res) => {
        this.posts = res;
      },
      error: (err) => {
        console.log(err);
        alert(err.error.error);
      },
    });

    this.postService.getSavedPosts().subscribe({
      next: (res) => {
        this.savePosts = res;
      },
      error: (err) => {
        console.log(err);
        alert(err.error.error);
      },
    });

    this.userService.getProfileByUsername(this.username).subscribe({
      next: (res) => {
        this.userProfile = res;
      },
      error: (err) => {
        console.log(err);
        alert(err.error.error);
      },
    });

    this.titleService.setTitle(this.username);
  }

  closeModalPost() {
    this.isModalPostOpen = false;
  }

  closeModalCreate() {
    this.isModalCreateOpen = false;
  }

  openModalCreate() {
    this.isModalCreateOpen = true;
  }

  selectPost(post: IGetPostResponse) {
    this.post = post;
    this.isModalPostOpen = true;
  }

  onPost() {
    this.isPost = true;
    this.isSaved = false;
  }

  onSave() {
    this.isPost = false;
    this.isSaved = true;
  }
}
