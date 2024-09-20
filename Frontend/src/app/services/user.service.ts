import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user';
import {Address} from "../models/address";
import {UserImage} from "../models/userImage";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private url: string = 'http://localhost:8080/';

  constructor(private httpClient: HttpClient) { }

  getUser(userId: string): Observable<User> {
    return this.httpClient.get<User>(`${this.url}users/getuser?id=${userId}`);
  }

  getAllUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>(`${this.url}users/getusers`);
  }

  createUser(user: User): Observable<User> {
    return this.httpClient.post<User>(`${this.url}users/createuser`, user);
  }

  updateUser(myUser: { user: User, addresses: Address[] }): Observable<User> {
    return this.httpClient.put<User>(`${this.url}users/updateuser`, myUser);
  }

  deleteUser(userId: string): Observable<Boolean> {
    return this.httpClient.delete<Boolean>(`${this.url}users/deleteuser?id=${userId}`);
  }

  getAllUsersImages(): Observable<UserImage[]> {
    return this.httpClient.get<UserImage[]>(`${this.url}users/getuserimages`);
  }

  getUserImage(userId: string): Observable<UserImage> {
    return this.httpClient.get<UserImage>(`${this.url}users/getuserimage?id=${userId}`);
  }

  createUserImage(userId: string, image: string): Observable<UserImage> {

    return this.httpClient.post<UserImage>(`${this.url}users/createuserimage`, image);
  }

  deleteUserImage(userImageId: string): Observable<Boolean> {
    return this.httpClient.delete<Boolean>(`${this.url}users/deleteuserimage?id=${userImageId}`);
  }
}

export function calculateAge(birthdate: string): number {

  const birthdateObj = new Date(birthdate);
  const today = new Date();

  let age = today.getFullYear() - birthdateObj.getFullYear();
  const monthDifference = today.getMonth() - birthdateObj.getMonth();

  if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < birthdateObj.getDate())) {
    age--;
  }

  return age;
}
