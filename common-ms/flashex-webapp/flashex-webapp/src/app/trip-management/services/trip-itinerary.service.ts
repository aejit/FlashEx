import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { ITripProperties } from '../interfaces/trip-planning-properties';
import { IItinerary } from '../interfaces/trip-itinerary';
import { IVehicle } from '../interfaces/vehicle';

@Injectable({
  providedIn: 'root'
})

export class TripItineraryService {

  public location;
  private planningProperties: {
    propertiesId: '1',
    algorithmSelected: 'Vrp with Capacity Constraint using Bing',
    lastUpdated: Date
  };
  public selectedAlgo;
  public handleError = [];
  public date: Date;

  constructor(private http: HttpClient) {
    this.date = new Date();
    this.load();
   }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  };

  // private tripItineraryUrl = '../../../assets/tripsListFormat2.json';

  private tripItineraryUrl = 'tripplanning-microservice-webservice/api/v1/tripitinerary';
  // private tripItineraryUrl = 'http://localhost:3000/tripItinerary';

  // private vehiclesListUrl = 'https://vehicle-json-server:3000/vehicles';
  private vehiclesListUrl = 'http://flashex-dev.stackroute.io:3000/vehicles';

  private optimizationPropertiesUrl = 'tripplanning-microservice-webservice/api/v1/optprops';

  private dataSource = [];
  public behaviourSubject = new BehaviorSubject<IItinerary[]>(this.dataSource);
  private vehiclesData = [];
  public vehicleBehaviourSubject = new BehaviorSubject<IVehicle[]>(this.vehiclesData);

  // get trip itinerary details

  load() {
    this.http.get<IItinerary[]>(this.tripItineraryUrl).subscribe(data => {
      this.dataSource = data;
      this.behaviourSubject.next(this.dataSource);
      // console.log('Obtained Trip itineraries from the db ---------------------->', data);
    },
    error => {
      this.handleError[1] = error;
    });

    // get vehicle details

    this.http.get<IVehicle[]>(this.vehiclesListUrl).subscribe(data => {
      this.vehiclesData = data;
      console.log(this.vehiclesData);
      this.vehicleBehaviourSubject.next(this.vehiclesData);
    },
    error => {
      this.handleError[2] = error;
    });

    this.http.get<ITripProperties>(this.optimizationPropertiesUrl).subscribe(data => {
      if (data != null) {
        this.planningProperties = data[0];
        // console.log('Obtained from DB ----> ', this.planningProperties);
      } else {
        this.planningProperties = {
          propertiesId: '1',
          algorithmSelected: 'Vrp with Capacity Constraint using Bing',
          lastUpdated: this.date
        };
        // console.log('Created in angular ----> ', this.planningProperties);
      }
    },
    error => {
      this.handleError[3] = error;
    });
  }

  updateOptimizationProperties(properties: ITripProperties) {
    this.http.put<ITripProperties>( this.optimizationPropertiesUrl,
                                    properties,
                                    this.httpOptions);
    // console.log('Sending the updated optimization -----> ',
    // properties.lastUpdated,
    // properties.algorithmSelected, properties.propertiesId);
  }
}
