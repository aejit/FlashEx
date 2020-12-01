import { Component, OnInit, Inject } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { ShipmentManagementService } from '../../services/shipment-management.service';
import { IShipmentConfig } from '../../interfaces/ShipmentConfig';
import { MatSlideToggleChange } from '@angular/material/slide-toggle';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class SettingsComponent implements OnInit {


  shipmentConfig: IShipmentConfig;
  updatedConfig: IShipmentConfig;
  errormsg: string[];
  checked = false;
  disabled = false;
  max = 40;
  min = 5;
  steps = 1;
  stepsRelax = 0.01;
  changeOfConfig = false;
  tickInterval = 1;
  tickIntervalRelax = 1;
  maxShipmentSize = 20;
  relaxation = 1.0;
  color = 'accent';
  pincodeChecked = true;
  orderTypeChecked = true;
  priorityChecked = false;
  volumeChecked = false;
  thumbLabel = true;
  minRelaxation = 0.5;
  maxRelaxation = 1.0;
  originAddress = new FormGroup({
    addressLine1: new FormControl(''),
    city: new FormControl(''),
    state: new FormControl(''),
    pincode: new FormControl(''),
    latitude: new FormControl(''),
    longitude: new FormControl('')
  });
  // });

  constructor(
    private shipmentManagementService: ShipmentManagementService,
    public settingsDialogRef: MatDialogRef<SettingsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: IShipmentConfig
  ) {
    this.settingsDialogRef.disableClose = true;
  }

  ngOnInit() {
    this.shipmentManagementService.configSubject.subscribe(
      config => {
        this.shipmentConfig = config[0];
        this.updatedConfig = this.shipmentConfig;
        for (const strategy of config[0].groupStrategy) {
          if (strategy === 'PINCODE') {
            this.pincodeChecked = true;
          } else if (strategy === 'PACKET_TYPE') {
            this.orderTypeChecked = true;
          } else if (strategy === 'PRIORITY') {
            this.priorityChecked = true;
          }
        }
        if (this.shipmentConfig !== null) {
          this.originAddress = new FormGroup({
            addressLine1: new FormControl(this.shipmentConfig.originAddress.addressLine1),
            city: new FormControl(this.shipmentConfig.originAddress.city),
            state: new FormControl(this.shipmentConfig.originAddress.state),
            pincode: new FormControl(this.shipmentConfig.originAddress.pincode),
            latitude: new FormControl(this.shipmentConfig.originAddress.latitude),
            longitude: new FormControl(this.shipmentConfig.originAddress.longitude)
          });
        }
      },
      error => {
        // console.log(error);
        this.errormsg.push(error);
      }
    );

    // set defaults

  }

  public togglePincode(event: MatSlideToggleChange) {
    // console.log('toggle', event.checked);
    this.pincodeChecked = event.checked;
    if (this.pincodeChecked === false && this.orderTypeChecked === false && this.priorityChecked === false) {
      this.orderTypeChecked = true;
    }
  }

  public toggleType(event: MatSlideToggleChange) {
    // console.log('toggle', event.checked);
    this.orderTypeChecked = event.checked;
    if (this.pincodeChecked === false && this.orderTypeChecked === false && this.priorityChecked === false) {
      this.pincodeChecked = true;
    }
  }

  public togglePriority(event: MatSlideToggleChange) {
    // console.log('toggle', event.checked);
    this.priorityChecked = event.checked;
    if (this.pincodeChecked === false && this.orderTypeChecked === false && this.priorityChecked === false) {
      this.pincodeChecked = true;
    }
  }

  public toggleVolume(event: MatSlideToggleChange) {
    // console.log('toggle', event.checked);
    this.volumeChecked = event.checked;
    if (this.pincodeChecked === false && this.orderTypeChecked === false && this.priorityChecked === false) {
      this.pincodeChecked = true;
    }
  }


  onNoClick(): void {
    this.settingsDialogRef.close();
  }

  close(): void {
    this.settingsDialogRef.close();
  }
  onSubmit() {

    this.updatedConfig.configDate = new Date();
    this.updatedConfig.maxShipmentSize = this.maxShipmentSize;
    this.updatedConfig.originAddress = this.originAddress.value;
    this.updatedConfig.relaxation = this.relaxation;
    // console.log(this.updatedConfig.originAddress);
    this.updatedConfig.groupStrategy = [];
    if (this.pincodeChecked) {
      this.updatedConfig.groupStrategy.push('PINCODE');
    }
    if (this.orderTypeChecked) {
      this.updatedConfig.groupStrategy.push('PACKET_TYPE');
    }
    if (this.priorityChecked) {
      this.updatedConfig.groupStrategy.push('PRIORITY');
    }
    this.shipmentManagementService.updateConfig(this.updatedConfig);
    this.settingsDialogRef.close();
  }
}

