import { Component, OnInit } from '@angular/core';
import { TripItineraryService } from '../../services/trip-itinerary.service';
import { IVehicle } from '../../interfaces/vehicle';

@Component({
  selector: 'app-vehicle-details',
  templateUrl: './vehicle-details.component.html',
  styleUrls: ['./vehicle-details.component.css']
})
export class VehicleDetailsComponent implements OnInit {

  constructor(private tripItineraryService: TripItineraryService) { }

  public vehicleDataSource;

  ngOnInit() {
      this.tripItineraryService.vehicleBehaviourSubject.subscribe(data => {
      this.vehicleDataSource = data;
      console.log(this.vehicleDataSource);
    });
  }

}
