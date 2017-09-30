package au.uni.melb.rfid.nfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.net.MalformedURLException;

import au.uni.melb.rfid.nfc.common.AzureServiceAdapter;
import au.uni.melb.rfid.nfc.common.CloudConfig;
import au.uni.melb.rfid.nfc.dao.InsertRfidDao;
import au.uni.melb.rfid.nfc.model.Payload;

/**
 * Main activity to start the application
 */
public class AutoTagActivity extends AppCompatActivity {
    private final static String TAG = AutoTagActivity.class.getSimpleName();
    public static AutoTagActivity instance;
    private TextView tvTagId;
    private ToggleButton tbAuthorized;
    private Button btnAdd;
    private InsertRfidDao insertRfidDao;
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private PendingIntent pendingIntent;
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        try {
            AzureServiceAdapter.init(this.getApplicationContext());
        } catch (MalformedURLException e) {
            Log.e(TAG, "Unable to register with the azure backend service", e);
            this.finish();
        }
        insertRfidDao = new InsertRfidDao();

        this.nfcAdapter = NfcAdapter.getDefaultAdapter(AutoTagActivity.instance);
        if (nfcAdapter == null) {
            Toast.makeText(AutoTagActivity.instance,
                    "NFC NOT supported on this devices!",
                    Toast.LENGTH_LONG).show();
            AutoTagActivity.instance.finish();
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(AutoTagActivity.instance,
                    "NFC NOT Enabled!",
                    Toast.LENGTH_LONG).show();
            AutoTagActivity.instance.finish();
        }
        setContentView(R.layout.activity_main);
        tvTagId = (TextView) findViewById(R.id.tvTagId);
        tbAuthorized = (ToggleButton) findViewById(R.id.tbAuthorized);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("*/*");    /* Handles all MIME based dispatches.
                                       You should specify only the ones that you need. */
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        intentFiltersArray = new IntentFilter[]{ndef,};
        techListsArray = new String[][]{new String[]{NfcF.class.getName()}};

    }

    public void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
    }

    public void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
    }

    public void onNewIntent(Intent intent) {
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //do something with tagFromIntent
        if (tagFromIntent != null) {
            String tagIdInfo = "";
            int i, j, in;
            final byte[] tagId = tagFromIntent.getId();
            for (j = 0; j < tagId.length; ++j) {
                in = (int) tagId[j] & 0xff;
                i = (in >> 4) & 0x0f;
                tagIdInfo += CloudConfig.HEX[i];
                i = in & 0x0f;
                tagIdInfo += CloudConfig.HEX[i];
            }

            tvTagId.setText(tagIdInfo);
            btnAdd.setEnabled(true);
        }
    }

    /**
     * Add a new item
     *
     * @param view The view that originated the call
     */
    public void addItem(View view) throws Exception {
        // Create a new item
        final Payload payload = new Payload(tvTagId.getText().toString(), tbAuthorized.isChecked());
        insertRfidDao.execute(payload).get();
        tvTagId.setText("");
        btnAdd.setEnabled(false);
    }
}
