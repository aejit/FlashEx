export interface Itripsummary {
  summaryId: string;
  summaryDate: Date;
  algorithms: string[];
  distanceSummary: number[];
  timeSummary: number[];
  costSummary: number[];
  droppedPacektsSummary: number[];
  ntrips: number[];
  volumeOccupancySummary: number[];
}
