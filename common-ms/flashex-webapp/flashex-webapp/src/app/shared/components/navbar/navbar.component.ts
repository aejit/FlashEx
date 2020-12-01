import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../../services/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  public authority;
  public role;
  public userName;
  constructor(private tokenStorage: TokenStorageService, private router: Router) { }

  ngOnInit() {
    this.authority = this.tokenStorage.getAuthorities();
    if (this.authority[0] === 'ROLE_ADMIN') {
      this.role = 'Manager';

    } else if (this.authority[0] === 'ROLE_USER') {
      this.role = 'Delivery Executive';

    }
    this.userName = this.tokenStorage.getUsername();
  }
  logout() {
    this.tokenStorage.signOut();
    this.router.navigate(['/auth/login']);
  }
}
