import {AfterContentInit, Component, ViewChild} from "@angular/core";
import {SensorService} from "./services/sensor.service";
import {Sensor} from "./model/sensor";
import {AgmMap} from "@agm/core";

/**
 * Main Application component to provide the master container for all the other compoenents
 */
@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css'],
})
export class AppComponent implements AfterContentInit {

  title = 'RFID Web Locator';
  sensors: Sensor[] = [];
  geolocationPosition: Position;
  zoomLevel = 8;
  @ViewChild(AgmMap) public agmMap: AgmMap;

  constructor(private sensorSvc: SensorService) {
  }

  OnMapReady($event) {
    this.initCurrentLocation();
    //Lest find all the sensor data as soon as map is available
    this.sensorSvc.findAll().subscribe(data => this.sensors = data);
  }

  ngAfterContentInit(): void {
    //we need to reposition when new content is identified
    this.repositionMap();
  }

  /**
   * This method will get the current position of the machine on which the application is running
   * using browser location service and display it on the map
   */
  initCurrentLocation(): void {
    if (window.navigator && window.navigator.geolocation) {
      window.navigator.geolocation.getCurrentPosition(
        position => {
          this.geolocationPosition = position;
          this.repositionMap();
        },
        error => {
          switch (error.code) {
            case 1:
              console.log('Permission Denied');
              break;
            case 2:
              console.log('Position Unavailable');
              break;
            case 3:
              console.log('Timeout');
              break;
          }
        }
      );
    }
  }

  openNav() {
    document.getElementById('mySidenav').style.width = '250px';
    document.getElementById('map-wrapper').style.marginLeft = '250px';
    document.getElementById('map-wrapper').style.backgroundColor = 'rgba(0,0,0,0.4)';
  }

  closeNav() {
    document.getElementById('mySidenav').style.width = '0';
    document.getElementById('map-wrapper').style.marginLeft = '0';
    document.getElementById('map-wrapper').style.backgroundColor = 'white';
  }

  /**
   * When user performs a search for a specific RFID this method will be called to fulfill the request
   * @param form the form data which contains the RFID
   */
  submitForm(form: any): void {
    if (form.rfid) {
      this.sensorSvc.findAllFor(form.rfid).subscribe(data => {
        this.sensors = data;
        this.repositionMap();
      });
    } else {
      this.sensorSvc.findAll().subscribe(data => {
        this.sensors = data;
        this.repositionMap();
      });
    }
  }

  /**
   * Function to reposition map after we are done plotting the coordinates
   */
  private repositionMap() {
    if (this.geolocationPosition) {
      this.agmMap.latitude = this.geolocationPosition.coords.latitude;
      this.agmMap.longitude = this.geolocationPosition.coords.longitude;
    }
    this.agmMap.zoom = 6;
    this.agmMap.triggerResize(true);
  }
}
