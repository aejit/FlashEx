import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './components/home/home.component';
import { TripDetailsComponent } from './components/trip-details/trip-details.component';
import { OrderDetailsComponent } from './components/order-details/order-details.component';
import { LiveTrackingComponent } from './components/live-tracking/live-tracking.component';
import { VehicleDetailsComponent } from './components/vehicle-details/vehicle-details.component';
import { TripManagementRoutingModule } from './trip-management-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from '../material/material.module';
import { TripPlanningPropertiesComponent } from './components/trip-planning-properties/trip-planning-properties.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { TripItineraryService } from './services/trip-itinerary.service';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { StepperDialogComponent } from './components/stepper-dialog/stepper-dialog.component';
import { StatusDialogComponent } from './components/status-dialog/status-dialog.component';


import { LiveTrackingGoogleComponent } from './components/live-tracking-google/live-tracking-google.component';
import { AgmCoreModule } from '@agm/core';
import { AgmDirectionModule } from 'agm-direction';
import { SharedModule } from '../shared/shared.module';
import { SettingsComponent } from './components/settings/settings.component';

import { NgxChartsModule } from '@swimlane/ngx-charts';
import { GoogleNotAvailableComponent } from './components/trip-details/trip-details.component';




@NgModule({
  declarations: [
    HomeComponent,
    TripDetailsComponent,
    OrderDetailsComponent,
    LiveTrackingComponent,
    VehicleDetailsComponent,
    TripPlanningPropertiesComponent,
    DashboardComponent,
    LiveTrackingGoogleComponent,
    SettingsComponent,
    StepperDialogComponent,
    StatusDialogComponent,
    GoogleNotAvailableComponent
  ],
  imports: [
    CommonModule,
    TripManagementRoutingModule,
    HttpClientModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    NgxChartsModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyBLw09tedbxbyfc0Fgpy9Z30XhK53ClJDkyui'
   }),
   AgmDirectionModule,
   SharedModule
  ],
  entryComponents: [
    TripPlanningPropertiesComponent,
    SettingsComponent,
    StatusDialogComponent,
    GoogleNotAvailableComponent
  ],
  exports: [
    HomeComponent,
    TripDetailsComponent,
    OrderDetailsComponent,
    LiveTrackingComponent,
    VehicleDetailsComponent,
    TripPlanningPropertiesComponent,
    SettingsComponent,
    GoogleNotAvailableComponent
  ],
  providers: [
    TripItineraryService,
    GoogleNotAvailableComponent
  ],

})
export class TripManagementModule { }
