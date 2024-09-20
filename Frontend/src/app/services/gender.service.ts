import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../models/user";
import {Gender} from "../models/gender";
import {Address} from "../models/address";

@Injectable({
  providedIn: 'root'
})
export class GenderService {

  private url: string = 'http://localhost:8080/';

  constructor(private httpClient: HttpClient) { }

  getGender(genderId: string): Observable<Gender> {
    return this.httpClient.get<Gender>(`${this.url}genders/getgender?id=${genderId}`);
  }

  getAllGenders(): Observable<Gender[]> {
    return this.httpClient.get<Gender[]>(`${this.url}genders/getgenders`);
  }

  createGender(myGender: Gender): Observable<Gender> {

    let gender = {name: myGender.name};
    return this.httpClient.post<Gender>(`${this.url}genders/creategender`, gender);
  }

  updateGender(myGender: Gender): Observable<Gender> {
    return this.httpClient.put<Gender>(`${this.url}genders/updategender`, myGender);
  }

  deleteGender(genderId: string): Observable<Boolean> {
    return this.httpClient.delete<Boolean>(`${this.url}genders/removegender?id=${genderId}`);
  }

  saveGendersChanges(gendersChanges: any[]): Observable<any> {
    return this.httpClient.post<any>(`${this.url}genders/savegenders`, gendersChanges);
  }

}
