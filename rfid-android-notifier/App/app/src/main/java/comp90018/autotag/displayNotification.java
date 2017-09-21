package comp90018.autotag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class displayNotification extends AppCompatActivity {

    private static final String TAG = "notifyActivity";

    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Opened display notification activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notification);
        ok = (Button) findViewById(R.id.okButton);

        String ID = getIntent().getStringExtra("id");
        Log.i(TAG, "Got user ID " + ID);
        TextView userID = (TextView) findViewById(R.id.displayUserID);
        userID.setText("User ID: " + ID);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // return to main activity
                Intent intent = new Intent(displayNotification.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
