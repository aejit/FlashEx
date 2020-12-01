import { NgModule} from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';


const sharedRoutes: Routes = [
  {
    path: 'auth/login', component: LoginComponent
  },
  {
    path: 'auth/signup', component: SignUpComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(sharedRoutes)],
  exports: [RouterModule],
})
export class SharedRoutingModule { }
