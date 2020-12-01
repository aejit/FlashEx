import { Injectable } from '@angular/core';
import Stomp from 'stompjs';
import SockJS from 'sockjs-client';
// import { Stomp, CompatClient, StompHeaders, StompSubscription } from '@stomp/stompjs';
// import * as sockjs from 'sockjs-client';
import { Subject, BehaviorSubject } from 'rxjs';
import { TripLog } from '../interfaces/triplog';
@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  // private serverUrl = 'http://localhost:6030/socket';
  private serverUrl = 'https://flashex-dev.stackroute.io/socket';
  public stompClient;
  // public realtimeSubject: BehaviorSubject<any> = new BehaviorSubject('');
  public realtimeSubject = new Subject();
  public that;
  constructor() {
    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
   }

   initializeWebSocketConnection() {
    const that = this;
    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe('/start/startdate', (tripLogMessage) => {
        this.that = tripLogMessage.body;
        // console.log("Websocket message received from spring ------ ", tripLogMessage, tripLogMessage.body);
        if ( tripLogMessage.body ) {
          that.realtimeSubject.next(tripLogMessage.body);
        }
      });
      that.stompClient.subscribe('/end/enddate', (tripLogMessage) => {
        this.that = tripLogMessage.body;
        if ( tripLogMessage.body ) {
          that.realtimeSubject.next(tripLogMessage.body);
        }
    });
  });
}

   sendDataForStartTrip(tripLog) {
    this.stompClient.send('/app/starttrip', {}, tripLog);
  }
  sendDataForEndTrip(tripLog) {
    this.stompClient.send('/app/endtrip', {}, tripLog);
  }
}
