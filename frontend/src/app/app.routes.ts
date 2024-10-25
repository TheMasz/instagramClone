import { Routes } from '@angular/router';
import { SigninComponent } from './components/signin/signin.component';
import { SignupComponent } from './components/signup/signup.component';
import { HomepageComponent } from './components/homepage/homepage.component';
import { ActivateAccountComponent } from './components/activate-account/activate-account.component';
import { AuthGuardService } from './services/auth-guard.service';
import { SendResetPasswordComponent } from './components/send-reset-password/send-reset-password.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { ProfileComponent } from './components/profile/profile.component';
import { DirectMessagesComponent } from './components/direct-messages/direct-messages.component';

export const routes: Routes = [
  {
    path: '',
    component: HomepageComponent,
    canActivate: [AuthGuardService],
  },
  {
    path: 'profile/:username',
    component: ProfileComponent,
    canActivate: [AuthGuardService],
  },
  {
    path: 'direct/inbox',
    component: DirectMessagesComponent,
    canActivate: [AuthGuardService],
  },
  {
    path: 'signin',
    component: SigninComponent,
  },
  {
    path: 'signup',
    component: SignupComponent,
  },
  {
    path: 'activate/:token',
    component: ActivateAccountComponent,
  },
  {
    path: 'send-reset-password',
    component: SendResetPasswordComponent,
  },
  {
    path: 'reset-password/:resetToken',
    component: ResetPasswordComponent,
  },
];
