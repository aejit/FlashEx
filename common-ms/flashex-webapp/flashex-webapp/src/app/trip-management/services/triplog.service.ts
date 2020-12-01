import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ITripLog } from '../interfaces/triplog';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class TriplogService {

  constructor(private http: HttpClient) {
    this.load();
  }

  private url = 'triptracking-microservice-webservice/api/v1/triplogs';

  private dataSource = [];
  public behaviourSubject = new BehaviorSubject<ITripLog[]>(this.dataSource);

  load() {
    this.http.get<ITripLog[]>(this.url).subscribe(
      data => {
        for (let i = 0; i < 4; i++) {
          this.dataSource[i] = data[i];
        }
        this.behaviourSubject.next(this.dataSource);
      }
    );
  }

  // tslint:disable-next-line: ban-types
  getTripLog(id: string): Observable<Object> {
    return this.http.get('triptracking-microservice-webservice/api/v1/triplog?id=' + id);
  }

  // tslint:disable-next-line: ban-types
  updateTripLog(id: string, value: any): Observable<Object> {
    const options = { responseType: 'text' as 'json' };
    return this.http.put('triptracking-microservice-webservice/api/v1/updatelogs?id=' + id, value, options);
  }
  // tslint:disable-next-line: ban-types
  updatePacketLog(id: string, value: any, tripPacketId: string): Observable<Object> {
    const options = { responseType: 'text' as 'json' };
    return this.http.put('triptracking-microservice-webservice/api/v1/packetstatus?id=' + id +
      '&tripPacketId=' + tripPacketId, value, options);
  }
}
