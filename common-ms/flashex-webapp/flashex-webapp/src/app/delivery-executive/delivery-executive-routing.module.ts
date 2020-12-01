import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NavigationComponent } from './components/navigation/navigation.component';
import { OrderDeliveryListComponent } from './components/order-delivery-list/order-delivery-list.component';
import { TripsComponent } from './components/trips/trips.component';
import { UserguardService } from '../shared/services/userguard.service';
const deliveryRoutes: Routes = [
  {
    path: 'user', component: TripsComponent,
    canActivate: [UserguardService],
    data: {
      expectedRole: 'ROLE_USER'
    },
    children: [
      {
        path: 'navigation', component: NavigationComponent,
        canActivate: [UserguardService],
        data: {
          expectedRole: 'ROLE_USER'
        },
      },
      {
        path: 'order', component: OrderDeliveryListComponent,
        canActivate: [UserguardService],
        data: {
          expectedRole: 'ROLE_USER'
        },
      },
    ]
  }
];
@NgModule({
  imports: [RouterModule.forChild(deliveryRoutes)],
  exports: [RouterModule],
})
export class DeliveryExecutiveRoutingModule { }
