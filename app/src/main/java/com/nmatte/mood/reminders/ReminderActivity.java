package com.nmatte.mood.reminders;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import com.nmatte.mood.moodlog.R;

import org.joda.time.DateTime;

public class ReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
    }

    public void setTime(View view) {
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                /*
                EventBus.getDefault().post(
                        new ReminderSaveEvent(
                                new Reminder(
                                        new DateTime().withHourOfDay(hourOfDay).withMinuteOfHour(minute),
                                        "test text")));
                                        */

            }
        };

        TimePickerDialog dialog = new TimePickerDialog(this,listener,0,0,false);
        dialog.show();
    }

    public void makeAndDeleteReminder(View view){
        DateTime time = DateTime.now().plusMinutes(2);
        Reminder reminder = new Reminder(time,"Cancel Test");
        ReminderTableHelper.addReminder(this,reminder);
        AlarmManagerHelper.setAlarms(this);
        AlarmManagerHelper.cancelAlarms(this);
        Log.d("Reminders", "Set and canceled test reminder for time " + time.toString("HHmmss"));
    }

    public void oneMinTest(View view){
        DateTime time = DateTime.now().plusMinutes(2);
        Reminder reminder = new Reminder(time,"non-cancel Test");
        ReminderTableHelper.addReminder(this,reminder);
        AlarmManagerHelper.setAlarms(this);
        Log.d("Reminders", "Set test reminder for time " + time.toString("HH:mm:ss"));
    }
}
