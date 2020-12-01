import { TestBed } from '@angular/core/testing';

import { AuthguardService } from './authguard.service';
import { RouterTestingModule } from '@angular/router/testing';

describe('AuthguardService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [RouterTestingModule]
  }));

  it('should be created', () => {
    const service: AuthguardService = TestBed.get(AuthguardService);
    expect(service).toBeTruthy();
  });
});
