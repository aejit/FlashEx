import { Injectable } from '@angular/core';
import * as jwt_decode from 'jwt-decode';

const TOKEN_KEY = 'AuthToken';
const USERNAME_KEY = 'AuthUsername';
const AUTHORITIES_KEY = 'AuthAuthorities';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  private roles: Array<string> = [];
  constructor() { }
  // On clicking logout, clears all the session content
  signOut() {
    window.sessionStorage.clear();
  }
  // On sign in window will store TOKEN_KEY which will be saved as token
  public saveToken(token: string) {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }
  // Get the token from session storage
  public getToken(): string {
    return sessionStorage.getItem(TOKEN_KEY);
  }
  // Save username when logged in
  public saveUsername(username: string) {
    window.sessionStorage.removeItem(USERNAME_KEY);
    window.sessionStorage.setItem(USERNAME_KEY, username);
  }
  // Get username from logged in session storage
  public getUsername(): string {
    return sessionStorage.getItem(USERNAME_KEY);
  }
  // Save authorities
  public saveAuthorities(authorities: string[]) {
    window.sessionStorage.removeItem(AUTHORITIES_KEY);
    window.sessionStorage.setItem(AUTHORITIES_KEY, JSON.stringify(authorities));
  }
  // Get the authorities and store in roles
  public getAuthorities(): string[] {
    this.roles = [];

    if (sessionStorage.getItem(TOKEN_KEY)) {
      JSON.parse(sessionStorage.getItem(AUTHORITIES_KEY)).forEach(authority => {
        this.roles.push(authority.authority);
      });
    }

    return this.roles;
  }

  // Get the token expiration date from session storage which has AuthToken key
  // that consists all the detail for token
  // jwt_decode will decode the token and fetch expiration time
  getTokenExpirationDate(token: string): Date {
    const decoded = jwt_decode(window.sessionStorage.AuthToken);
    if (decoded.exp === undefined) { return null; }

    const date = new Date(0);
    date.setUTCSeconds(decoded.exp + 86400 + 300);
    return date;


  }
  // Check if token is expired : return true of false
  isTokenExpired(token?: string): boolean {
    if (!token) { token = window.sessionStorage.AuthToken; }
    if (!token) { return true; }

    const date = this.getTokenExpirationDate(token);
    if (date === undefined) { return false; }
    return (date.valueOf() > new Date().getTime());
  }
}
