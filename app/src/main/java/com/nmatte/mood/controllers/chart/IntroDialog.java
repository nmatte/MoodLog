package com.nmatte.mood.controllers.chart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;

import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.settings.PreferencesContract;

public class IntroDialog extends DialogFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .edit()
                .putBoolean(PreferencesContract.TUTORIAL_FINISHED, true)
                .apply();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.intro_choose_preset,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setView(view)
                .setTitle("intro")
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IntroDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }


}
