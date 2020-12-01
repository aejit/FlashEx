import { Component, OnInit } from '@angular/core';
import { TripItineraryService } from '../../services/trip-itinerary.service';
import { MatDialog } from '@angular/material';
import { TripPlanningPropertiesComponent } from '../trip-planning-properties/trip-planning-properties.component';
import { ITripProperties } from '../../interfaces/trip-planning-properties';
import { TripSummaryService } from '../../services/trip-summary.service';
import { Itripsummary } from '../../interfaces/trip-summary';
import { MatSnackBar } from '@angular/material';
import { FormControl } from '@angular/forms';
import { MatSlideToggleChange } from '@angular/material';
import { MatDatepickerModule } from '@angular/material/datepicker';


@Component({
  selector: 'app-trip-details',
  templateUrl: './trip-details.component.html',
  styleUrls: ['./trip-details.component.css']
})
export class TripDetailsComponent implements OnInit {

  public timeWindowDeliveryTrips = [];
  public vrpWithCCTrips = [];
  public vrpWithDVTrips = [];
  public vrpWithCCTripsUsingBing = [];
  public vrpWithDVTripsUsingBing = [];
  public timeWindowDeliveryTripsUsingBing = [];
  public otherTrips = [];
  public dataSource;
  public selectedAlgo;
  public orders;
  public userName;
  datepicked = new FormControl(new Date());
  public tripsDate = new Date().toDateString();
  public properties = {
    propertiesId: '1',
    algorithmSelected: 'Vrp with Capacity Constraint using Bing',
    lastUpdated: new Date()
  };
  public algorithms = ['Vrp with Capacity Constraint', 'Vrp with Dropping Visit', 'Vrp with Time Window Delivery'];
  public Bingalgorithm = ['Vrp with Capacity Constraint using Bing',
    'Vrp with Dropping Visit using Bing',
    'Vrp with Time Window Delivery using Bing'
  ];
  Bing = false;
  Default;

  multi1: any[];
  multi2: any[];

  summary: Itripsummary;
  public distanceCover = [];
  public totalTime = [];
  public totalExpense = [];
  public nTrips = [];
  public algorithm = [];

  view: any[] = [700, 400];

  // options
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = true;
  showXAxisLabel = true;
  showYAxisLabel = true;
  yAxisLabel = 'Z-Score';
  xAxisLabel = 'Algorithm';


  colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C'] // , '#AAAAAA', '#009688', '#69F0AE'
  };

  durationInSeconds = 5;

  constructor(private tripService: TripItineraryService,
              private tripsummary: TripSummaryService,
              private dialog: MatDialog, private snackBar: MatSnackBar) { }


  ngOnInit() {
    // this.properties = this.tripService.planningProperties;
    // console.log('Trip planning properties inside trip-details component-----> ');
    // console.log(this.properties);
    this.tripService.behaviourSubject.subscribe(data => {
      this.dataSource = data;

      // console.log(data);

      data.forEach(d => {
        // console.log('Algos list ----------> ', d.algorithm);
        if (d.algorithm === 'Vrp with Time Window Delivery') {
          this.timeWindowDeliveryTrips.unshift(d);
        } else
          if (d.algorithm === 'Vrp with Capacity Constraint') {
            this.vrpWithCCTrips.unshift(d);
          } else
            if (d.algorithm === 'Vrp with Dropping Visit') {
              this.vrpWithDVTrips.unshift(d);
            } else
              if (d.algorithm === 'Vrp with Capacity Constraint using Bing') {
                this.vrpWithCCTripsUsingBing.unshift(d);
              } else
                if (d.algorithm === 'Vrp with Dropping Visit using Bing') {
                  this.vrpWithDVTripsUsingBing.unshift(d);
                } else
                  if (d.algorithm === 'Vrp with Time Window Delivery using Bing') {
                    this.timeWindowDeliveryTripsUsingBing.unshift(d);
                  } else {
                    this.otherTrips.unshift(d);
                  }
      });

    });

    this.tripsummary.behaviourSubject.subscribe(data => {
      // console.log("Fetching summary ------->", data);

      data.forEach((d) => {
        // console.log("In loop ---->", d);
        d.distanceSummary.forEach(summary => {
          this.distanceCover.push(summary);
        });

        d.timeSummary.forEach(summary => {
          this.totalTime.push(summary);
        });

        d.costSummary.forEach(summary => {
          this.totalExpense.push(summary);
        });

        d.ntrips.forEach(summary => {
          this.nTrips.push(summary);
        });

        d.algorithms.forEach(summary => {
          this.algorithm.push(summary);
        });

        // console.log("values---->", this.distanceCover);
        // console.log("mean ---->",this.mean(this.distanceCover));
        // console.log("STD----->", this.standardDev(this.distanceCover));

        this.multi1 = [
          {
            name: 'Distance',
            series: [
              {
                name: this.algorithm[2],
                value: Math.abs((this.distanceCover[2] - this.mean(this.distanceCover))) / this.standardDev(this.distanceCover)
              },
              {
                name: this.algorithm[0],
                value: Math.abs((this.distanceCover[0] - this.mean(this.distanceCover))) / this.standardDev(this.distanceCover)
              },
              {
                name: this.algorithm[4],
                value: Math.abs((this.distanceCover[4] - this.mean(this.distanceCover))) / this.standardDev(this.distanceCover)
              }
            ]
          },
          {
            name: 'Time',
            series: [
              {
                name: this.algorithm[2],
                value: Math.abs((this.totalTime[2] - this.mean(this.totalTime))) / this.standardDev(this.totalTime)
              },
              {
                name: this.algorithm[0],
                value: Math.abs((this.totalTime[0] - this.mean(this.totalTime))) / this.standardDev(this.totalTime)
              },
              {
                name: this.algorithm[4],
                value: Math.abs((this.totalTime[4] - this.mean(this.totalTime))) / this.standardDev(this.totalTime)
              }
            ]
          },
          {
            name: 'Trips',
            series: [
              {
                name: this.algorithm[2],
                value: Math.abs((this.nTrips[2] - this.mean(this.nTrips))) / this.standardDev(this.nTrips)
              },
              {
                name: this.algorithm[0],
                value: Math.abs((this.nTrips[0] - this.mean(this.nTrips))) / this.standardDev(this.nTrips)
              },
              {
                name: this.algorithm[4],
                value: Math.abs((this.nTrips[4] - this.mean(this.nTrips))) / this.standardDev(this.nTrips)
              }
            ]
          },
          {
            name: 'Fuel Cost',
            series: [
              {
                name: this.algorithm[2],
                value: Math.abs((this.totalExpense[2] - this.mean(this.totalExpense))) / this.standardDev(this.totalExpense)
              },
              {
                name: this.algorithm[0],
                value: Math.abs((this.totalExpense[0] - this.mean(this.totalExpense))) / this.standardDev(this.totalExpense)
              },
              {
                name: this.algorithm[4],
                value: Math.abs((this.totalExpense[4] - this.mean(this.totalExpense))) / this.standardDev(this.totalExpense)
              }
            ]
          }
        ];

        this.multi2 = [
          {
            name: 'Distance',
            series: [
              {
                name: this.algorithm[3],
                value: Math.abs((this.distanceCover[3] - this.mean(this.distanceCover))) / this.standardDev(this.distanceCover)
              },
              {
                name: this.algorithm[1],
                value: Math.abs((this.distanceCover[1] - this.mean(this.distanceCover))) / this.standardDev(this.distanceCover)
              },
              {
                name: this.algorithm[5],
                value: Math.abs((this.distanceCover[5] - this.mean(this.distanceCover))) / this.standardDev(this.distanceCover)
              }
            ]
          },
          {
            name: 'Time',
            series: [
              {
                name: this.algorithm[3],
                value: Math.abs((this.totalTime[3] - this.mean(this.totalTime))) / this.standardDev(this.totalTime)
              },
              {
                name: this.algorithm[1],
                value: Math.abs((this.totalTime[1] - this.mean(this.totalTime))) / this.standardDev(this.totalTime)
              },
              {
                name: this.algorithm[5],
                value: Math.abs((this.totalTime[5] - this.mean(this.totalTime))) / this.standardDev(this.totalTime)
              }
            ]
          },
          {
            name: 'Trips',
            series: [
              {
                name: this.algorithm[3],
                value: Math.abs((this.nTrips[3] - this.mean(this.nTrips))) / this.standardDev(this.nTrips)
              },
              {
                name: this.algorithm[1],
                value: Math.abs((this.nTrips[1] - this.mean(this.nTrips))) / this.standardDev(this.nTrips)
              },
              {
                name: this.algorithm[5],
                value: Math.abs((this.nTrips[5] - this.mean(this.nTrips))) / this.standardDev(this.nTrips)
              }
            ]
          },
          {
            name: 'Fuel Cost',
            series: [
              {
                name: this.algorithm[3],
                value: Math.abs((this.totalExpense[3] - this.mean(this.totalExpense))) / this.standardDev(this.totalExpense)
              },
              {
                name: this.algorithm[1],
                value: Math.abs((this.totalExpense[1] - this.mean(this.totalExpense))) / this.standardDev(this.totalExpense)
              },
              {
                name: this.algorithm[5],
                value: Math.abs((this.totalExpense[5] - this.mean(this.totalExpense))) / this.standardDev(this.totalExpense)
              }
            ]
          }
        ];

      });
    });

  }

  Option1() {
    const temp = this.Bing;
    this.Bing = !temp;
  }

  openSnackBar() {
    // tslint:disable-next-line: no-use-before-declare
    this.snackBar.openFromComponent( GoogleNotAvailableComponent , {
      duration: 3 * 1000,
    });
  }

  Option3() {
    const temp = this.Bing;
    this.Bing = !temp;
  }

  // public toggle(event: MatSlideToggleChange) {
  //   console.log('toggle', event.checked);
  //   this.Bing = event.checked;
  // }

  mean(a: number[]) {
    const n = a.length;
    let sum = 0;
    for (let k = 0; k < n; k++) {
      sum = sum + a[k];
    }
    return sum / n;
  }

  // Function for calculating variance
  standardDev(a: number[]) {
    const n = a.length;
    const mean = this.mean(a);
    let sum = 0;
    for (let k = 0; k < n; k++) {
      sum = sum + Math.pow(a[k] - mean, 2);
    }
    sum = sum / n;

    return Math.sqrt(sum);
  }

  onSelect(event) {
    // console.log(event);
  }

  changeAlgo(algo: string) {
    const selectedAlgo = {
      propertiesId: '1',
      algorithmSelected: algo,
      lastUpdated: new Date()
    };
    // console.log("------------: ", selectedAlgo.algorithmSelected);
    // this.properties.algorithmSelected = algo;
    this.properties = Object.assign({}, selectedAlgo);

    // console.log("-----------sdfsdaf: ",this.tripService.planningProperties.algorithmSelected);
  }

  sendSelectedProperties() {
    this.properties.lastUpdated = new Date();
    this.tripService.updateOptimizationProperties(this.properties);
    // console.log("------------------>", this.properties);
  }

  openPropertiesDialog(): void {
    const dialogRef = this.dialog.open(TripPlanningPropertiesComponent, {
      width: '80%',
      data: { userName: this.userName, properties: this.properties }
    });
  }

  fetchSummary(selectedDate: string) {
    const date = new Date(selectedDate);
    let month = date.getMonth();
    month = month + 1;
    const day = date.getDate();
    const year = date.getFullYear();
    const dateString = day + '-' + month + '-' + year;
    // console.log(selectedDate, month, day, year);
    // this.tripsummary.loadSummary(dateString);
    // this.loadChartData();
    // console.log(this.single1);
  }
}

@Component({
  selector: 'app-snack-bar-component-snack',
  templateUrl: 'snack-bar-component-snack.html',
  styles: [`{}`],
})
export class GoogleNotAvailableComponent { }
