import {Location} from "./location";

/**
 * Entity to represent the sensor data
 */
export class Data {
  temperature: number;
  humidity: number;
  location: Location = new Location();
}
