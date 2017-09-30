package rfid.melb.uni.au.dao;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import rfid.melb.uni.au.activity.NotifierActivity;
import rfid.melb.uni.au.common.AzureServiceAdapter;
import rfid.melb.uni.au.common.CloudConfig;
import rfid.melb.uni.au.model.Payload;

/**
 * Data access object to send notification to IOT hub
 * Created by Dheeraj Agrawal (agrawald@student.unimelb.edu.au) on 24-Sep-17.
 */
public class NotificationDao extends AsyncTask<Payload, Void, Void> {
    private final static String TAG = NotificationDao.class.getSimpleName();
    private final MobileServiceClient mobileServiceClient;

    public NotificationDao() {
        this.mobileServiceClient = AzureServiceAdapter.getInstance().getMobileServiceClient();
    }

    @Override
    protected Void doInBackground(final Payload... params) {
        ListenableFuture<String> result = mobileServiceClient.invokeApi(CloudConfig.VALUES_RESOURCE,
                params[0], CloudConfig.METHOD_POST, null, String.class);
        Futures.addCallback(result, new FutureCallback<String>() {
            @Override
            public void onFailure(Throwable exc) {
                Toast.makeText(NotifierActivity.instance, exc.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error while sendign device notification", exc);
            }

            @Override
            public void onSuccess(String result) {
                Toast.makeText(NotifierActivity.instance, "Notification successful.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Successfully sent the notification");
            }
        });
        return null;
    }
}
