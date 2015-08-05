package com.nmatte.mood.chart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;

import com.nmatte.mood.moodlog.R;

import java.util.Calendar;

public class DateRangeDialog extends DialogFragment {
    DateRangeDialongListener listener;
    DatePicker datePicker;
    boolean isStart = true;

    public static String
            IS_START_PICKER = "StartOrEnd",
            START_DATE_VALUE = "StartDateValue",
            BUTTON_END = "Done",
            BUTTON_START = "Next",
            TITLE_START = "Choose start date",
            TITLE_END = "Choose end date";

    interface DateRangeDialongListener{
        void startDatePicked(Calendar startDate);
        void endDatePicked(Calendar endDate, boolean save);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            listener = (DateRangeDialongListener) getActivity();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_pick_date_range, null);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        final CheckBox saveBox = (CheckBox) view.findViewById(R.id.saveBox);

        try{
            isStart = getArguments().getBoolean(IS_START_PICKER);
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            initDatePicker(getArguments());
        } catch (Exception e){
            e.printStackTrace();
        }

        String title = isStart? TITLE_START : TITLE_END;
        String positiveButtonText = isStart? BUTTON_START : BUTTON_END;


        if (!isStart){
            saveBox.setVisibility(View.VISIBLE);
            saveBox.setText("Remember next time");
        }

        final boolean isStartFinal = isStart;
        builder.setView(view)
                .setTitle(title)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Calendar date = Calendar.getInstance();
                        date.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                        date.set(Calendar.MONTH, datePicker.getMonth());
                        date.set(Calendar.YEAR, datePicker.getYear());
                        if(isStartFinal)
                            listener.startDatePicked(date);
                        else
                            listener.endDatePicked(date, saveBox.isChecked());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DateRangeDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    /*
    date picker behavior:
    two dialogs in succession: first, pick the start date. Start date picker has max date of today
    second, pick the end date. Minimum is the chosen start date and maximum is today.
     */

    private void initDatePicker(Bundle args) throws Exception {
        if (isStart){
            long maxTime = Calendar.getInstance().getTimeInMillis();
            datePicker.setMaxDate(maxTime);
        } else {
            long minDate = args.getLong(START_DATE_VALUE);
            Calendar max = Calendar.getInstance();
            max.setTimeInMillis(minDate);
            max.roll(Calendar.DAY_OF_YEAR,31);
            long maxDate = max.getTimeInMillis();
            datePicker.setMinDate(minDate);
            datePicker.setMaxDate(maxDate);
        }
    }
}
