import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Gender} from "../models/gender";
import {JobPosition} from "../models/jobPosition";

@Injectable({
  providedIn: 'root'
})
export class JobPositionService {

  private url: string = 'http://localhost:8080/';

  constructor(private httpClient: HttpClient) {}

  getAllJobPositions(): Observable<JobPosition[]> {
    return this.httpClient.get<JobPosition[]>(`${this.url}jobpositions/getjobpositions`);
  }

  getJobPosition(jobPositionId: string): Observable<JobPosition> {
    return this.httpClient.get<JobPosition>(`${this.url}jobpositions/getjobposition?id=${jobPositionId}`);
  }

  createJobPosition(jobPosition: {name: string}): Observable<JobPosition> {
    return this.httpClient.post<JobPosition>(`${this.url}jobpositions/createjobposition`, jobPosition);
  }

  updateJobPosition(jobPosition: JobPosition): Observable<JobPosition> {
    return this.httpClient.put<JobPosition>(`${this.url}jobpositions/updatejobposition`, jobPosition);
  }

  deleteJobPosition(jobPositionId: string): Observable<Boolean> {
    return this.httpClient.delete<Boolean>(`${this.url}jobpositions/removejobposition?id=${jobPositionId}`);
  }

  saveJobPositionsChanges(jobPositionsChanges: any[]): Observable<any> {
    return this.httpClient.post<any>(`${this.url}jobpositions/savejobpositions`, jobPositionsChanges);
  }

}
