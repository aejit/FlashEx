import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TripPlanningPropertiesComponent } from './trip-planning-properties.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MaterialModule } from 'src/app/material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogRef } from '@angular/material';
import { TripDetailsComponent } from '../trip-details/trip-details.component';

describe('TripPlanningPropertiesComponent', () => {
  let component: TripPlanningPropertiesComponent;
  let fixture: ComponentFixture<TripPlanningPropertiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TripPlanningPropertiesComponent ],
      imports: [
        RouterTestingModule,
        HttpClientTestingModule,
        MaterialModule,
        ReactiveFormsModule,
        BrowserAnimationsModule
      ],
      providers: [
        {
          provide: MatDialogRef,
          // useValues: {}
          useValue: TripDetailsComponent
        }
        // {
        //   provide: MatDialogRef,
        //   useValue: true
        // }
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TripPlanningPropertiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    // console.log('Trip properties component creation test passed ------------------>');
    expect(component).toBeTruthy();
  });
});
