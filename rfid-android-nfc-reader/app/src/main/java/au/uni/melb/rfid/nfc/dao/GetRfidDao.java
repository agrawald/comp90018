package au.uni.melb.rfid.nfc.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.List;
import java.util.concurrent.TimeUnit;

import au.uni.melb.rfid.nfc.common.AzureServiceAdapter;
import au.uni.melb.rfid.nfc.model.Payload;

/**
 * Created by dagrawal on 30-Sep-17.
 */

public class GetRfidDao extends AsyncTask<String, Void, Payload> {
    private final static String TAG = GetRfidDao.class.getSimpleName();
    private final MobileServiceTable<Payload> rfidTable;

    public GetRfidDao() {
        this.rfidTable = AzureServiceAdapter.getInstance().getTable("Rfid");
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
