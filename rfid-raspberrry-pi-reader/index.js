'use strict';

const Request = require('tedious').Request;
const Connection = require('tedious').Connection;
const AzureIoT = require('./js/azureIoT');
const rc522 = require("rc522");
const config = require('./config.json');

const azureIoT = new AzureIoT(config);
console.log("Starting the RFID listener...");
rc522(function (rfidSerialNumber) {
// let rfidSerialNumber = "sdafa";
    console.log(rfidSerialNumber);
    let connection = new Connection(config.database);
    connection.on('connect', function (err) {
        // If no error, then good to go...
        if (!err) {
            executeStatement();
        }
    });

    function executeStatement() {
        //create the db request to get the authorization
        let request = new Request("select authorized from rfidAuth where id='" + rfidSerialNumber + "'",
            function (err, rowCount) {
                if (err) {
                    console.log(err);
                } else {
                    console.log(rowCount + ' rows');
                    let type = rowCount === 0 ? 1 : 0;
                    //even if the access is authorized we want to audit every swipe
                    azureIoT.sendMessage({
                        id: rfidSerialNumber,
                        type: type
                    });
                }
            });
        //lets register a method to get the rows
        request.on('row', function (columns) {
            columns.forEach(function (column) {
                console.log(column.value);
                if (column.value) {
                    //TODO open the gate here
                }

            });
        });

        connection.execSql(request);
    }
});
