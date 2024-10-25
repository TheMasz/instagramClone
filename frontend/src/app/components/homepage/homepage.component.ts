import { Component } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { MainLayoutComponent } from '../../layouts/main-layout/main-layout.component';
import { StoryComponent } from '../story/story.component';
import { PostComponent } from '../post/post.component';
import { PostService } from '../../services/post.service';
import { NgFor, NgIf } from '@angular/common';
import { IGetPostResponse } from '../../interfaces/i-get-post-response';
import { UserService } from '../../services/user.service';
import { IProfileResponse } from '../../interfaces/i-profile-response';
import { getFirstCharacter } from '../../utils/formater';
import { FollowerService } from '../../services/follower.service';

@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [MainLayoutComponent, StoryComponent, PostComponent, NgFor, NgIf],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.scss',
})
export class HomepageComponent {
  constructor(
    private titleService: Title,
    private userService: UserService,
    private postService: PostService,
    private followerService: FollowerService
  ) {}

  getFirstCharacter = getFirstCharacter;

  posts: IGetPostResponse[] = [];
  user: IProfileResponse | undefined;
  usersSuggest: IProfileResponse[] = [];

  ngOnInit() {
    this.titleService.setTitle('Instagram');
    this.postService.getAll().subscribe({
      next: (res) => {
        this.posts = res;
      },
      error: (err) => {
        console.log(err);
        alert(err.error.error);
      },
    });
    this.userService.getMyProfile().subscribe({
      next: (res) => {
        this.user = res;
      },
      error: (err) => {
        console.log(err);
        alert(err.error.error);
      },
    });
    this.userService.getSuggestUser().subscribe({
      next: (res) => {
        this.usersSuggest = res;
      },
      error: (err) => {
        console.log(err);
        alert(err.error.error);
      },
    });
  }

  onFollowerAction(userSuggest: IProfileResponse) {
    const action = userSuggest.followedByCurrentUser ? 'unfollow' : 'follow';
    this.followerService.followerAction(action, userSuggest.id)!.subscribe({
      next: (res) => {
        userSuggest.followedByCurrentUser = res.isFollowedByCurrentUser;
      },
      error: (err) => {
        console.log(err);
        alert(err.error.error);
      },
    });
  }
}
