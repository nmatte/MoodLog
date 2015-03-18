package com.nmatte.mood.moodlog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class DeleteMedicationDialog extends DialogFragment{
    DeleteMedicationListener listener;
    String name;

    public interface DeleteMedicationListener{
        public void onDeleteDialogPositiveClick(String name);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            listener = (DeleteMedicationListener) activity;
        } catch (ClassCastException e){
            Log.e("Class cast exception", "failed to instantiate listener");
        }

    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        name = (String) args.getCharSequence("name");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setMessage("All log data related to "+name+" will be removed.")
                .setTitle("Delete "+name+"?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onDeleteDialogPositiveClick(name);
            }
        })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DeleteMedicationDialog.this.getDialog().cancel();
            }
        });

        return builder.create();

    }





}