import { NgClass } from '@angular/common';
import { Component } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../services/user.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, NgClass],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss',
})
export class SignupComponent {
  constructor(
    private userService: UserService,
    private router: Router,
    private titleService: Title
  ) {}

  ngOnInit() {
    this.titleService.setTitle('Signup');
  }

  signupFormGroup: FormGroup = new FormGroup({
    email: new FormControl(
      '',
      Validators.compose([Validators.email, Validators.required])
    ),
    username: new FormControl('', Validators.required),
    password: new FormControl(
      '',
      Validators.compose([Validators.minLength(8), Validators.required])
    ),
  });

  onSubmit(): void {
    if (this.signupFormGroup.invalid) {
      return;
    }
    let email = this.signupFormGroup.controls['email'].value;
    let username = this.signupFormGroup.controls['username'].value;
    let password = this.signupFormGroup.controls['password'].value;

    this.userService.signup(email, username, password).subscribe({
      next: (response) => {
        this.router.navigate(['/signin']);
      },
      error: (err) => {
        console.log(err);
        alert(err.error.error);
      },
    });
  }
}
