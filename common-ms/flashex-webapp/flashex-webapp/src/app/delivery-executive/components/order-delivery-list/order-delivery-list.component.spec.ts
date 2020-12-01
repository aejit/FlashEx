import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderDeliveryListComponent } from './order-delivery-list.component';

describe('OrderDeliveryListComponent', () => {
  let component: OrderDeliveryListComponent;
  let fixture: ComponentFixture<OrderDeliveryListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderDeliveryListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderDeliveryListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
