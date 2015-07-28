package com.nmatte.mood.notifications;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.nmatte.mood.logbookitems.boolitems.BoolItemList;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.moodlog.R;

import java.util.ArrayList;

public class AddMedNotificationDialog extends DialogFragment {
    AddNotificationListener listener;

    public interface AddNotificationListener{
        void onAddDialogPositiveClick(MedNotification notification);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener = (AddNotificationListener) activity;
        } catch (Exception e){
            Log.e("add notification", "couldn't cast notification listener",e);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_notification_dialog, null);
        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker);
        final BoolItemList boolItemList = (BoolItemList) view.findViewById(R.id.medList);
        //TODO replace boolItemList.updateList(getActivity());
        /*
        builder.setView(view)
                .setTitle("New Notification")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour = timePicker.getCurrentHour();
                        int minute = timePicker.getCurrentMinute();
                        ArrayList<BoolItem> boolItems = boolItemList.getCheckedMeds();
                        listener.onAddDialogPositiveClick(new MedNotification(hour,minute, boolItems));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddMedNotificationDialog.this.getDialog().cancel();
                    }
                });
*/
        return builder.create();
    }
}
