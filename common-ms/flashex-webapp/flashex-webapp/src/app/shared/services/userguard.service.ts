import { Injectable } from '@angular/core';
import { TokenStorageService } from './token-storage.service';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserguardService {

  token: any;
  constructor(private auth: TokenStorageService, private router: Router) { }

  // Check for logged in authorities and let them access their respected path
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {

    const currentUser = this.auth.getAuthorities();
    // check for user authorities and expiration for token and let them access respectively
    if (currentUser[0] === 'ROLE_USER' && this.auth.isTokenExpired()) {
      return true;
    }
    // if not authorized, will be routed to login page
    this.router.navigate(['/auth/login'], { queryParams: { returnUrl: state.url } });
    return false;
  }}
