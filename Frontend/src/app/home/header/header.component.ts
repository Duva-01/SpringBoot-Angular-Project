import { Component } from '@angular/core';
import {NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  username: string | undefined = "";

  constructor(private authService: AuthService) {

    this.username = authService.getUser()?.username;
  }

  onLogOut(): void {
    this.authService.logoutUser();
  }

}
