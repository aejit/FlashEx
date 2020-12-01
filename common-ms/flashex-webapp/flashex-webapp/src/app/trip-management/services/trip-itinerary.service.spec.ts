import { TestBed } from '@angular/core/testing';

import { TripItineraryService } from './trip-itinerary.service';
import { HttpClientModule } from '@angular/common/http';

describe('TripItineraryService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientModule]
  }));

  it('should be created', () => {
    const service: TripItineraryService = TestBed.get(TripItineraryService);
    expect(service).toBeTruthy();
  });
});
