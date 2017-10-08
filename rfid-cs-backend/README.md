# rfid-cs-backend [AZURE URL](http://weblocatorrfid.azurewebsites.net) 

This project was generated using C# .NET framework.

This application acts as Azure Mobile backend service exposing REST API to manuplate RFID data and send cloud-to-device notification.

## basic idea
The idea is to expose following REST API to help mobile frontend application to perform database CRUD operation
 - Get all the rfid authorization records
 - Insert rfid authorization records
 - Delete rfid authorization records
 - Update rfid authorization records
 - Send notifications from cloud-to-device when notification requets was sent by the RFID reader device

## technologies

 - C#
 - Entity Framework
 - Azure Mobile Services
 - REST API
 - Azure SQL DB
 - Azure IoT Hub

## future scope

 - can be enhanced to have some health endpoints and get some ML/AI insights using Azure Logic App or ML/AI capability. 

