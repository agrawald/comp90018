'use strict';

/**
 * Simulated sensor class which mock the sensor reading
 * @constructor
 */
function Simulated(/* options */) {
    // nothing todo
}

/**
 * Function to read the mock sensor data which include temperature, humidity, location and may more.
 * @returns {{temperature, humidity, location: {lat, lng}}}
 */
Simulated.prototype.read = function () {
    return {
        temperature: random(20, 30),
        humidity: random(60, 80),
        location: {
            lat: random(-90, 90),
            lng: random(-180, 180)
        }
    };
};

/**
 * Function to generate random numbers between in the range provided
 * @param min Floor
 * @param max Ceiling
 * @returns {*} A number in between min and max
 */
function random(min, max) {
    return Math.random() * (max - min) + min;
}

module.exports = Simulated;
