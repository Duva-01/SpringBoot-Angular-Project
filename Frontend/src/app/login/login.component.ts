import { Component } from '@angular/core';
import {NgOptimizedImage} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {User} from "../models/user";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    NgOptimizedImage,
    RouterLink,
    FormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  username = "";
  password  = "";

  error = false;

  constructor(private router: Router, private authService: AuthService) { }

  onLogin() {

    this.authService.loginUser(this.username, this.password).subscribe(
      (response: User) => {
        if (response) {
          this.error = false;
          this.router.navigate(['/home']);
        } else {
          this.error = true;
          console.log('Invalid credentials');
        }
      },
      (error) => {
        console.error('Login failed', error);
      }
    );
  }
}
