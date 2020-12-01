import { Component, OnInit, Inject } from '@angular/core';
import { TripLog } from '../../../trip-management/interfaces/triplog';

declare let L;
declare let tomtom: any;

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  constructor() {
                }

  ngOnInit() {
  }
}
