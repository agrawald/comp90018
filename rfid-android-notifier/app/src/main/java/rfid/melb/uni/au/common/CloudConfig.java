package rfid.melb.uni.au.common;

/**
 * Interface to have all the cloud configuration
 * Created by Dheeraj Agrawal (agrawald@student.unimelb.edu.au) on 24-Sep-17.
 */
public interface CloudConfig {
    String SENDER_ID = "907965440312";
    String HUB_NAME = "displayHub";
    String HUB_CONN_STR = "Endpoint=sb://displayhub.servicebus.windows.net/;SharedAccessKeyName=DefaultListenSharedAccessSignature;SharedAccessKey=gjtqKDTnYiyoHFklFYm8Xdy0jHXktRbrH2JOCbsgJbU=";
    String MOBILE_BACKEND_URL = "http://rfidbackendandroid.azurewebsites.net";
    String RFID_AUTH_RESOURCE = "Rfid";
    String VALUES_RESOURCE = "values";
    String METHOD_POST = "POST";

}
