import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ITripProperties } from '../../interfaces/trip-planning-properties';
import { FormBuilder, Validators } from '@angular/forms';
import { TripItineraryService } from '../../services/trip-itinerary.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-trip-planning-properties',
  templateUrl: './trip-planning-properties.component.html',
  styleUrls: ['./trip-planning-properties.component.css']
})
export class TripPlanningPropertiesComponent implements OnInit {

  constructor(private dialogRef: MatDialogRef<TripPlanningPropertiesComponent>,
              private router: Router) { dialogRef.disableClose = false; }

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
