package rfid.melb.uni.au.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.concurrent.TimeUnit;

import rfid.melb.uni.au.common.AzureServiceAdapter;
import rfid.melb.uni.au.common.CloudConfig;
import rfid.melb.uni.au.model.Payload;

/**
 * Data access object to delete a record from rfidAuth table
 * Created by Dheeraj Agrawal (agrawald@student.unimelb.edu.au) on 24-Sep-17.
 */
public class DeleteRfidDao extends AsyncTask<String, Void, Void> {
    private final static String TAG = DeleteRfidDao.class.getSimpleName();
    private final MobileServiceTable<Payload> rfidTable;

    public DeleteRfidDao() {
        this.rfidTable = AzureServiceAdapter.getInstance().getTable(CloudConfig.RFID_AUTH_RESOURCE);
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
