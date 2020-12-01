import { TestBed } from '@angular/core/testing';

import { UserguardService } from './userguard.service';
import { RouterTestingModule } from '@angular/router/testing';

describe('UserguardService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [
      RouterTestingModule
    ]
  }));

  it('should be created', () => {
    const service: UserguardService = TestBed.get(UserguardService);
    expect(service).toBeTruthy();
  });
});
