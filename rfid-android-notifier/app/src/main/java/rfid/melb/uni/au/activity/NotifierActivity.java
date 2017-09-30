package rfid.melb.uni.au.activity;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.microsoft.windowsazure.notifications.NotificationsManager;

import java.net.MalformedURLException;

import rfid.melb.uni.au.common.AzureServiceAdapter;
import rfid.melb.uni.au.common.CloudConfig;
import rfid.melb.uni.au.dialog.AuthorizeDialogFragment;
import rfid.melb.uni.au.dialog.DisplayDialogFragment;
import rfid.melb.uni.au.handler.NotificationHandler;
import rfid.melb.uni.au.service.GooglePlaySvc;
import rfid.melb.uni.au.service.ToastSvc;

/**
 * Main activity class to show the main UI for the application
 * Created by Dheeraj Agrawal (agrawald@student.unimelb.edu.au) on 24-Sep-17.
 */
public class NotifierActivity extends AppCompatActivity {
    private static final String TAG = NotifierActivity.class.getSimpleName();
    public static NotifierActivity instance;
    private final GooglePlaySvc googlePlaySvc;

    /**
     * Constructor to initialize {@link GooglePlaySvc}
     */
    public NotifierActivity() {
        this.googlePlaySvc = new GooglePlaySvc(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifier);
        instance = this;
        //initializing the cloud GCM notification manager
        NotificationsManager.handleNotifications(this, CloudConfig.SENDER_ID, NotificationHandler.class);
        googlePlaySvc.register();

        //initializing the azure cloud services
        try {
            AzureServiceAdapter.init(this.getApplicationContext());
        } catch (MalformedURLException e) {
            Log.e(TAG, "Unable to register with the azure backend service", e);
            this.finish();
        }

        //only show install dialog when we do not have parameters sets
        if (this.getIntent().getExtras() == null) {
            this.showInstallDialog();
        }
    }

    /**
     * Function to show the authorization ot diaply dialog when a notificaiton message arrives
     *
     * @param mainBundle {@link Bundle}
     */
    public void showDialog(Bundle mainBundle) {
        final String rfid = mainBundle.getString("id");
        final String type = mainBundle.getString("type");
        String message;
        DialogFragment dialog;
        //check if the request type is for authorization (1) or just a display message (0)
        if (Integer.parseInt(type) > 0) {
            message = String.format(NotifierActivity.instance.getResources().getString(R.string.dialog_authorize_message), rfid);
            dialog = new AuthorizeDialogFragment();
        } else {
            message = String.format(NotifierActivity.instance.getResources().getString(R.string.dialog_display_message), rfid);
            dialog = new DisplayDialogFragment();
        }
        final Bundle dialogBundle = new Bundle(mainBundle);
        dialogBundle.putString("message", message);
        dialog.setArguments(dialogBundle);
        dialog.show(this.getFragmentManager(), "dialogFragment");
        ToastSvc.make(this, message);
    }

    /**
     * Function to show the install successful dialog
     */
    private void showInstallDialog() {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("RFID Notifier Application Started.")
                .setTitle(R.string.dialog_install_title);
        // Add the buttons
        builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NotifierActivity.instance.finish();
            }
        });
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.getIntent().getExtras() != null) {
            showDialog(this.getIntent().getExtras());
        }
    }
}
