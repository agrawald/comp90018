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

public class NotifierActivity extends AppCompatActivity {
    private static final String TAG = NotifierActivity.class.getSimpleName();
    public static NotifierActivity instance;
    private final GooglePlaySvc googlePlaySvc;

    public NotifierActivity() {
        this.googlePlaySvc = new GooglePlaySvc(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifier);
        instance = this;
        NotificationsManager.handleNotifications(this, CloudConfig.SENDER_ID, NotificationHandler.class);
        googlePlaySvc.register();
        try {
            AzureServiceAdapter.init(this.getApplicationContext());
        } catch (MalformedURLException e) {
            Log.e(TAG, "Unable to register with the azure backend service", e);
            this.finish();
        }

        this.showInstallDialog();
    }

    public void showDialog(Bundle mainBundle) {
        final String rfid = mainBundle.getString("id");
        final String type = mainBundle.getString("type");
        String message;
        DialogFragment dialog;
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
