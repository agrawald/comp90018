import {Data} from "./data";

/**
 * Entity to represent sensor information with all the sensor data and device information
 */
export class Sensor {
  id: string;
  deviceId: string;
  data: Data = new Data();
}
