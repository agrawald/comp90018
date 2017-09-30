package rfid.melb.uni.au.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import rfid.melb.uni.au.activity.R;

/**
 * {@link android.support.v4.app.DialogFragment} to represent a display notification
 * Created by Dheeraj Agrawal (agrawald@student.unimelb.edu.au) on 24-Sep-17.
 */

public class DisplayDialogFragment extends DialogFragment {
    private final static String TAG = DisplayDialogFragment.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String message = getArguments().getString("message");
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setTitle(R.string.dialog_display_title);
        // Add the buttons
        builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.i(TAG, "Done");
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
