import { Component, OnInit, Sanitizer } from '@angular/core';
import { ChangeDetectorRef, OnDestroy } from '@angular/core';
import { MediaMatcher } from '@angular/cdk/layout';
import { TokenStorageService } from '../../../shared/services/token-storage.service';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { TriplogService } from '../../../trip-management/services/triplog.service';
import { ITripLog, TripLog } from '../../../trip-management/interfaces/triplog';
import { Observable } from 'rxjs';
import { Inject } from '@angular/core';
import { NavigationComponent } from '../navigation/navigation.component';
import { OrderDeliveryListComponent } from '../order-delivery-list/order-delivery-list.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { WebSocketService } from '../../services/websocket.service';
import * as lodash from 'lodash';

declare let L;
declare let tomtom: any;


@Component({
  selector: 'app-trips',
  templateUrl: './trips.component.html',
  styleUrls: ['./trips.component.css']
})
export class TripsComponent implements OnInit {

  mobileQuery: MediaQueryList;
  public dataSource = [];
  public details;
  public listofOrders;
  public tripDetails;
  step = 0;
  public tripLog: any;
  public tripLogById;
  triplogss: Observable<ITripLog[]>;
  public trip: any;
  public authority;
  public role;
  public userName;
  public scheduledDate = new Date();
  public intialData;
  public tripId;
  public start = false;
  public end = false;
  public tripDate = new Date().toDateString();
  public warehouse;
  public centerMap;
  public marks = [];
  public location;
  public markers = [];
  public colors = [];
  public color;
  public markerIcon = ['../../../../assets/mapIcon/78753.svg',
    '../../../../assets/mapIcon/2.svg',
    '../../../../assets/mapIcon/3.svg',
    '../../../../assets/mapIcon/4.svg',
    '../../../../assets/mapIcon/5.svg',
    '../../../../assets/mapIcon/6.svg',
    '../../../../assets/mapIcon/7.svg',
    '../../../../assets/mapIcon/8.svg',
    '../../../../assets/mapIcon/9.svg',
    '../../../../assets/mapIcon/10.svg',
  ];
  public addressLine = [];
  public routeColor = ['blue', 'red', 'green', 'black'];
  public lMap = false;
  private mobileQueryListener: () => void;



  constructor(changeDetectorRef: ChangeDetectorRef,
              private webSocketService: WebSocketService,
              media: MediaMatcher, private tripService: TriplogService,
              private tokenStorage: TokenStorageService, private router: Router, private snackBar: MatSnackBar) {
              this.mobileQuery = media.matchMedia('(max-width: 600px)');
              this.mobileQueryListener = () => changeDetectorRef.detectChanges();
              // tslint:disable-next-line: deprecation
              this.mobileQuery.addListener(this.mobileQueryListener);
              this.webSocketService.initializeWebSocketConnection();
  }

  openSnackBar() {
    this.snackBar.openFromComponent(NavigationComponent, {
      duration: 3 * 1000
    });
  }

  startSnackBar() {
    this.snackBar.openFromComponent(OrderDeliveryListComponent, {
      duration: 3000
    });
  }

  ngOnInit() {
    this.authority = this.tokenStorage.getAuthorities();
    if (this.authority[0] === 'ROLE_ADMIN') {
      this.role = 'Manager';

    } else if (this.authority[0] === 'ROLE_USER') {
      this.role = 'Delivery Executive';

    }

    // tslint:disable-next-line: deprecation
    this.mobileQuery.removeListener(this.mobileQueryListener);

    this.userName = this.tokenStorage.getUsername();
    this.tripService.behaviourSubject.subscribe(data => {
      if (data && data.length > 0) {
        if (this.userName === 'anurag123') {
          this.dataSource[0] = data[0];
        } else if (this.userName === 'anup123') {
          this.dataSource[0] = data[1];

        } else if (this.userName === 'parth123') {
          this.dataSource[0] = data[2];

        }
      }
      this.trips(0);
    });

    if (this.dataSource) {
      this.webSocketService.realtimeSubject.subscribe(d => {
        // let store = JSON.parse(d);
        if (typeof d === 'string') {
          const st = JSON.parse(d);
          console.log('tripId:', typeof d);
          const temp = lodash.find(this.dataSource, ['tripItineraryId', st.tripId]);
          // console.log("temp: ",temp);
          if (st.startTime) {
            temp.tripStart = new Date();
            this.start = true;
          }
          if (st.endTime) {
            // console.log('fasfsdfdf-----');
            temp.tripEnd = new Date();
            this.end = true;
          }
          // console.log("data::: ")
          const ans = this.dataSource.indexOf(temp);
          this.dataSource[ans] = temp;
          this.dataSource = this.dataSource;
        }

      });
    }

  }

  trips(value) {
    // console.log('vali', value);
    this.details = this.dataSource[value];
    // console.log('deta', this.details);
    if (this.details) {
      this.tripDetails = this.details;
      this.listofOrders = this.details.packetLogs;
      // console.log('lis', this.listofOrders);
    }
    // toggle();
  }
  getTripLogById(id: string) {
    this.tripService.getTripLog(id).subscribe(
      data => {
        this.tripLogById = data;

      }
    );
  }
  // update trip start time for particular trip with its id being fetched from UI
  updateTripStart(details, tripId) {
    this.trip = new TripLog();
    this.trip.tripStart = new Date();
    this.tripLog = details;
    this.tripLog.tripStart = new Date();
    this.tripId = tripId;
    const store = {
      /* tslint:enable:no-string-literal */
      tripId,
      startTime: true,
      endTime: false
    };
    this.webSocketService.sendDataForStartTrip(JSON.stringify(store));
    this.tripService.updateTripLog(tripId, this.trip).subscribe(data => {
      this.tripLog = data;
    });


  }


  // update trip end time for particular trip with its id being fetched from UI
  updateTripEnd(details, tripId) {
    this.trip.tripEnd = new Date();
    this.tripLog = details;
    this.tripLog.tripEnd = new Date();
    this.tripId = tripId;
    // console.log('enddate::',this.tripLog);

    const store = {
      /* tslint:disable:no-string-literal */
      tripId,
      startTime: false,
      endTime: true
    };
    this.webSocketService.sendDataForEndTrip(JSON.stringify(store));
    this.tripService.updateTripLog(tripId, this.trip).subscribe(data => {
      this.tripLog = details;

    });
  }

  // update packet status of particular packet id inside a particular trip itinerary
  updatePacketLog(tripId, tripPacketId) {
    // console.log('tr', tripId, ' pacl', tripPacketId);
    if (this.trip && this.trip.packetLogs && this.trip.packetLogs.packetStatus) {
      this.trip.packetLogs = [{ packetStatus: 'Delivered' }];
    } else {
      /* tslint:disable:no-string-literal */
      this.trip['packetLogs'] = [{ packetStatus: 'Delivered' }];
    }
    this.tripService.updatePacketLog(tripId, this.trip, tripPacketId).subscribe(
      data => {
        this.tripLog = data;
      }
    );
  }

  // update packet status of particular packet id inside a particular trip itinerary
  updatePacketUndelivered(tripId, tripPacketId) {
    console.log('tr', tripId, ' pacl', tripPacketId);
    if (this.trip && this.trip.packetLogs && this.trip.packetLogs.packetStatus) {
      this.trip.packetLogs = [{ packetStatus: 'Undelivered' }];
    } else {
      /* tslint:disable:no-string-literal */
      this.trip['packetLogs'] = [{ packetStatus: 'Undelivered' }];
    }
    this.tripService.updatePacketLog(tripId, this.trip, tripPacketId).subscribe(
      data => {
        this.tripLog = data;
      }
    );
  }
  logout() {
    this.tokenStorage.signOut();
    this.router.navigate(['/auth/login']);
  }
  loadMap() {
    this.lMap = !this.lMap;
    setTimeout(() => {
      const map = tomtom.L.map('map', {
        key: 'bvlnbSj7Eu5i41bgOFAlfWPZEuPkDcug',
        basePath: '/assets/sdk',
        center: this.centerMap,
        zoom: 18,
      });
      // tslint:disable-next-line: prefer-for-of
      for (let i = 0; i < this.dataSource.length; i++) {
        this.marks = [];
        this.warehouse = [
          this.dataSource[i].originAddress.latitude,
          this.dataSource[i].originAddress.longitude
        ];
        const packets = this.dataSource[i].packetLogs;
        // store delivery address and latitude and longitude to marks
        // tslint:disable-next-line: prefer-for-of
        for (let j = 0; j < packets.length; j++) {
          const deliveryAddress = packets[j].deliveryAddress;
          const mark = [deliveryAddress.latitude, deliveryAddress.longitude];
          this.centerMap = mark;
          const address = deliveryAddress.addressLine1;
          this.marks.push(mark);
          this.addressLine.push(address);
        }
        const warehouseMarker: any = tomtom.L.marker(this.warehouse, {
          icon: tomtom.L.icon({
            iconUrl: '../../../../assets/images/warehouse.png',
            iconSize: [40, 40],
            iconAnchor: [30, 30],
            popupAnchor: [0, -30]
          }),
        }).addTo(map);
        warehouseMarker.bindPopup(this.addressLine[i]).openPopup();
        // add marker to the map and attached delivery address to each marker
        // tslint:disable-next-line: prefer-for-of
        for (let m = 0; m < this.marks.length; m++) {
          const marker: any = tomtom.L.marker(this.marks[m], {
            icon: tomtom.L.icon({
              iconUrl: this.markerIcon[i],
              iconSize: [40, 40],
              iconAnchor: [30, 30],
              popupAnchor: [0, -30]
            }),
          }).addTo(map);
          marker.bindPopup(this.addressLine[i]).openPopup();

        }
        const routesColor = this.routeColor[i];
        const wareRoutes = this.warehouse.join(',').concat(':').concat(this.marks[0].join(','));
        tomtom.routing().locations(wareRoutes)
          // tslint:disable-next-line: only-arrow-functions
          .go().then(function(routeJson) {
            const route = tomtom.L.geoJson(routeJson, {
              style: { color: routesColor, opacity: 0.5, weight: 5 }
            }).addTo(map);
            map.fitBounds(route.getBounds(), { padding: [5, 5] });
          });
        // tslint:disable-next-line: prefer-for-of
        for (let n = 0; n < this.marks.length - 1; n++) {
          // store origin and destination for routes
          let routes = [];
          routes = this.marks[n].join(',').concat(':').concat(this.marks[n + 1].join(','));
          tomtom.routing().locations(routes)
            // tslint:disable-next-line: only-arrow-functions
            .go().then(function(routeJson) {
              const route = tomtom.L.geoJson(routeJson, {
                style: { color: routesColor, opacity: 0.5, weight: 5 }
              }).addTo(map);
              map.fitBounds(route.getBounds(), { padding: [5, 5] });
            });

        }
      }
    }, 1000);

  }

}





