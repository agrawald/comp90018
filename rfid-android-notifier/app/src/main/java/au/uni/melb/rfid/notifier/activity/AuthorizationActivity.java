package au.uni.melb.rfid.notifier.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import au.uni.melb.rfid.notifier.R;
import au.uni.melb.rfid.notifier.model.AutoTag;

public class AuthorizationActivity extends AppCompatActivity {
    private Button confirm;
    private Button deny;

    private static final String TAG = "AuthorizeActivity";
    String resultString = null;
    private MobileServiceClient mobileServiceClient;
    private MobileServiceTable<AutoTag> mobileServiceTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize_notification);
        confirm = (Button) findViewById(R.id.confirmButton);
        deny = (Button) findViewById(R.id.denyButton);
        confirm.setOnClickListener(new ConfirmOnClickListener());
        deny.setOnClickListener(new DenyOnClickListener());

        String ID = getIntent().getStringExtra("id");
        Log.i(TAG, "Got user ID" + ID);
        TextView userID = (TextView) findViewById(R.id.authorizeUserID);
        userID.setText("User ID: " + ID);

        try {
            mobileServiceClient = new MobileServiceClient(
                    "https://nfcconnection.azurewebsites.net",
                    this);
            mobileServiceTable = mobileServiceClient.getTable(AutoTag.class);
        } catch (Exception e) {
            Log.e(TAG, resultString = "Failed to initiate mobileServiceClient", e);
        }
    }

    private class DenyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // print denial
            Context context = getApplicationContext();
            CharSequence text = "User denied";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.TOP, 0, 180);
            toast.show();

            //send to azure
            String ID = getIntent().getStringExtra("id");
            addItem(ID, false);

            // return to main activity
            Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private class ConfirmOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // print confirmation
            Context context = getApplicationContext();
            CharSequence text = "User authorized";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.TOP, 0, 180);
            toast.show();

            //send to azure
            String ID = getIntent().getStringExtra("id");
            addItem(ID, true);

            // return to main activity
            Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void addItem(String ID, boolean authorization) {
        if (mobileServiceClient == null) {
            return;
        }

        AutoTag autoTag = new AutoTag();
        autoTag.setAuthorized(authorization);
        autoTag.setId(ID);
        mobileServiceTable.insert(autoTag);

        String auth;
        if (authorization) {
            auth = "authorized";
        } else {
            auth = "denied";
        }
        Log.i(TAG, ID + " was added to the table as " + auth);
    }
}
