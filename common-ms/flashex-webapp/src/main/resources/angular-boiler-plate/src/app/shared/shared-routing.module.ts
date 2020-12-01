import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NavbarComponent } from './navbar/navbar.component';
import { LoginComponent } from './login/login.component';


const sharedRoutes: Routes = [
  {
    path: 'navbar', component: NavbarComponent
    },
  {
    path: 'login' , component: LoginComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(sharedRoutes)],
  exports: [RouterModule],
  // declarations: [ NavbarComponent, LoginComponent ]
})

export class SharedRoutingModule { }
