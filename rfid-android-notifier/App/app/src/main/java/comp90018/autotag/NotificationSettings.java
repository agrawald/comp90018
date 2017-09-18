package comp90018.autotag;

/**
 * Created by Lilian on 12/09/2017.
 */

// Connection IDs
public class NotificationSettings {
    public static String SenderID = "51158287557";
    public static String HubName = "AutoTAG";
    public static String HubListenConnectionString =
            "Endpoint=sb://autotag.servicebus.windows.net/;SharedAccessKeyName=DefaultListenSharedAccessSignature;SharedAccessKey=8WyGVu9swcmkllMZj5yOEaYcGUhrUrKyY4ozzj+/s7U=";


    public static String authSenderID = "796149648033";
    public static String authHubName = "authorizationHub";
    public static String authHubListenConnectionString =
            "Endpoint=sb://authorizationhub.servicebus.windows.net/;SharedAccessKeyName=DefaultListenSharedAccessSignature;SharedAccessKey=HRajUq1Wck/Il+LUCGxaF7Q9nCxG9N5lBbS3XzKFZ3Y=";

    public static String dispSenderID = "907965440312";
    public static String dispHubName = "displayHub";
    public static String dispHubListenConnectionString =
            "Endpoint=sb://displayhub.servicebus.windows.net/;SharedAccessKeyName=DefaultListenSharedAccessSignature;SharedAccessKey=gjtqKDTnYiyoHFklFYm8Xdy0jHXktRbrH2JOCbsgJbU=";


}
