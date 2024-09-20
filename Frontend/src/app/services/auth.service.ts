import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../models/user";
import {UserService} from "./user.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url: string = 'http://localhost:8080/';
  private user: User | null = null;  // Aquí guardaremos el usuario completo
  private isLogged: boolean = false;

  constructor(private httpClient: HttpClient, private userService: UserService) {
    const storedUser = localStorage.getItem('loggedUser');
    if (storedUser) {
      this.user = JSON.parse(storedUser);
      this.isLogged = true;
    }
  }

  public getUser(): User | null {
    return this.user;
  }

  public isUserLogged(): boolean {
    return this.isLogged;
  }

  loginUser(username: string, password: string): Observable<User> {

    let loginRequest = { username: username, password: password };

    const result = this.httpClient.post<User>(`${this.url}users/login`, loginRequest);

    result.subscribe((user: User) => {
      if (user) {
        this.isLogged = true;
        this.user = user;

        localStorage.setItem('loggedUser', JSON.stringify(user));
      }
    });

    return result;
  }

  logoutUser(): void {
    this.isLogged = false;
    this.user = null;
    localStorage.removeItem('loggedUser');
  }
}
