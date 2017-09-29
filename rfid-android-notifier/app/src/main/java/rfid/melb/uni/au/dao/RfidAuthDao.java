package rfid.melb.uni.au.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import rfid.melb.uni.au.common.AzureServiceAdapter;
import rfid.melb.uni.au.model.Payload;

/**
 * Created by dagrawal on 29-Sep-17.
 */

public class RfidAuthDao {
    private static final String TAG = RfidAuthDao.class.getSimpleName();
    private static final String TABLE = "Rfid";
    private final MobileServiceTable<Payload> rfidTable;

    public RfidAuthDao() {
        this.rfidTable = AzureServiceAdapter.getInstance().getTable(TABLE);
    }

    public Payload findOne(final String id) throws MobileServiceException, ExecutionException, InterruptedException {
        final AsyncTask<String, Void, MobileServiceList<Payload>> asyncTask = new AsyncTask<String, Void, MobileServiceList<Payload>>() {
            @Override
            protected MobileServiceList<Payload> doInBackground(String... params) {
                try {
                    return rfidTable.where().field("Id").eq(params[0]).execute().get(1000, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    Log.e(TAG, "Unable to get data for id: " + id, e);
                }
                return null;
            }
        }.execute(id);
        final MobileServiceList<Payload> payloads = asyncTask.get();
        return payloads != null && payloads.size() > 0 ? payloads.get(0) : null;
    }

    public Payload saveOrUpdate(final Payload payload) throws ExecutionException, InterruptedException, MobileServiceException {
        final Payload existing = findOne(payload.getId());
        AsyncTask<Void, Void, Payload> insertOrUpdateAsyncTask;
        if (existing == null) {
            insertOrUpdateAsyncTask = new AsyncTask<Void, Void, Payload>() {
                @Override
                protected Payload doInBackground(Void... params) {
                    try {
                        return rfidTable.insert(payload).get(1000, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        Log.e(TAG, "Unable to insert the rfid payload: " + payload, e);
                    }
                    return null;
                }
            }.execute();
        } else {
            insertOrUpdateAsyncTask = new AsyncTask<Void, Void, Payload>() {
                @Override
                protected Payload doInBackground(Void... params) {
                    try {
                        return rfidTable.update(payload).get(1000, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        Log.e(TAG, "Unable to update the rfid payload: " + payload, e);
                    }
                    return null;
                }
            }.execute();
        }

        return insertOrUpdateAsyncTask.get();
    }
}
