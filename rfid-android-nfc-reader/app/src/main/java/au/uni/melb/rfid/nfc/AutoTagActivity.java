package au.uni.melb.rfid.nfc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.microsoft.windowsazure.mobileservices.MobileServiceException;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import au.uni.melb.rfid.nfc.common.AzureServiceAdapter;
import au.uni.melb.rfid.nfc.dao.RfidAuthDao;
import au.uni.melb.rfid.nfc.model.Payload;
import au.uni.melb.rfid.nfc.service.NfcSensorSvc;

public class AutoTagActivity extends AppCompatActivity {
    private final static String TAG = AutoTagActivity.class.getSimpleName();
    public static AutoTagActivity instance;
    private TextView tvTagId;
    private ToggleButton tbAuthorized;
    private Button btnAdd;
    private RfidAuthDao rfidAuthDao;
    private NfcSensorSvc nfcSensorSvc;
    private ProgressBar spinner;

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
        rfidAuthDao = new RfidAuthDao();
        nfcSensorSvc = new NfcSensorSvc();

        setContentView(R.layout.activity_main);
        tvTagId = (TextView) findViewById(R.id.tvTagId);
        tbAuthorized = (ToggleButton) findViewById(R.id.tbAuthorized);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final String tagId = nfcSensorSvc.read();
        if (tagId != null && tagId.trim().length() > 0) {
            tvTagId.setText(tagId);
            btnAdd.setEnabled(true);
        }
    }

    /**
     * Add a new item
     *
     * @param view The view that originated the call
     */
    public void addItem(View view) throws InterruptedException, ExecutionException, MobileServiceException {
        spinner.setVisibility(View.VISIBLE);
        // Create a new item
        rfidAuthDao.saveOrUpdate(new Payload(tvTagId.getText().toString(), tbAuthorized.isChecked()));
        spinner.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Tag is uploaded to the authorization store.", Toast.LENGTH_LONG).show();
        tvTagId.setText("");
        btnAdd.setEnabled(false);
    }
}
