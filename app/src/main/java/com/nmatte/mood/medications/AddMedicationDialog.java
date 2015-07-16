package com.nmatte.mood.medications;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.nmatte.mood.moodlog.R;

public class AddMedicationDialog extends DialogFragment {
    AddMedicationListener listener;

    public interface AddMedicationListener {
        void onAddDialogPositiveClick(String name);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener = (AddMedicationListener) activity;
        } catch (ClassCastException e){
            Log.e("Class cast exception", "failed to instantiate listener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_medication_layout,null);
        final EditText nameEditText = (EditText) view.findViewById(R.id.nameEditText);
        builder.setView(view)
               .setTitle(getString(R.string.add_medication_dialog_title))
               .setPositiveButton(getString(R.string.add_medication_dialog_add_button), new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       String name = nameEditText.getText().toString();
                       if (name.length() > 0) {
                           listener.onAddDialogPositiveClick(name);
                       } else {
                           AddMedicationDialog.this.getDialog().cancel();
                       }
                   }
               })
                .setNegativeButton(getString(R.string.add_medication_dialog_cancel_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddMedicationDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
