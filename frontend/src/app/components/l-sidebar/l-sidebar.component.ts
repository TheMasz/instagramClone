import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AvatarComponent } from '../avatar/avatar.component';
import { IProfileResponse } from '../../interfaces/i-profile-response';
import { UserService } from '../../services/user.service';
import { AppCookiesService } from '../../services/app-cookies.service';
import { NgClass, NgIf } from '@angular/common';
import { getFirstCharacter } from '../../utils/formater';

@Component({
  selector: 'app-l-sidebar',
  standalone: true,
  imports: [
    NgClass,
    RouterLink,
    RouterLinkActive,
    MatIconModule,
    AvatarComponent,
    NgIf,
  ],
  templateUrl: './l-sidebar.component.html',
  styleUrl: './l-sidebar.component.scss',
})
export class LSidebarComponent {
  constructor(
    private userService: UserService,
    private router: Router,
    private appCookiesService: AppCookiesService
  ) {}

  getFirstCharacter = getFirstCharacter;

  @Input() user: IProfileResponse | undefined;
  @Output() openModalCreate = new EventEmitter<void>();
  @Input() isSidebarExpanded: boolean = false;
  @Input() isChat: boolean = false;

  isSearch = false;
  isNoti = false;
  isMoreOptions = false;

  chats = [];


  toggleMoreoptions() {
    this.isMoreOptions = !this.isMoreOptions;
  }

  toggleExpanded(content: string) {
    if (content === 'search') {
      this.isSearch = !this.isSearch;
    } else if (content === 'notifications') {
      this.isNoti = !this.isNoti;
    } else if (content === 'chats') {
      this.isChat = !this.isChat;
    }
    this.isSidebarExpanded = !this.isSidebarExpanded;
  }

  onOpenModalCreatePost() {
    this.openModalCreate.emit();
  }

  signout() {
    this.userService.signout().subscribe({
      next: (res) => {
        this.appCookiesService.deleteAccessToken();
        this.router.navigate(['/signin']);
      },
      error: (err) => {
        console.log(err);
        alert(err.error.error);
      },
    });
  }
}
