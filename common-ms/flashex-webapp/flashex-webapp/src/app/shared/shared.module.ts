import { NgModule } from '@angular/core';
import { NavbarComponent } from './components/navbar/navbar.component';
import { MaterialModule } from '../material/material.module';
import { CommonModule } from '@angular/common';
import { SharedRoutingModule } from './shared-routing.module';
import { LoginComponent } from './components/login/login.component';
import { AppRoutingModule } from '../app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthenticationService } from './services/authentication.service';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { UserService} from '../shared/services/user.service';
import { HttpClientModule } from '@angular/common/http';
import { httpInterceptorProviders } from './services/auth-interceptor';
import { LandingPageComponent } from '../landing-page/landing-page.component';


@NgModule({
  declarations: [NavbarComponent, LoginComponent, SignUpComponent],
  imports: [
    CommonModule,
    MaterialModule,
    SharedRoutingModule,
    AppRoutingModule,
    SharedRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [AuthenticationService, UserService, httpInterceptorProviders],
  exports: [ NavbarComponent, LoginComponent, SignUpComponent]
})
export  class SharedModule { }
