package comp90018.autotag;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

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
                System.out.print("User authorized");
                Intent intent = new Intent(authorizeNotification.this, MainActivity.class);
                startActivity(intent);

                // send to azure
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.print("User dismissed");
                Intent intent = new Intent(authorizeNotification.this, MainActivity.class);
                startActivity(intent);

                // send to azure
            }
        });
    }
}
