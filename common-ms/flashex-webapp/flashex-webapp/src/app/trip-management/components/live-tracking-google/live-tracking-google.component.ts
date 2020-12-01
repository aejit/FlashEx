import { Component, OnInit } from '@angular/core';
import { TriplogService } from '../../services/triplog.service';
import { TripLog, ITripLog } from '../../interfaces/triplog';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-live-tracking-google',
  templateUrl: './live-tracking-google.component.html',
  styleUrls: ['./live-tracking-google.component.css']
})
export class LiveTrackingGoogleComponent implements OnInit {

  public lng;
  public lat;
  public dataSource;
  public orders;
  public tripLogById;
  public tripLog: any;

  // public location;
  public markers = [];
  public colors = [];
  public zoom = 10;
  public dir;
  public origin: any;
  public destination: any;
  public renderOptions = {
    suppressMarkers: true,
  };
  public markerColor = [];
  public color;
  public storedColor = [];
  public url;
  public warehouse;
  public routes = [];
  public tripDate = new Date().toDateString();

  displayedColumns: string[] = ['orderId', 'status'];
  public scheduledDate = new Date();
  triplogss: Observable<ITripLog[]>;
  public trip: any;
  constructor(private tripLogService: TriplogService) { }

  ngOnInit() {
    this.tripLogService.behaviourSubject.subscribe(data => {
      this.dataSource = data;
      this.getRandomColor();
      this.url = '../../../../assets/images/warehouse.png';
      // tslint:disable-next-line: prefer-for-of
      for (let i = 0; i < data.length; i++) {
        const tripItinerary = this.dataSource[i];
        this.warehouse = {
          latitude: tripItinerary.originAddress.latitude,
          longitude: tripItinerary.originAddress.longitude
        };
        // console.log('ware', this.warehouse);
        // console.log(tripItinerary.orders.length)
        if (tripItinerary && tripItinerary.packetLogs) {
          const genColor = this.colors;
          // tslint:disable-next-line: prefer-for-of
          for (let k = 0; k < tripItinerary.packetLogs.length; k++) {
            this.markerColor.push(genColor[i]);
          }
          this.markers.push(tripItinerary.packetLogs);
          this.lat = tripItinerary.packetLogs[1].deliveryAddress.latitude;
          this.lng = tripItinerary.packetLogs[1].deliveryAddress.longitude;
        }
      }
      this.markers = [].concat.apply([], this.markers);
    });
  }
  trips(value) {
    // console.log('value', value);
    this.routes = [];
    const tripItinerary = this.dataSource[value];

    if (tripItinerary && tripItinerary.packetLogs) {
      this.markers = tripItinerary.packetLogs;
      this.lat = tripItinerary.packetLogs[1].deliveryAddress.latitude;
      this.lng = tripItinerary.packetLogs[1].deliveryAddress.longitude;
      this.zoom = 12;
      this.origin = { lat: this.warehouse.latitude, lng: this.warehouse.longitude };
      this.destination = {
        lat: tripItinerary.packetLogs[0].deliveryAddress.latitude,
        lng: tripItinerary.packetLogs[0].deliveryAddress.longitude
      };
      this.routes.push({ origin: this.origin, dest: this.destination });
      for (let j = 0; j < tripItinerary.packetLogs.length - 1; j++) {
        this.origin = {
          lat: tripItinerary.packetLogs[j].deliveryAddress.latitude,
          lng: tripItinerary.packetLogs[j].deliveryAddress.longitude
        };
        this.destination = {
          lat: tripItinerary.packetLogs[j + 1].deliveryAddress.latitude,
          lng: tripItinerary.packetLogs[j + 1].deliveryAddress.longitude
        };
        this.routes.push({ origin: this.origin, dest: this.destination });
        // console.log('route', this.routes);
      }
    }
  }
  getRandomColor() {
    this.dataSource.forEach((element, i) => {
      const color = Math.floor(0x1000000 * Math.random()).toString(16);
      const generatedColor = '#' + ('000000' + color).slice(-6);
      this.colors.push(generatedColor);

    });
  }
  // get trip log by its id from backend
  getTripLogById(id: string) {
    this.tripLogService.getTripLog(id).subscribe(
      data => {
        this.tripLogById = data;

      }
    );
  }
  // update trip start time for particular trip with its id being fetched from UI
  updateTripStart(tripId) {
    this.trip = new TripLog();
    this.trip.tripStart = new Date();
    this.tripLogService.updateTripLog(tripId, this.trip).subscribe(data => {
      this.tripLog = data;
    });
  }
  // update trip end time for particular trip with its id being fetched from UI
  updateTripEnd(tripId) {
    this.trip.tripEnd = new Date();
    this.tripLogService.updateTripLog(tripId, this.trip).subscribe(data => {
      this.tripLog = data;
    });
  }

  // update packet status of particular packet id inside a particular trip itinerary
  updatePacketLog(tripId, tripPacketId) {
    if (this.trip && this.trip.packetLogs && this.trip.packetLogs.packetStatus) {
      this.trip.packetLogs = [{ packetStatus: 'Delivered' }];
    } else {
      /* tslint:disable:no-string-literal */
      this.trip['packetLogs'] = [{ packetStatus: 'Delivered' }];
    }
    this.tripLogService.updatePacketLog(tripId, this.trip, tripPacketId).subscribe(
      data => {
        this.tripLog = data;
      }
    );
  }
}
