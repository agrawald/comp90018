package rfid.melb.uni.au.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import rfid.melb.uni.au.activity.NotifierActivity;
import rfid.melb.uni.au.activity.R;
import rfid.melb.uni.au.dao.RfidAuthDao;
import rfid.melb.uni.au.model.Payload;

/**
 * Created by dagrawal on 24-Sep-17.
 */

public class AuthorizeDialogFragment extends DialogFragment {
    private final static String TAG = AuthorizeDialogFragment.class.getSimpleName();
    private final static RfidAuthDao rfidAuthDao = new RfidAuthDao();

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

    private static class DialogOnClickListener implements DialogInterface.OnClickListener {
        private final Bundle bundle;

        private DialogOnClickListener(Bundle bundle) {
            this.bundle = bundle;
        }

        @Override
        public void onClick(DialogInterface dialog, int id) {
            // send to azure
            try {
                rfidAuthDao.saveOrUpdate(new Payload(bundle.getString("id"), true));
            } catch (Exception e) {
                Log.e(TAG, "Unable to save or update RFID", e);
            } finally {
                NotifierActivity.instance.finish();
            }
        }
    }
}
