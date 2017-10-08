'use strict';

/**
 * Class to do all the registeration and initialization work with Azure
 * @type {Client}
 */

const AzureIoTClient = require('azure-iot-device').Client;
const AzureIoTMessage = require('azure-iot-device').Message;
const AzureIoTMqtt = require('azure-iot-device-mqtt').Mqtt;
const Simulated = require('./sensor/simulated');

let client;
const simulatedSensor = new Simulated();

function AzureIoT(config) {
    this.config = config;
    console.log("Initializing the Azure IoT client...");
    this.initClient();
}

/**
 * Function to send message to IoT Hub
 * @param content
 */
AzureIoT.prototype.sendMessage = function (content) {
    content.deviceId = this.getDeviceId();
    content.data = simulatedSensor.read();
    let message = new AzureIoTMessage(JSON.stringify(content));
    client.sendEvent(message, (err) => {
        if (err) {
            console.error('Failed to send message to Azure IoT Hub: ' + err);
        }
    })
};

/**
 * Function ti do initialization work
 */
AzureIoT.prototype.initClient = function () {
    // fromConnectionString must specify a transport constructor, coming from any transport package.
    client = AzureIoTClient.fromConnectionString(this.getConnectionString(), AzureIoTMqtt);

    client.open((err) => {
        if (err) {
            console.error('[IoT hub Client] Connect error: ', err);
        }

        // set C2D and device method callback
        client.onDeviceMethod('start', onStart);
        client.onDeviceMethod('stop', onStop);
        client.on('message', receiveMessageCallback);
    })
};

/**
 * Function to get the connection string for IoT Hub
 * @returns {*}
 */
AzureIoT.prototype.getConnectionString = function () {
    return this.config.azureIoTHubDeviceConnectionString;
};

/**
 * Function to get the device Id
 * @returns {*}
 */
AzureIoT.prototype.getDeviceId = function () {
    return this.config.deviceId;
};

/**
 * Function which actually send a message to  IoT Hub when device is started
 * @param request
 * @param response
 */
function onStart(request, response) {
    console.log('Try to invoke method start(' + request.payload || '' + ')');
    response.send(200, 'Successully start sending message to cloud', function (err) {
        if (err) {
            console.error('[IoT hub Client] Failed sending a method response:\n' + err.message, err);
        }
    });
}

/**
 * Function notifies IoT Hub that device has stopped
 * @param request
 * @param response
 */
function onStop(request, response) {
    console.log('Try to invoke method stop(' + request.payload || '' + ')');

    response.send(200, 'Successfully stop sending message to cloud', function (err) {
        if (err) {
            console.error('[IoT hub Client] Failed sending a method response:\n' + err.message, err);
        }
    });
}

/**
 * Function to recieve all the cloud 2 device methods.
 * @param msg
 */
function receiveMessageCallback(msg) {
    let message = JSON.parse(msg.getData());
    if (message.Authorized) {
        console.log("The Tag (" + message.Id + ") is authorized, opening the gate.")
    } else {
        console.log("The Tag (" + message.Id + ") is not-authorized.")
    }
    client.complete(msg, () => {
        console.log("---------------------------------")
    })
}

module.exports = AzureIoT;
