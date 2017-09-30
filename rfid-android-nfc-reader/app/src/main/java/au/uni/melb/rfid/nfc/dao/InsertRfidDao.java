package au.uni.melb.rfid.nfc.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import au.uni.melb.rfid.nfc.common.AzureServiceAdapter;
import au.uni.melb.rfid.nfc.model.Payload;

/**
 * Created by dagrawal on 30-Sep-17.
 */

public class InsertRfidDao extends AsyncTask<Payload, Void, Payload> {
    private final static String TAG = InsertRfidDao.class.getSimpleName();
    private final MobileServiceTable<Payload> rfidTable;

    public InsertRfidDao() {
        this.rfidTable = AzureServiceAdapter.getInstance().getTable("Rfid");
    }

    @Override
    protected Payload doInBackground(final Payload... params) {
        try {
            AzureServiceAdapter.getInstance()
                    .getTable("Rfid")
                    .delete(params[0]).addListener(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(TAG, "Inserting the record now: " + params[0]);
                        AzureServiceAdapter.getInstance()
                                .getTable("Rfid")
                                .insert(params[0]).get(20, TimeUnit.SECONDS);
                    } catch (Exception e) {
                        Log.e(TAG, "Unable to insert any record for id: " + params[0], e);
                    }
                }
            }, Executors.newSingleThreadExecutor());
        } catch (Exception e) {
            Log.e(TAG, "Unable to find any record for id: " + params[0], e);
        }
        return null;
    }
}
