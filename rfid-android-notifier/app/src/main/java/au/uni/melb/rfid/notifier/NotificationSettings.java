package au.uni.melb.rfid.notifier;

/**
 * Created by Lilian on 12/09/2017.
 */

// Connection IDs
public interface NotificationSettings {
    String SenderID = "907965440312";
    String HubName = "displayHub";
    String HubListenConnectionString =
            "Endpoint=sb://displayhub.servicebus.windows.net/;SharedAccessKeyName=DefaultListenSharedAccessSignature;SharedAccessKey=gjtqKDTnYiyoHFklFYm8Xdy0jHXktRbrH2JOCbsgJbU=";
    
}
