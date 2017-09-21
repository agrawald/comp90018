'use strict';

const Request = require('tedious').Request;
const Connection = require('tedious').Connection;
const AzureIoT = require('./js/azureIoT');
const rc522 = require("rc522");
let config;

// read in configuration in config.json
try {
    console.log("Loading the configuration...");
    config = require('./config.json');
} catch (err) {
    console.error('Failed to load config.json: ' + err.message);
    return;
}
const azureIoT = new AzureIoT(config);
console.log("Starting the RFID listener...");
rc522(function (rfidSerialNumber) {
    console.log(rfidSerialNumber);
    let connection = new Connection(config);
    connection.on('connect', function (err) {
        // If no error, then good to go...
        executeStatement();
    });

    function executeStatement() {
        //create the db request to get the authorization
        let request = new Request("select authorized from rfid_auth where id='" + rfidSerialNumber + "'",
            function (err, rowCount) {
                if (err) {
                    console.log(err);
                } else {
                    console.log(rowCount + ' rows');
                }
            });
        //lets register a method to get the rows
        request.on('row', function (columns) {
            columns.forEach(function (column) {
                console.log(column.value);
                if (column.value) {
                    //TODO open the gate here
                }
                //even if the access is authorized we want to audit every swipe
                azureIoT.sendMessage({
                    id: rfidSerialNumber,
                    authorized: column.value
                });
            });
        });

        connection.execSql(request);
    }
});