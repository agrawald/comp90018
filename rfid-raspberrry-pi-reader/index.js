'use strict';
/**
 * Main javascript file to start the application including RFID sensor
 * @type {*|exports}
 */

const Request = require('tedious').Request;
const Connection = require('tedious').Connection;
const AzureIoT = require('./js/azureIoT');
const rc522 = require("rc522");
const config = require('./config.json');

const azureIoT = new AzureIoT(config);
console.log("Starting the RFID listener...");
//start reading a new RFID tag
rc522(function (rfidSerialNumber) {
    console.log("---------------------------------");
    console.log("Tag Scanned: " + rfidSerialNumber);
    let connection = new Connection(config.database);
    //register a callback after we successfully connect with azure database
    connection.on('connect', function (err) {
        // If no error, then good to go...
        if (!err) {
            //now start the rfid verification
            executeStatement();
        }
    });

    /**
     * Function to perform all the processing for RFID
     */
    function executeStatement() {
        //create the db request to get the authorization
        let request = new Request("select authorized from rfidAuth where id='" + rfidSerialNumber + "'",
            function (err) {
                if (err) {
                    console.error("Error while querying the DB", err);
                }
            });
        //lets register a method to get the rows
        request.on('row', function (columns) {
            columns.forEach(function (column) {
                let type;
                if (column.value) {
                    // open the gate here
                    console.log("The Tag (" + rfidSerialNumber + ") is authorized, opening the gate.");
                    console.log("---------------------------------");
                    type = 0;
                } else {
                    console.log("The Tag (" + rfidSerialNumber + ") is not authorized, waiting for the authorization.");
                    type = 1;
                }

                //even if the access is authorized we want to audit every swipe
                azureIoT.sendMessage({
                    id: rfidSerialNumber,
                    type: type
                });
            });
        });

        connection.execSql(request);
    }

});
