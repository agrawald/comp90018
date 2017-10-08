import {Injectable} from "@angular/core";
import {Sensor} from "../model/sensor";
import {Headers, Http, RequestOptions, URLSearchParams} from "@angular/http";
import {Observable} from "rxjs/Observable";
import "rxjs/add/operator/map";
import {environment} from "../../environments/environment";

/**
 * Function to add all the required parameter to be sent with every request made to Azure Storage account API.
 * @returns {any}
 */
function getParams() {
  const params = new URLSearchParams();
  const sas = environment.sas;
  const keys = Object.keys(sas);
  keys.forEach(key => params.set(key, sas[key]));
  return params;
}

/**
 * Function to generate the request header with common stuff
 * @returns {any}
 */
function getHeaders() {
  const headers = new Headers();
  headers.set('Accept', 'application/json');
  return headers;
}

/**
 * Function to generate request options
 * @returns {any}
 */
function getOptions() {
  const options = new RequestOptions({headers: getHeaders()});
  options.params = getParams();
  return options;
}

/**
 * Sensor Service which will make the REST API call to the Azure Storage Account API to fetch the data
 */
@Injectable()
export class SensorService {

  constructor(private http: Http) {
  }

  /**
   * Function to find all the data from the Azure Storage Account from RFIDAUTH table
   * @returns {Uint32Array|Uint8Array|Uint16Array|Int8Array|(Uint32Array|Uint8Array|Uint16Array|I
   * nt8Array|Sensor[]|Int32Array|any)[]|Int32Array|any}
   */
  findAll(): Observable<Sensor[]> {
    const sensors$ = this.http
      .get(`${environment.baseUrl}`, getOptions())
      .map((response) => {
        const json = response.json();
        return json.value.map(this.toSensor);
      });
    return sensors$;
  }

  /**
   * Function to find all the record satisfying the search criteria provided
   * @param rfid
   * @returns {Uint32Array|Uint8Array|Uint16Array|Int8Array|(Uint32Array|Uint8Array|Uint16Array|
   * Int8Array|Sensor[]|Int32Array|any)[]|Int32Array|any}
   */
  findAllFor(rfid: string): Observable<Sensor[]> {
    const options = getOptions();
    options.params.set('$filter', 'id eq \'' + rfid + '\'');
    const sensor$ = this.http
      .get(`${environment.baseUrl}`, options)
      .map((response) => {
        const json = response.json();
        return json.value.map(this.toSensor);
      });
    return sensor$;
  }

  /**
   * Function to convert the JSON record into Sensor data object
   * @param record
   * @returns {Sensor}
   */
  toSensor(record: any): Sensor {
    const message = JSON.parse(record.message)[0];
    const sensor = new Sensor();
    sensor.id = message.id;
    sensor.deviceId = message.deviceId;
    if (message.data) {
      sensor.data.temperature = message.data.temperature;
      sensor.data.humidity = message.data.humidity;
      if (message.data.location) {
        sensor.data.location.lng = message.data.location.lng;
        sensor.data.location.lat = message.data.location.lat;
      }
    }
    console.log('Parsed sensor:', sensor);
    return sensor;
  }
}
