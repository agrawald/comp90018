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
public class DeleteRfidDao extends AsyncTask<String, Void, Void> {
    private final static String TAG = DeleteRfidDao.class.getSimpleName();
    private final MobileServiceTable<Payload> rfidTable;

    public DeleteRfidDao() {
        this.rfidTable = AzureServiceAdapter.getInstance().getTable("Rfid");
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            rfidTable.delete(params[0]).get(20, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "Unable to delete the record for id: " + params[0], e);
        }

        return null;
    }
}
