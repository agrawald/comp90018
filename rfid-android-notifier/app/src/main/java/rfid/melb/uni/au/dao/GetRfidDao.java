package rfid.melb.uni.au.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rfid.melb.uni.au.common.AzureServiceAdapter;
import rfid.melb.uni.au.common.CloudConfig;
import rfid.melb.uni.au.model.Payload;

/**
 * Data access object to get a record from rfidAuth table
 * Created by Dheeraj Agrawal (agrawald@student.unimelb.edu.au) on 24-Sep-17.
 */
public class GetRfidDao extends AsyncTask<String, Void, Payload> {
    private final static String TAG = GetRfidDao.class.getSimpleName();
    private final MobileServiceTable<Payload> rfidTable;

    public GetRfidDao() {
        this.rfidTable = AzureServiceAdapter.getInstance().getTable(CloudConfig.RFID_AUTH_RESOURCE);
    }

    @Override
    protected Payload doInBackground(String... params) {
        List<Payload> payloads = null;
        try {
            payloads = rfidTable.where()
                    .field("Id")
                    .eq(params[0])
                    .execute()
                    .get(20, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.e(TAG, "Unable to find any record for id: " + params[0], e);
        }
        return payloads != null && payloads.size() > 0 ? payloads.get(0) : null;
    }
}
