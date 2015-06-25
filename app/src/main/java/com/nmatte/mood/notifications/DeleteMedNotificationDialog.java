package com.nmatte.mood.notifications;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class DeleteMedNotificationDialog extends DialogFragment {
    DeleteNotificationListener listener;

    public interface DeleteNotificationListener{
        void onDeleteDialogPositiveClick(int timeID);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (DeleteNotificationListener) activity;
        } catch (Exception e){
            Log.e("class cast exception", "couldn't cast listener",e);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        final int timeID = (int) args.getInt("time");


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setMessage("This reminder notification will be removed.")
                .setTitle("Delete?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDeleteDialogPositiveClick(timeID);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteMedNotificationDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
