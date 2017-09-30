package au.uni.melb.rfid.nfc.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.concurrent.TimeUnit;

import au.uni.melb.rfid.nfc.common.AzureServiceAdapter;
import au.uni.melb.rfid.nfc.model.Payload;

/**
 * Created by dagrawal on 30-Sep-17.
 */

public class UpdateRfidDao extends AsyncTask<Payload, Void, Payload> {
    private final static String TAG = UpdateRfidDao.class.getSimpleName();
    private final MobileServiceTable<Payload> rfidTable;

    public UpdateRfidDao() {
        this.rfidTable = AzureServiceAdapter.getInstance().getTable("Rfid");
    }

    @Override
    protected Payload doInBackground(Payload... params) {
        try {
            return rfidTable.update(params[0]).get(20, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "Unable to find any record for id: " + params[0], e);
        }
        return null;
    }
}
