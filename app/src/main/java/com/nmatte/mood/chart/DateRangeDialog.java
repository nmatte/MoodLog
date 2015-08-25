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

import org.joda.time.DateTime;

import de.greenrobot.event.EventBus;

public class DateRangeDialog extends DialogFragment {
    DatePicker datePicker;
    DateTime startDate;
    boolean isStart = true;

    public static String
            BOOL_IS_START_PICKER = "StartOrEnd",
            LONG_START_DATE_VALUE = "StartDateValue",

            BUTTON_END = "Done",
            BUTTON_START = "Next",
            TITLE_START = "Choose start date",
            TITLE_END = "Choose end date";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_pick_date_range, null);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        final CheckBox saveBox = (CheckBox) view.findViewById(R.id.saveBox);

        try{
            isStart = getArguments().getBoolean(BOOL_IS_START_PICKER);
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

        final boolean isStartF = isStart;
        builder.setView(view)
                .setTitle(title)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DateTime chosenDate = new DateTime(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),0,0);

                        if(isStartF)
                            EventBus.getDefault().post(new OpenEndDateDialogEvent(chosenDate));
                        else
                            EventBus.getDefault().post(new SaveEndDateDialogEvent(chosenDate,startDate,saveBox.isChecked()));
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
            // maximum start date is current date.
            long maxTime = DateTime.now().getMillis();
            datePicker.setMaxDate(maxTime);
        } else {
            // minimum date must be the start date.
            // pull start date from the bundle.
            DateTime minDate = new DateTime(args.getLong(LONG_START_DATE_VALUE));
            startDate = minDate;
            datePicker.setMinDate(minDate.getMillis());

            // maximum date is 31 days after min date.
            DateTime maxDate = minDate.plusDays(31);
            datePicker.setMaxDate(maxDate.getMillis());
        }
    }
}
