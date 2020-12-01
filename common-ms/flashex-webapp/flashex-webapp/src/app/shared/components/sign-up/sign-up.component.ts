import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { AuthenticationService } from '../../services/authentication.service';
import { SignUpInfo } from '../../interfaces/signupinfo';



@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  form: any = {};
  signupInfo: SignUpInfo;
  isSignedUp = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private authService: AuthenticationService) { }


  ngOnInit() {
  }
  onSubmit() {
    // sign up info from interfaces will be assigned to local signupInfo
    this.signupInfo = new SignUpInfo(
      this.form.name,
      this.form.username,
      this.form.email,
      this.form.password);

    // check if authorized and subscribe if sign up is successed or failed
    this.authService.signUp(this.signupInfo).subscribe(
      data => {
        // console.log(data);
        this.isSignedUp = true;
        this.isSignUpFailed = false;
      },
      // display error based on sign up failed message
      error => {
        // console.log(error);
        this.errorMessage = error.error.message;
        this.isSignUpFailed = true;
      }
    );
  }

}
