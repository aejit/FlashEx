export interface IList {
  tripItineraryId: number;
  tripStartTime: string;
  tripEndTime: string;
  tripDistance: string;
  hubLocation: string;
  orders: [{
    orderId: number;
    name: string;
    contactNo: number;
    deliveryAddress: string;
    deliveryLocation: number[];
  }];
}
