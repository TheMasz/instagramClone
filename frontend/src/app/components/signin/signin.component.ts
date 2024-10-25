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
import { AppCookiesService } from '../../services/app-cookies.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-signin',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, NgClass],
  templateUrl: './signin.component.html',
  styleUrl: './signin.component.scss',
})
export class SigninComponent {
  constructor(
    private appCookiesService: AppCookiesService,
    private userService: UserService,
    private router: Router,
    private titleService: Title
  ) {}

  ngOnInit() {
    this.titleService.setTitle('Signin');
  }

  signinFormGroup: FormGroup = new FormGroup({
    emailOrUsername: new FormControl('', Validators.required),
    password: new FormControl(
      '',
      Validators.compose([Validators.minLength(8), Validators.required])
    ),
  });

  onSubmit(): void {
    if (this.signinFormGroup.invalid) {
      return;
    }
    let emailOrUsername =
      this.signinFormGroup.controls['emailOrUsername'].value;
    let password = this.signinFormGroup.controls['password'].value;

    this.userService.signin(emailOrUsername, password).subscribe({
      next: (res) => {
        this.appCookiesService.setAccessToken(res.token);
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.log(err);
        alert(err.error.error);
      },
    });
  }
}
