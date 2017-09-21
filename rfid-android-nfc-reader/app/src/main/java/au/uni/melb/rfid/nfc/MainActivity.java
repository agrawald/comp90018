package au.uni.melb.rfid.nfc;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private NfcAdapter nfcAdapter;
    TextView textViewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewInfo = (TextView) findViewById(R.id.info);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this,
                    "NFC NOT supported on this devices!",
                    Toast.LENGTH_LONG).show();
            finish();
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this,
                    "NFC NOT Enabled!",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            Toast.makeText(this,
                    "onResume() - ACTION_TAG_DISCOVERED",
                    Toast.LENGTH_SHORT).show();

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag == null) {
                textViewInfo.setText("tag == null");
            } else {
                int i, j, in;
                String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
                String tagInfo = tag.toString() + "\n";
                byte[] tagId = tag.getId();
                tagInfo += "length = " + tagId.length + "\n";
                tagInfo += "Tag Id: ";

                for(j = 0 ; j < tagId.length ; ++j)
                {
                    in = (int) tagId[j] & 0xff;
                    i = (in >> 4) & 0x0f;
                    tagInfo += hex[i];
                    i = in & 0x0f;
                    tagInfo += hex[i];
                }
                textViewInfo.setText(tagInfo);
            }
        } else {
            Toast.makeText(this,
                    "onResume() : " + action,
                    Toast.LENGTH_SHORT).show();
        }

    }
}
