package rfid.melb.uni.au.service;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import static android.content.ContentValues.TAG;

/**
 * Created by dagrawal on 24-Sep-17.
 */

public class GooglePlaySvc {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private final Activity parent;

    public GooglePlaySvc(Activity parent) {
        this.parent = parent;
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean check() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(parent);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(parent, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported by Google Play Services.");
                Toast.makeText(parent, "This device is not supported by Google Play Services.", Toast.LENGTH_LONG);
                parent.finish();
            }
            return false;
        }
        return true;
    }

    public void register() {
        Log.i(TAG, " Registering with Notification Hubs");

        if (check()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(parent, RegistrationIntentService.class);
            parent.startService(intent);
        }
    }
}
