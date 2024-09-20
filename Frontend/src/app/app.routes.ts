import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import {AuthGuard} from "./guards/auth.guard";
import {HomeComponent} from "./home/home.component";

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent,
    title: 'ELCA LOGIN',
  },
  {
    path: 'home',
    component: HomeComponent,
    title: 'ELCA - HOME',
    canActivate: [AuthGuard]
  },
  {
    path: '**',
    redirectTo: '/login',

  }
];
