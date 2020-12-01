import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { IPacket } from '../interfaces/Packet';
import { HttpHeaders } from '@angular/common/http';
import { IShipmentConfig } from '../interfaces/ShipmentConfig';

@Injectable({
  providedIn: 'root'
})
export class ShipmentManagementService {

  public packetList: IPacket;
  public errormsg;
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  };

  constructor( private http: HttpClient) {
    this.load();
    this.loadConfig();
  }

   private url = 'shipment-microservice-webservice/api/v1/packets';
   // private url = '  http://localhost:3000/packet';
   private configUrl = 'shipment-microservice-webservice/api/v1/binner-config';

  private dataSource = [];
  private shipmentConfiguration = [];
  public behaviourSubject = new BehaviorSubject<IPacket[]>(this.dataSource);
  public configSubject = new BehaviorSubject<IShipmentConfig[]>(this.shipmentConfiguration);

  load() {
    this.http.get<IPacket[]>(this.url).subscribe(
      data => {
        this.dataSource = data;
        this.behaviourSubject.next(this.dataSource);
      },
      error => {
        this.errormsg = error.message;
      }
    );
  }
  loadConfig() {
    this.http.get<IShipmentConfig[]>(this.configUrl).subscribe(
      shipmentConfig => {
        this.shipmentConfiguration = shipmentConfig;
        this.configSubject.next(this.shipmentConfiguration);
      },
      error => {
        this.errormsg = error.message;
      }
    );
  }

  updateConfig(config: IShipmentConfig) {
    this.http.put(this.configUrl, config).subscribe(
      response => {
        // console.log(response);
      },
      error => {
        this.errormsg = error.message;
      }
    );
  }
}
