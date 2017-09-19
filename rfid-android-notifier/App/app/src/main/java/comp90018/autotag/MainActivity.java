package comp90018.autotag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.*;
import com.microsoft.windowsazure.notifications.NotificationsManager;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static MainActivity mainActivity;
    public static Boolean isVisible = false;
    private GoogleCloudMessaging gcm;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private String ID = null;
    private boolean authNeeded = false;

    TextView notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;
        NotificationsManager.handleNotifications(this, NotificationSettings.dispSenderID, MyHandler.class);
        registerWithNotificationHubs();

        Log.d(TAG, "Welcome to AutoTAG");

        notification = (TextView) findViewById(R.id.notificationText);

        notification.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (ID != null){
                    if (authNeeded) {
                        Intent intent = new Intent(MainActivity.this, authorizeNotification.class);
                        intent.putExtra("id", ID);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, displayNotification.class);
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
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        isVisible = true;
    }

    @Override
    protected  void onPause() {
        super.onPause();
        isVisible = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
    }

    @Override
    protected  void onStop() {
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

    public void setVariables(final String ID, final boolean authNeeded){
        this.ID = ID;
        this.authNeeded = authNeeded;
    }

}
