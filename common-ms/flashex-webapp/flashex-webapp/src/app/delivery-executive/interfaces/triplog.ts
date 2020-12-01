export interface TripLog {
  tripItineraryId: string;
  tripStart: Date;
  tripEnd: Date;
  packetLogs: {
    packetId: string;
    deliveryAddress: {
      addressLine1: string;
      city: string;
      state: string;
      latitude: number,
      longitude: number,
      pincode: number
    };
    deliveryDescription: string;
    packetStatus: string;
  };
  plannedStartTime: Date;
  plannedEndTime: Date;
  originAddress: string;
}
export class TripLog {
  tripItineraryId: string;
  tripStart: Date;
  tripEnd: Date;
  packetLogs: {
    packetId: string;
    deliveryAddress: {
      addressLine1: string;
      city: string;
      state: string;
      latitude: number,
      longitude: number,
      pincode: number
    };
    deliveryDescription: string;
    packetStatus: string;
  };
  plannedStartTime: Date;
  plannedEndTime: Date;
  originAddress: string;
}
