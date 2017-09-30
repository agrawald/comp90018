package rfid.melb.uni.au.service;

import android.app.Activity;
import android.widget.Toast;

/**
 * Service to show toast on a seperate ui thread
 * Created by dagrawal on 24-Sep-17.
 */
public class ToastSvc implements Runnable {
    private final Activity parent;
    private final String message;

    public ToastSvc(Activity parent, String message) {
        this.parent = parent;
        this.message = message;
    }

    public static void make(final Activity activity, final String message) {
        activity.runOnUiThread(new ToastSvc(activity, message));
    }

    @Override
    public void run() {
        Toast.makeText(parent, message, Toast.LENGTH_LONG).show();
    }
}
