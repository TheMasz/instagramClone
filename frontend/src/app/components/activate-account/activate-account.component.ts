import { Component } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { UserService } from '../../services/user.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-activate-account',
  standalone: true,
  imports: [RouterLink, NgIf],
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.scss',
})
export class ActivateAccountComponent {
  private token: any;
  isSuccess = false;
  isResendSuccess = false;
  isErrorTokenExipire = false;
  isErrorAccountAlreadyActivated = false;
  errorMessage = '';

  constructor(
    private titleService: Title,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.titleService.setTitle('Activated Account');
    this.token = this.activatedRoute.snapshot.paramMap.get('token');

    if (this.token === null) {
      this.router.navigate(['/signin']);
      return;
    }

    this.activateAccount(this.token as string);
  }

  private activateAccount(token: string) {
    this.userService.activateAccount(token).subscribe({
      next: (res) => {
        console.log(res);
        this.isSuccess = true;
      },
      error: (err) => {
        let code = err.error.message;
        if (code == 'user.activate.already') {
          this.isErrorAccountAlreadyActivated = true;
          this.errorMessage = 'บัญชีของคุณยืนยันเรียบร้อยแล้ว';
        } else if (code == 'user.activate.token.expire') {
          this.isErrorTokenExipire = true;
          this.errorMessage = 'โทเค็นของคุณหมดอายุแล้ว';
        }
        this.isSuccess = false;
      },
    });
  }

  resendActivationEmail() {
    this.userService.resendActivationEmail(this.token).subscribe({
      next: (res) => {
        console.log(res);
        this.isResendSuccess = true;
      },
      error: (error) => console.log(error),
    });
  }
}
