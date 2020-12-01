import { TestBed } from '@angular/core/testing';

import { TriplogService } from './triplog.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('TripItineraryService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule]
  }));

  it('should be created', () => {
    const service: TriplogService = TestBed.get(TriplogService);
    expect(service).toBeTruthy();
  });
});
