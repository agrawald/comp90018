package rfid.melb.uni.au.service;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by dagrawal on 24-Sep-17.
 */

public class InstanceIdSvc extends InstanceIDListenerService {
    private static final String TAG = InstanceIdSvc.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        Log.i(TAG, "Refreshing GCM Registration Token");
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}