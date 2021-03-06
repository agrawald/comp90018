package rfid.melb.uni.au.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.microsoft.windowsazure.messaging.NotificationHub;

import rfid.melb.uni.au.common.CloudConfig;

/**
 * Service class to register with Google notification hub.
 */
public class RegistrationIntentSvc extends IntentService {

    private static final String TAG = "RegIntentService";

    public RegistrationIntentSvc() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String regID = null;

        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(CloudConfig.SENDER_ID,
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            Log.i(TAG, "Got GCM Registration Token: " + token);

            // Storing the registration id that indicates whether the generated token has been
            // sent to your server. If it is not stored, send the token to your server,
            // otherwise your server should have already received the token.
            if ((regID = sharedPreferences.getString("registrationID", null)) == null) {
                NotificationHub hub = new NotificationHub(CloudConfig.HUB_NAME,
                        CloudConfig.HUB_CONN_STR, this);
                Log.i(TAG, "Attempting to register with NH using token : " + token);

                regID = hub.register(token).getRegistrationId();
                Log.i(TAG, "Registered Successfully - RegId : " + regID);
                sharedPreferences.edit().putString("registrationID", regID).apply();
            } else {
                Log.i(TAG, "Previously Registered Successfully - RegId : " + regID);
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to complete token refresh", e);
        }
    }
}