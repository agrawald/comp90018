package au.uni.melb.rfid.nfc.common;

import android.content.Context;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;

import au.uni.melb.rfid.nfc.model.Payload;

/**
 * Adapter to do all the initialization work with Azure Mobile Web Application backend
 */
public class AzureServiceAdapter {
    private static AzureServiceAdapter mInstance = null;
    private Context mContext;
    private MobileServiceClient mClient;

    private AzureServiceAdapter(Context context) throws MalformedURLException {
        mContext = context;
        mClient = new MobileServiceClient(CloudConfig.MOBILE_BACKEND_URL, mContext);
    }

    /**
     * Initializtion method
     *
     * @param context {@link Context}
     * @throws MalformedURLException
     */
    public static void init(Context context) throws MalformedURLException {
        if (mInstance == null) {
            mInstance = new AzureServiceAdapter(context);
        }
    }

    /**
     * Function to get the singleton instance of this class
     * @return instance of {@link AzureServiceAdapter}
     */
    public static AzureServiceAdapter getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("AzureServiceAdapter is not initialized");
        }
        return mInstance;
    }

    /**
     * Function to get the {@link MobileServiceTable} insatcne
     * @param tableName Name of the table as {@link String}
     * @return MobileServiceTable
     */
    public MobileServiceTable<Payload> getTable(final String tableName) {
        return mClient.getTable(tableName, Payload.class);
    }
}
