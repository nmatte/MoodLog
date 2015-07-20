package com.nmatte.mood.logbookentries;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.nmatte.mood.moodlog.R;

public class SingleEntryDialog extends DialogFragment {
    SingleEntryDialogListener listener;

    public interface SingleEntryDialogListener{
        void onSaveEntryPositiveClick(LogbookEntry entry);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (SingleEntryDialogListener) activity;
        } catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View mainView = inflater.inflate(R.layout.dialog_single_entry, null);
        Bundle b = getArguments();
        final LogbookEntryFragment entryFragment = (LogbookEntryFragment) getFragmentManager().findFragmentById(R.id.dialogEntryFragment);
        LogbookEntry entry = new LogbookEntry(
                b.getInt(LogbookEntry.DATE_TAG),
                b.getString(LogbookEntry.MOOD_TAG),
                b.getInt(LogbookEntry.IRR_TAG),
                b.getInt(LogbookEntry.ANX_TAG),
                b.getInt(LogbookEntry.SLEEP_TAG),
                b.getString(LogbookEntry.MED_TAG)
        );
        entryFragment.setEntry(entry);
        builder.setView(mainView)
                .setTitle("Edit entry")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onSaveEntryPositiveClick(entryFragment.getEntry());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SingleEntryDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
