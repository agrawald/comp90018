# rfid-raspberry-pi-reader

This raspberrry pi, node.js application will be used to read the RFID (RC522) and other sensor data from the sensors attched to raspberry pi and sent the JSON packet to Azure for safe keeping or further processing

## basic idea
When an RFID tag is scanner on the RFID reader, this application will parse the RFID data and other sensore data and sent them in JSON format to Azure for storage and further processing. Such as sending notifications to rfid-android-notifier application.

## technologies
- Node.js
- Azure
- NPM
- ECMA6
- RC522 - RFID Sensor
- BME280 - Environmental Data Sensor 
