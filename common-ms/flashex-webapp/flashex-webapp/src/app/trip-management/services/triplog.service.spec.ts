import { TestBed } from '@angular/core/testing';

import { TriplogService } from './triplog.service';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('TriplogService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [RouterTestingModule,
      HttpClientTestingModule]
  }));

  it('should be created', () => {
    const service: TriplogService = TestBed.get(TriplogService);
    expect(service).toBeTruthy();
  });
});
