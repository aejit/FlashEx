import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from '../../services/authentication.service';
import { TokenStorageService } from '../../services/token-storage.service';
import { AuthLoginInfo } from '../../interfaces/logininfo';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  private loginInfo: AuthLoginInfo;
  private authority: string;


  constructor(
    private authService: AuthenticationService, private tokenStorage: TokenStorageService,
    private route: Router
  ) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      // check if user is logged in or not
      this.isLoggedIn = true;
      // get the role from token
      this.roles = this.tokenStorage.getAuthorities();
      // provide routes checking logged in authority as admin
      if (this.roles[0] === 'ROLE_ADMIN') {
        this.route.navigate(['/admin']);
      } else if (this.roles[0] === 'ROLE_USER') {
        {
          // if not route to user if logged in as user
          this.route.navigate(['/user']);

        }
      }
    }
  }

  onSubmit() {
    // on submit login credentials ,username and password compared to backend
    this.loginInfo = new AuthLoginInfo(
      this.form.username,
      this.form.password);

    this.authService.attemptAuth(this.loginInfo).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveAuthorities(data.authorities);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getAuthorities(); if (this.roles[0] === 'ROLE_ADMIN') {
          // if logged in as admin routes to admin else to user
          this.route.navigate(['/admin']);
        } else if (this.roles[0] === 'ROLE_USER') {
          this.route.navigate(['/user']);

        }
      },
      error => {
        // console.log(error);
        this.errorMessage = error.error.message;
        this.isLoginFailed = true;
      }
    );
  }
}
