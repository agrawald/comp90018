package au.uni.melb.rfid.nfc;

import android.app.AlertDialog;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

public class AutoTagActivity extends AppCompatActivity {
    TextView tvIdInfo;
    TextView tvTextInfo;
    /**
     * Transfer NCF data into database
     */
    private MobileServiceClient autoTagNFCClient;
    private MobileServiceTable<AutoTag> autoTagTable;
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTextInfo = (TextView) findViewById(R.id.info);
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

        try {
            autoTagNFCClient = new MobileServiceClient(
                    "https://nfcconnection.azurewebsites.net",
                    this);
            autoTagTable = autoTagNFCClient.getTable("AutoTag", AutoTag.class);
        } catch (Exception e) {
            createAndShowDialog(e, "Error");
        }
        tvIdInfo = (TextView) findViewById(R.id.tvIdToDo);
        tvTextInfo = (TextView) findViewById(R.id.tvTextToDo);
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
                tvIdInfo.setText("tag == null");
            } else {
                int i, j, in;
                String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
                String tagIdInfo = "";
                String tagTextInfo = "";
                byte[] tagId = tag.getId();
                tagTextInfo += tagId.length;

                for (j = 0; j < tagId.length; ++j) {
                    in = (int) tagId[j] & 0xff;
                    i = (in >> 4) & 0x0f;
                    tagIdInfo += hex[i];
                    i = in & 0x0f;
                    tagIdInfo += hex[i];
                }
                tvIdInfo.setText(tagIdInfo);
                tvTextInfo.setText(tagTextInfo);
            }
        } else {
            Toast.makeText(this,
                    "onResume() : " + action,
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Add a new item
     *
     * @param view The view that originated the call
     */
    public void addItem(View view) {
        if (autoTagNFCClient == null) {
            return;
        }

        // Create a new item
        AutoTag nfcTag = new AutoTag();
        nfcTag.setAuthorized(false);
        nfcTag.setId(tvIdInfo.getText().toString());
        try {
            autoTagTable.insert(nfcTag).get();
        } catch (Exception e) {
            createAndShowDialog(e, "Error");
        }
    }

    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if (exception.getCause() != null) {
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }

    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }

    /**
     * Creates a dialog and shows it
     *
     * @param exception The exception to show in the dialog
     * @param title     The dialog title
     */
    private void createAndShowDialogFromTask(final Exception exception, String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                createAndShowDialog(exception, "Error");
            }
        });
    }
}
