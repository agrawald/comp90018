package au.uni.melb.rfid.nfc.common;

/**
 * Interface to store all the static cloud configuration settings
 * Created by dagrawal on 01-Oct-17.
 */

public interface CloudConfig {
    String MOBILE_BACKEND_URL = "http://rfidbackendandroid.azurewebsites.net";
    String RFIDAUTH_RESOURCE = "Rfid";
    String[] HEX = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
}
