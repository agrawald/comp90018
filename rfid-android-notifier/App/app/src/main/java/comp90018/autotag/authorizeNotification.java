package comp90018.autotag;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class authorizeNotification extends AppCompatActivity {

    Button confirm;
    Button dismiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize_notification);
        confirm = (Button)findViewById(R.id.confirmButton);
        dismiss = (Button)findViewById(R.id.dismissButton);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // print confirmation
                Context context = getApplicationContext();
                CharSequence text = "User authorized";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //send to azure
                // ... code ...

                // return to main activity
                Intent intent = new Intent(authorizeNotification.this, MainActivity.class);
                startActivity(intent);

            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // print dismissal
                Context context = getApplicationContext();
                CharSequence text = "User dismissed";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //send to azure
                // ... code ...

                // return to main activity
                Intent intent = new Intent(authorizeNotification.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
