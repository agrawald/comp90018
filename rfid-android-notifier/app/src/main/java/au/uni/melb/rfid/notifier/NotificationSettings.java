package au.uni.melb.rfid.notifier;

/**
 * Created by Lilian on 12/09/2017.
 */

// Connection IDs
public interface NotificationSettings {
    String SenderID = "51158287557";
    String HubName = "AutoTAG";
    String HubListenConnectionString =
            "Endpoint=sb://autotag.servicebus.windows.net/;SharedAccessKeyName=DefaultListenSharedAccessSignature;SharedAccessKey=8WyGVu9swcmkllMZj5yOEaYcGUhrUrKyY4ozzj+/s7U=";
}
