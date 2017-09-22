package au.uni.melb.rfid.notifier.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import au.uni.melb.rfid.notifier.R;

public class DisplayActivity extends AppCompatActivity {
    private Button ok;
    private static final String TAG = "DisplayActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notification);
        ok = (Button) findViewById(R.id.okButton);
        ok.setOnClickListener(new OkOnClickListener());

        String ID = getIntent().getStringExtra("id");
        Log.i(TAG, "Got user ID " + ID);
        TextView userID = (TextView) findViewById(R.id.displayUserID);
        userID.setText("User ID: " + ID);
    }

    private class OkOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // return to main activity
            Intent intent = new Intent(DisplayActivity.this, MainActivity.class);
            startActivity(intent);

        }
    }
}
