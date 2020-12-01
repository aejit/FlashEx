import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthLoginInfo} from '../interfaces/logininfo';
import { SignUpInfo} from '../interfaces/signupinfo';
import { JwtResponse} from '../interfaces/jwtresponse';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(
    private http: HttpClient
  ) { }

  // get access to backend url for sign up and sign in
  private loginUrl = 'user-microservice/api/auth/signin';
  private signupUrl = 'user-microservice/api/auth/signup';


  // Provide access to login page for login page
  attemptAuth(credentials: AuthLoginInfo): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.loginUrl, credentials, httpOptions);
  }

  // Provide access to login page for sign up page
  signUp(info: SignUpInfo): Observable<string> {
    return this.http.post<string>(this.signupUrl, info, httpOptions);
  }
}
