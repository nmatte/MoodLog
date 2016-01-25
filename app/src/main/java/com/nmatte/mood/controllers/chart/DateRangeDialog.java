package com.nmatte.mood.controllers.chart;

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
    public static String
            BOOL_IS_START_PICKER = "StartOrEnd",
            LONG_START_DATE_VALUE = "StartDateValue",

            BUTTON_END = "Done",
            BUTTON_START = "Next",
            TITLE_START = "Choose start date",
            TITLE_END = "Choose end date";
    DatePicker datePicker;
    CheckBox saveDateCheckBox;
    DateTime startDate;
    boolean isStart = true;
    private DialogInterface.OnClickListener onPositiveClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // datePicker provides month as 0-11 while DateTime takes 1-12, so increment
            DateTime chosenDate = new DateTime(datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth(),0,0);
            if(isStart)
                EventBus.getDefault().post(new OpenEndDateDialogEvent(chosenDate));
            else
                EventBus.getDefault().post(new SaveEndDateDialogEvent(startDate,chosenDate,saveDateCheckBox.isChecked()));

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*
    date picker behavior:
    two dialogs in succession: first, pick the start date. Start date picker has max date of today
    second, pick the end date. Minimum is the chosen start date and maximum is today.
     */

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args.containsKey(BOOL_IS_START_PICKER)){
            isStart = args.getBoolean(BOOL_IS_START_PICKER);
        }
        if (args.containsKey(LONG_START_DATE_VALUE)){
            startDate = new DateTime(args.getLong(LONG_START_DATE_VALUE));
        }
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_pick_date_range, null);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        saveDateCheckBox = (CheckBox) view.findViewById(R.id.saveBox);
        initWidgets();

        String title = isStart? TITLE_START : TITLE_END;
        String positiveButtonText = isStart? BUTTON_START : BUTTON_END;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle(title)
                .setPositiveButton(positiveButtonText, onPositiveClickListener)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DateRangeDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void initWidgets() {
        if (isStart){
            // maximum start date is current date.
            long maxTime = DateTime.now().getMillis();
            datePicker.setMaxDate(maxTime);
        } else {

            saveDateCheckBox.setVisibility(View.VISIBLE);
            saveDateCheckBox.setText("Remember next time");

            // minimum date must be the start date.
            DateTime minDate = startDate;
            datePicker.setMinDate(minDate.getMillis());
            datePicker.updateDate(minDate.getYear(), minDate.getMonthOfYear(),minDate.getDayOfMonth());


            // maximum date is 31 days after min date.
            DateTime maxDate = minDate.plusDays(31);
            datePicker.setMaxDate(maxDate.getMillis());
        }
    }
}
