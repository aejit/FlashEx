import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { TripsComponent } from './trips.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from '../../../material/material.module';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TriplogService } from '../../services/triplog.service';
import {RouterTestingModule} from '@angular/router/testing';


describe('TripsComponent', () => {
  let component: TripsComponent;
  let fixture: ComponentFixture<TripsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TripsComponent ],
      imports: [
        BrowserAnimationsModule,
        MaterialModule,
        HttpClientModule,
        HttpClientTestingModule,
        RouterTestingModule
      ],
      providers: [TriplogService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TripsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
