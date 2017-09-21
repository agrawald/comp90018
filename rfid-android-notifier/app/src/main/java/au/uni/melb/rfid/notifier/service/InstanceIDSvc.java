package au.uni.melb.rfid.notifier.service;

import android.content.Intent;
import android.util.Log;
import com.google.android.gms.iid.InstanceIDListenerService;


/**
 * Created by Lilian on 12/09/2017.
 */

public class InstanceIDSvc extends InstanceIDListenerService {
    private static final String TAG = "InstanceIDSvc";

    @Override
    public void onTokenRefresh() {
        Log.i(TAG, "Refreshing GCM Registration Token");
        Intent intent = new Intent(this, RegistrationSvc.class);
        startService(intent);
    }
}

