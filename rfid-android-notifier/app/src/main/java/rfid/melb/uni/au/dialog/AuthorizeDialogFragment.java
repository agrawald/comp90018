package rfid.melb.uni.au.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import rfid.melb.uni.au.activity.NotifierActivity;
import rfid.melb.uni.au.activity.R;
import rfid.melb.uni.au.dao.InsertRfidDao;
import rfid.melb.uni.au.dao.NotificationDao;
import rfid.melb.uni.au.model.Payload;

/**
 * {@link android.support.v4.app.DialogFragment} to represent a authroization request
 * Created by Dheeraj Agrawal (agrawald@student.unimelb.edu.au) on 24-Sep-17.
 */

public class AuthorizeDialogFragment extends DialogFragment {
    private final static String TAG = AuthorizeDialogFragment.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        final String message = getArguments().getString("message");
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setTitle(R.string.dialog_authorize_title);
        // Add the buttons
        builder.setPositiveButton(R.string.approve, new DialogOnClickListener(getArguments()));
        builder.setNegativeButton(R.string.dismiss, new DialogOnClickListener(getArguments()));
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private class DialogOnClickListener implements DialogInterface.OnClickListener {
        private final Bundle bundle;
        private final InsertRfidDao insertRfidDao;
        private final NotificationDao notificationDao;

        private DialogOnClickListener(Bundle bundle) {
            this.bundle = bundle;
            insertRfidDao = new InsertRfidDao();
            notificationDao = new NotificationDao();
        }

        @Override
        public void onClick(DialogInterface dialog, int id) {
            // send to azure
            try {
                final Payload payload = new Payload(bundle.getString("id"), id == -1);
                insertRfidDao.execute(payload).get();
                notificationDao.execute(payload).get();
            } catch (Exception e) {
                Log.e(TAG, "Unable to save or update RFID", e);
            } finally {
                NotifierActivity.instance.finish();
            }
        }
    }
}
