import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LiveTrackingGoogleComponent } from './live-tracking-google.component';
import { MaterialModule } from 'src/app/material/material.module';
import { AgmCoreModule } from '@agm/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TriplogService } from '../../services/triplog.service';
import { AgmDirectionModule } from 'agm-direction';


describe('LiveTrackingGoogleComponent', () => {
  let component: LiveTrackingGoogleComponent;
  let fixture: ComponentFixture<LiveTrackingGoogleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LiveTrackingGoogleComponent ],
      imports: [MaterialModule,
        AgmCoreModule.forRoot({
          apiKey: 'AIzaSyBLw09tedbxbyfc0Fgpy9Z30XhK53ClJDk'
        }),
        HttpClientModule,
        BrowserAnimationsModule,
        HttpClientTestingModule,
        AgmDirectionModule
      ],
      providers: [TriplogService ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LiveTrackingGoogleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
