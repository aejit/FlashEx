import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../shared/services/token-storage.service';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent implements OnInit {
  constructor() { }
  ngOnInit() {
  }
}
