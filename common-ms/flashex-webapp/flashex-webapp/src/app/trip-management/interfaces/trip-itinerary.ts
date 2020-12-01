import { IPacket } from '../interfaces/Packet';
import { IVehicle } from '../interfaces/vehicle';
import { IDeliveryAddress } from '../interfaces/deliveryAddress';

export interface IItinerary {
  tripItineraryId: string;
  packets: IPacket[];
  planGeneratedTime: Date;
  plannedStartTime: Date;
  plannedEndTime: Date;
  plannedTotalDistance: number;
  vehicle: IVehicle;
  tripExpense: number;
  occupiedVolume: number;
  originAddress: IDeliveryAddress;
  algorithm: string;
  droppedPackets: IPacket[];
}
