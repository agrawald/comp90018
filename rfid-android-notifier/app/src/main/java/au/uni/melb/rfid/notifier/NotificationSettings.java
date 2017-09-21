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

    String authSenderID = "796149648033";
    String authHubName = "authorizationHub";
    String authHubListenConnectionString =
            "Endpoint=sb://authorizationhub.servicebus.windows.net/;SharedAccessKeyName=DefaultListenSharedAccessSignature;SharedAccessKey=HRajUq1Wck/Il+LUCGxaF7Q9nCxG9N5lBbS3XzKFZ3Y=";

    String dispSenderID = "907965440312";
    String dispHubName = "displayHub";
    String dispHubListenConnectionString =
            "Endpoint=sb://displayhub.servicebus.windows.net/;SharedAccessKeyName=DefaultListenSharedAccessSignature;SharedAccessKey=gjtqKDTnYiyoHFklFYm8Xdy0jHXktRbrH2JOCbsgJbU=";


}
