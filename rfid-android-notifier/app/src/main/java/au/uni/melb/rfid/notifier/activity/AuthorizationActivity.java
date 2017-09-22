package au.uni.melb.rfid.notifier.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import au.uni.melb.rfid.notifier.R;

public class AuthorizationActivity extends AppCompatActivity {
    private Button confirm;
    private Button dismiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize_notification);
        confirm = (Button) findViewById(R.id.confirmButton);
        dismiss = (Button) findViewById(R.id.dismissButton);
        confirm.setOnClickListener(new ConfirmOnClickListener());
        dismiss.setOnClickListener(new DismissOnClickListener());
    }

    private class DismissOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // print dismissal
            Context context = getApplicationContext();
            CharSequence text = "User dismissed";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.TOP, 0, 180);
            toast.show();

            //send to azure
            // ... code ...

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
            // ... code ...

            // return to main activity
            Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
