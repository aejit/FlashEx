import { TestBed } from '@angular/core/testing';
import { ShipmentManagementService } from './shipment-management.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';

describe('ShipmentManagementService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientModule]
  }));
  it('should be created', () => {
    const service: ShipmentManagementService = TestBed.get(ShipmentManagementService);
    expect(service).toBeTruthy();
  });
});
