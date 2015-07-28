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
        void onSaveEntryPositiveClick(FlexibleLogbookEntry entry);
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
        final FlexibleLogbookEntryFragment entryFragment =
                (FlexibleLogbookEntryFragment) getFragmentManager().findFragmentById(R.id.dialogEntryFragment);
        // TODO: fix bug where dialog causes FC when created a second time
        // fixed?
        entryFragment.setRetainInstance(false);

        FlexibleLogbookEntry entry = b.getParcelable(FlexibleLogbookEntry.PARCEL_TAG);
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
