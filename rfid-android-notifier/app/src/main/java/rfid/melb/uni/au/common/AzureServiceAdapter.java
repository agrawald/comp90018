package rfid.melb.uni.au.common;

import android.content.Context;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import rfid.melb.uni.au.model.Payload;

import static rfid.melb.uni.au.common.CloudConfig.MOBILE_BACKEND_URL;

/**
 * Adapter class to initialize all the azure related services
 * Created by Dheeraj Agrawal (agrawald@student.unimelb.edu.au) on 24-Sep-17.
 */
public class AzureServiceAdapter {
    private static AzureServiceAdapter mInstance = null;
    private Context mContext;
    private MobileServiceClient mClient;

    /**
     * Private constructor to make this a singletong class
     * Initializes the {@link MobileServiceClient}
     *
     * @param context
     * @throws MalformedURLException
     */
    private AzureServiceAdapter(Context context) throws MalformedURLException {
        mContext = context;
        mClient = new MobileServiceClient(MOBILE_BACKEND_URL, mContext);
        //initialize the client and azure server communication
        mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
            @Override
            public OkHttpClient createOkHttpClient() {
                OkHttpClient client = new OkHttpClient();
                client.setReadTimeout(60, TimeUnit.SECONDS);
                client.setWriteTimeout(60, TimeUnit.SECONDS);
                return client;
            }
        });
    }

    /**
     * Function to initialize things with azure
     * @param context {@link Context}
     * @throws MalformedURLException
     */
    public static void init(Context context) throws MalformedURLException {
        if (mInstance == null) {
            mInstance = new AzureServiceAdapter(context);
        }
    }

    /**
     * Singleton fucntion to get the one instance we created
     * @return instance of {@link AzureServiceAdapter}
     */
    public static AzureServiceAdapter getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("AzureServiceAdapter is not initialized");
        }
        return mInstance;
    }

    /**
     * Function to get the table based on the table name
     * @param tableName {@link String}
     * @return instance of {@link MobileServiceTable}
     */
    public MobileServiceTable<Payload> getTable(final String tableName) {
        return mClient.getTable(tableName, Payload.class);
    }

    /**
     * Function to get the {@link MobileServiceClient}
     *
     * @return instance of {@link MobileServiceClient}
     */
    public MobileServiceClient getMobileServiceClient() {
        return mClient;
    }
}
