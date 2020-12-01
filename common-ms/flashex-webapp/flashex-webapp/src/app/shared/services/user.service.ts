import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  // private userUrl = 'http://localhost:8080/user-microservice/api/test/user';
  // private adminUrl = 'http://localhost:8080/user-microservice/api/test/admin';

  // call the backend api to check the if the logged in person is user or admin and let them
  // access repected url
  private userUrl = 'user-microservice/api/test/user';
  private adminUrl = 'user-microservice/api/test/admin';
  constructor(private http: HttpClient) { }

  // Provide access to user board at backend if logged in as user
  getUserBoard(): Observable<string> {
    return this.http.get(this.userUrl, { responseType: 'text' });
  }

  // Provide access to admin board at backend if logged in as admin
  getAdminBoard(): Observable<string> {
    return this.http.get(this.adminUrl, { responseType: 'text' });
  }
}
