package au.uni.melb.rfid.notifier.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.microsoft.windowsazure.messaging.NotificationHub;

import au.uni.melb.rfid.notifier.NotificationSettings;
import au.uni.melb.rfid.notifier.activity.MainActivity;

/**
 * Created by Lilian on 13/09/2017.
 */

public class RegistrationSvc extends IntentService {

    private static final String TAG = "RegistrationSvc";

    private NotificationHub hub;

    public RegistrationSvc() {
        super(TAG);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String resultString;
        String regID;

        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(NotificationSettings.SenderID,
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            Log.i(TAG, "Got GCM Registration Token: " + token);

            if ((regID = sharedPreferences.getString("registrationID", null)) == null) {
                NotificationHub hub = new NotificationHub(NotificationSettings.HubName,
                        NotificationSettings.HubListenConnectionString, this);
                Log.i(TAG, "Attempting to register with NH using token: " + token);
                regID = hub.register(token).getRegistrationId();

                resultString = "Registered Successfully - RegID: " + regID;
                Log.i(TAG, resultString);
                sharedPreferences.edit().putString("registrationID", regID).apply();
            } else {
                resultString = "Previously Registered Successfully - RegID: " + regID;
            }
        } catch (Exception e) {
            Log.e(TAG, resultString = "Failed to complete token refresh", e);
        }

        if (MainActivity.isVisible) {
            MainActivity.mainActivity.ToastNotify(resultString);
        }
    }
}
