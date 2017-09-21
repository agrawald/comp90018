package au.uni.melb.rfid.notifier;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import au.uni.melb.rfid.notifier.activity.MainActivity;
import au.uni.melb.rfid.notifier.model.Notification;


public class NotificationAdapter extends ArrayAdapter<Notification> {

    /**
     * Adapter context
     */
    Context mContext;

    /**
     * Adapter View layout
     */
    int mLayoutResourceId;

    public NotificationAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }

    /**
     * Returns the view for a specific item on the list
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final Notification currentItem = getItem(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
        }

        row.setTag(currentItem);
        final CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkNotification);
        checkBox.setText(currentItem.getText());
        checkBox.setChecked(false);
        checkBox.setEnabled(true);

        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (checkBox.isChecked()) {
                    checkBox.setEnabled(false);
                    if (mContext instanceof MainActivity) {
                        MainActivity activity = (MainActivity) mContext;
                        Log.d("!!!!!!", currentItem.getId());
                        Log.d("!!!!!!", currentItem.getText());
                        activity.checkItem(currentItem);
                    }
                }
            }
        });

        return row;
    }

}