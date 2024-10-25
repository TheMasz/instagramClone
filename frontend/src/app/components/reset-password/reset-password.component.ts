import { NgClass, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Title } from '@angular/platform-browser';
import { UserService } from '../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [ReactiveFormsModule, NgClass, NgIf],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.scss',
})
export class ResetPasswordComponent {
  private resetToken: any;
  isSuccess = false;
  isResendSuccess = false;
  isFailTokenExpire = false;

  constructor(
    private titleService: Title,
    private userUservice: UserService,
    private router: Router,
    private activateRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.titleService.setTitle('Reset Password');
    this.resetToken = this.activateRoute.snapshot.paramMap.get('resetToken');
  }

  repassFormGroup: FormGroup = new FormGroup({
    password: new FormControl(
      '',
      Validators.compose([Validators.required, Validators.minLength(8)])
    ),
    cfPassword: new FormControl(
      '',
      Validators.compose([Validators.required, Validators.minLength(8)])
    ),
  });

  onSubmit(): void {
    if (this.repassFormGroup.invalid) {
      return;
    }
    let newPassword = this.repassFormGroup.controls['password'].value;
    let cfNewPassword = this.repassFormGroup.controls['cfPassword'].value;

    if (newPassword != cfNewPassword) {
      return alert('password not match');
    }

    this.userUservice.resetPassword(this.resetToken, newPassword).subscribe({
      next: (res) => {
        this.isSuccess = true;
        this.router.navigate(['/signin']);
        return;
      },
      error: (err) => {


        let code = err.error.message;
        console.log(code);
        if (code == 'user.reset.password.token.expire') {
          this.isFailTokenExpire = true;
        }
        this.isSuccess = false;
        return;
      },
    });
  }

  resendResetTokenPassword() {
    this.userUservice.resendResetPassword(this.resetToken).subscribe({
      next: (res) => {
        this.isResendSuccess = true;
      },
      error: (err) => {
        console.log(err);
        alert(err.error.error);
      },
    });
  }
}
