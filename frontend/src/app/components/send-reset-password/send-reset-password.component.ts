import { NgClass, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { UserService } from '../../services/user.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-send-reset-password',
  standalone: true,
  imports: [ReactiveFormsModule, NgClass, NgIf],
  templateUrl: './send-reset-password.component.html',
  styleUrl: './send-reset-password.component.scss',
})
export class SendResetPasswordComponent {
  isSuccess = false;

  constructor(private userService: UserService, private titleService: Title) {}

  ngOnInit() {
    this.titleService.setTitle('Reset Password');
  }

  repassFormGroup: FormGroup = new FormGroup({
    email: new FormControl(
      '',
      Validators.compose([Validators.required, Validators.email])
    ),
  });

  onSubmit(): void {
    if (this.repassFormGroup.invalid) {
      return;
    }
    let email = this.repassFormGroup.controls['email'].value;
    this.userService.sendResetPassword(email).subscribe({
      next: (res) => {
        this.isSuccess = true;
      },
      error: (err) => {
        console.log(err);
        alert(err.error.message);
      },
    });
  }
}
