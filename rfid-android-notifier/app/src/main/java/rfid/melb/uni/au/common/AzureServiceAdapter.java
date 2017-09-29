package rfid.melb.uni.au.common;

import android.content.Context;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;

import rfid.melb.uni.au.model.Payload;

public class AzureServiceAdapter {
    private static AzureServiceAdapter mInstance = null;
    private String mMobileBackendUrl = "http://rfidbackendandroid.azurewebsites.net";
    private Context mContext;
    private MobileServiceClient mClient;

    private AzureServiceAdapter(Context context) throws MalformedURLException {
        mContext = context;
        mClient = new MobileServiceClient(mMobileBackendUrl, mContext);
    }

    public static void init(Context context) throws MalformedURLException {
        if (mInstance == null) {
            mInstance = new AzureServiceAdapter(context);
        }
    }

    public static AzureServiceAdapter getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("AzureServiceAdapter is not initialized");
        }
        return mInstance;
    }

    public MobileServiceTable<Payload> getTable(final String tableName) {
        return mClient.getTable(tableName, Payload.class);
    }
}
