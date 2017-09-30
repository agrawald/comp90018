package rfid.melb.uni.au.handler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.microsoft.windowsazure.notifications.NotificationsHandler;

import rfid.melb.uni.au.activity.NotifierActivity;
import rfid.melb.uni.au.activity.R;

/**
 * {@link NotificationsHandler} to process all the notification messages received
 * Created by Dheeraj Agrawal (agrawald@student.unimelb.edu.au) on 24-Sep-17.
 */
public class NotificationHandler extends NotificationsHandler {
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context ctx, Bundle bundle) {
        final Intent intent = new Intent(ctx, NotifierActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(bundle);
        NotificationManager mNotificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        final PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);
        final String message = NotifierActivity.instance.getString(R.string.text_notification);
        final Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("AutoTAG")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(message))
                        .setSound(defaultSoundUri)
                        .setExtras(bundle)
                        .setContentText(message);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}