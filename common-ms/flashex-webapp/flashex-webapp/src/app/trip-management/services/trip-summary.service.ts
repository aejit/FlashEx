import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { Itripsummary } from '../interfaces/trip-summary';
import { catchError } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class TripSummaryService {
  public handleError = [];
  // public date: Date;
  constructor(private http: HttpClient) {
    const date = new Date();
    console.log(date);
    let month = date.getMonth();
    month = month + 1;
    const day = date.getDate();
    const year = date.getFullYear();
    console.log(day, month, year);
    const dateString = day + '-' + month + '-' + year;
    this.loadSummary(dateString);
  }
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  };
  private tripSummaryUrl = 'tripplanning-microservice-webservice/api/v1/generate-summary';
  // private tripSummaryUrl = 'http://localhost:6900/summary'
  private dataSource = [];
  public behaviourSubject = new BehaviorSubject<Itripsummary[]>(this.dataSource);
  // get trip itinerary details
  loadSummary(dateString) {
    // this.http.get<Itripsummary[]>(this.tripSummaryUrl).subscribe(data => {
    this.http.get<Itripsummary[]>(this.tripSummaryUrl + '?date=' + dateString).subscribe(data => {
      // console.log(data);
      this.dataSource = data;
      this.behaviourSubject.next(data);
    },
      error => {
        this.handleError[0] = error;
        // console.log(error);
      });
    return this.dataSource;
  }
  getSummary() {
    return this.dataSource;
  }
}
