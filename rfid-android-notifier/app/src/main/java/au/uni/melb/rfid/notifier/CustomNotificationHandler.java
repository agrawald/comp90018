package au.uni.melb.rfid.notifier;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.microsoft.windowsazure.notifications.NotificationsHandler;

import au.uni.melb.rfid.notifier.activity.MainActivity;

/**
 * This class listens for and handles notifications.
 * Created on 13/09/2017.
 */

public class CustomNotificationHandler extends NotificationsHandler {

    public static final int NOTIFICATION_ID = 1;
    private static final String TAG = "Handler";
    Context ctx;
    private NotificationManager mNotificationManager;
    private boolean notificationAuth;
    private String notificationID;

    @Override
    public void onReceive(Context context, Bundle bundle) {
        ctx = context;
        this.notificationID = getID(ctx, bundle);
        this.notificationAuth = authNeeded(ctx, bundle);
        String nhMessage;

        if (notificationAuth) {
            nhMessage = "Authorization has been requested by " + notificationID;
        } else {
            nhMessage = notificationID + " has requested access";
        }
        sendNotification(nhMessage);

        if (MainActivity.isVisible) {
            MainActivity.mainActivity.setVariables(notificationID,notificationAuth);
            MainActivity.mainActivity.ToastNotify(nhMessage);
        }
    }

    private void sendNotification(String msg) {

        Intent intent = new Intent(ctx, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        mNotificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("AutoTAG Notification Hub")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                        .setSound(defaultSoundUri)
                        .setContentText(msg)
                        .setOngoing(true);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }

    /*
    * Method to determine if notification is authorizing type
    * Returns 'TRUE' for authorization notification
    * Returns 'FALSE' for notification only
    */
    private boolean authNeeded(Context context, Bundle bundle) {
        ctx = context;
        try {
            String type = bundle.getString("type");
            int auth = Integer.parseInt(type);
            if (auth == 1) {
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return false;
    }


    /*
    * Method to retrieve the RFID registration ID
    */
    private String getID(Context context, Bundle bundle) {
        ctx = context;

        String ID = null;

        try {
            ID = bundle.getString("id");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return ID;
    }


    public boolean getAuthNeeded(){
        return this.notificationAuth;
    }

    public String getID() {
        return this.notificationID;
    }
}