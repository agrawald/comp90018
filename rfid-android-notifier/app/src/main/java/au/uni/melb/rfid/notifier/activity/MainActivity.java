package au.uni.melb.rfid.notifier.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.microsoft.windowsazure.notifications.NotificationsManager;

import au.uni.melb.rfid.notifier.CustomNotificationHandler;
import au.uni.melb.rfid.notifier.NotificationSettings;
import au.uni.melb.rfid.notifier.R;
import au.uni.melb.rfid.notifier.service.RegistrationSvc;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static MainActivity mainActivity;
    public static Boolean isVisible = false;
    TextView notification;
    String resultString = null;
    private GoogleCloudMessaging gcm;
    private String ID = null;
    private boolean authNeeded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;
        NotificationsManager.handleNotifications(this, NotificationSettings.SenderID, CustomNotificationHandler.class);
        registerWithNotificationHubs();

        Log.d(TAG, "Welcome to AutoTAG");

        notification = (TextView) findViewById(R.id.notificationText);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ID != null) {
                    if (authNeeded) {
                        Intent intent = new Intent(MainActivity.this, AuthorizationActivity.class);
                        intent.putExtra("id", ID);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                        intent.putExtra("id", ID);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();

            } else {
                Log.i(TAG, "This device is not supported by Google Play Services.");
                ToastNotify("This device is not supported by Google Play Services.");
                finish();
            }
            return false;
        }
        return true;
    }

    public void registerWithNotificationHubs() {
        Log.i(TAG, "Registering with Notification Hubs");

        if (checkPlayServices()) {
            Intent intent = new Intent(this, RegistrationSvc.class);
            startService(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        isVisible = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isVisible = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isVisible = false;
    }

    public void ToastNotify(final String notificationMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(MainActivity.this, notificationMessage, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 180);
                toast.show();
                TextView notification = (TextView) findViewById(R.id.notificationText);
                notification.setText(notificationMessage);
            }
        });
    }

    public void setVariables(final String ID, final boolean authNeeded) {
        this.ID = ID;
        this.authNeeded = authNeeded;
    }
}