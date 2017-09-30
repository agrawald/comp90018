package au.uni.melb.rfid.nfc.service;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.widget.Toast;

import au.uni.melb.rfid.nfc.AutoTagActivity;

/**
 * Created by dagrawal on 29-Sep-17.
 */

public class NfcSensorSvc {
    private final NfcAdapter nfcAdapter;

    public NfcSensorSvc() {
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
    }

    public String read() {
        final Intent intent = AutoTagActivity.instance.getIntent();
        String tagIdInfo = "";

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Toast.makeText(AutoTagActivity.instance,
                    "onResume() - ACTION_TAG_DISCOVERED",
                    Toast.LENGTH_SHORT).show();

            final Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag == null) {
                tagIdInfo = "tag == null";
            } else {
                int i, j, in;
                String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
                final byte[] tagId = tag.getId();

                for (j = 0; j < tagId.length; ++j) {
                    in = (int) tagId[j] & 0xff;
                    i = (in >> 4) & 0x0f;
                    tagIdInfo += hex[i];
                    i = in & 0x0f;
                    tagIdInfo += hex[i];
                }
            }
        } else {
            Toast.makeText(AutoTagActivity.instance,
                    "onResume() : " + intent.getAction(),
                    Toast.LENGTH_SHORT).show();
        }

        return tagIdInfo;
    }
}
