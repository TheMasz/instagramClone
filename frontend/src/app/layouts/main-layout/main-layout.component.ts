import { Component, Input } from '@angular/core';
import { LSidebarComponent } from '../../components/l-sidebar/l-sidebar.component';
import { ModalCreatePostComponent } from '../../components/modal-create-post/modal-create-post.component';
import { NgIf } from '@angular/common';
import { IProfileResponse } from '../../interfaces/i-profile-response';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [LSidebarComponent, ModalCreatePostComponent, NgIf],
  templateUrl: './main-layout.component.html',
  styleUrl: './main-layout.component.scss',
})
export class MainLayoutComponent {
  constructor(private userService: UserService) {}

  @Input() isSidebarExpanded!: boolean;
  @Input() isChat!: boolean;

  user!: IProfileResponse;

  isModalCreateOpen = false;

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

  openModalCreate() {
    this.isModalCreateOpen = true;
  }

  closeModalCreate() {
    this.isModalCreateOpen = false;
  }
}
