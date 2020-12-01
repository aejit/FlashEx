import { TestBed } from '@angular/core/testing';

import { TripSummaryService } from './trip-summary.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('TripSummaryService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      HttpClientTestingModule
    ]
  }));

  it('should be created', () => {
    const service: TripSummaryService = TestBed.get(TripSummaryService);
    expect(service).toBeTruthy();
  });
});
