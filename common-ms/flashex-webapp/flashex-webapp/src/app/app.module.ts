import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material/material.module';
import { SharedModule } from './shared/shared.module';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { TripManagementModule } from './trip-management/trip-management.module';
import { DeliveryExecutiveModule } from './delivery-executive/delivery-executive.module';
import { STEPPER_GLOBAL_OPTIONS } from '@angular/cdk/stepper';
import { MAT_DIALOG_DEFAULT_OPTIONS } from '@angular/material';
import { FlexLayoutModule} from '@angular/flex-layout';
import { AuthguardService } from './shared/services/authguard.service';
import { HAMMER_LOADER } from '@angular/platform-browser';
import { NgxChartsModule } from '@swimlane/ngx-charts';


@NgModule({
  declarations: [
    AppComponent,
    LandingPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SharedModule,
    BrowserAnimationsModule,
    MaterialModule,
    TripManagementModule,
    DeliveryExecutiveModule,
    FlexLayoutModule,
    NgxChartsModule
  ],
  providers: [
    AuthguardService,
    {
      provide: STEPPER_GLOBAL_OPTIONS,
      useValue: { displayDefaultIndicatorType: false }
    },
    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {hasBackdrop: true}},
    {
      provide: HAMMER_LOADER,
      useValue: () => new Promise(() => {})
    }
  ],
  exports: [
    LandingPageComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
