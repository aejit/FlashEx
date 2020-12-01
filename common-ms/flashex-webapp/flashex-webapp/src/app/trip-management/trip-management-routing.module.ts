import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LiveTrackingComponent } from './components/live-tracking/live-tracking.component';
import { VehicleDetailsComponent } from './components/vehicle-details/vehicle-details.component';
import { TripDetailsComponent } from './components/trip-details/trip-details.component';
import { OrderDetailsComponent } from './components/order-details/order-details.component';
import { AuthguardService } from '../shared/services/authguard.service';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LiveTrackingGoogleComponent } from './components/live-tracking-google/live-tracking-google.component';

const tripRoutes: Routes = [
  {
    path: 'admin',
    component: HomeComponent,
    // canActivate: [AuthguardService],
    // data: {
    //   expectedRole: 'ROLE_ADMIN'
    // },
    children: [
      {
        path: 'tripitinerary',
        component: TripDetailsComponent,
        // canActivate: [AuthguardService],
        // data: {
        //   expectedRole: 'ROLE_ADMIN'
        // }
      },
      {
        path: 'triptrack',
        component: LiveTrackingComponent,
        // canActivate: [AuthguardService],
        // data: {
        //   expectedRole: 'ROLE_ADMIN'
        // }
      },
      {
        path: 'orderdetail',
        component: OrderDetailsComponent,
        // canActivate: [AuthguardService],
        // data: {
        //   expectedRole: 'ROLE_ADMIN'
        // }
      },
      {
        path: 'vehicledetail',
        component: VehicleDetailsComponent,
        // canActivate: [AuthguardService],
        // data: {
        //   expectedRole: 'ROLE_ADMIN'
        // }
      },
      {
        path: 'trackgoogle',
        component: LiveTrackingGoogleComponent,
        // canActivate: [AuthguardService],
        // data: {
        //   expectedRole: 'ROLE_ADMIN'
        // }
      },
      {
        path: '',
        component: DashboardComponent,
        pathMatch: 'full'
      }
    ]
  },
];


@NgModule({
  imports: [RouterModule.forChild(tripRoutes)],
  exports: [RouterModule],
})
export class TripManagementRoutingModule { }
