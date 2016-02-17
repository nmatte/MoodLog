package com.nmatte.mood.reminders;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nmatte.mood.models.BoolComponent;
import com.nmatte.mood.moodlog.R;

import java.util.ArrayList;

public class ReminderList extends LinearLayout {
    ArrayList<Reminder> reminders;
    private Context context;



    public ReminderList(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.setOrientation(VERTICAL);
        updateList(ReminderTableHelper.getAll(context));

    }

    public void updateList(ArrayList<Reminder> reminders){
        this.removeAllViews();
        final LayoutInflater inflater = LayoutInflater.from(context);
        for (Reminder reminder : reminders){
            View  v = inflater.inflate(R.layout.row_reminder_edit,this);
            EditText messageText = (EditText) v.findViewById(R.id.messageText);
            messageText.setText(reminder.getMessage());
            TextView timeText = (TextView) v.findViewById(R.id.timeText);
            timeText.setText(reminder.calendarString());
        }

        View footer = inflater.inflate(R.layout.notification_list_footer,this);
        footer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addReminderEdit();
            }
        });

    }

    public void addReminderEdit(){
        View v = inflate(context,R.layout.row_reminder_edit,this);
    }

    public void addNotification(int hour, int minute, ArrayList<BoolComponent> m){
        /*
        notifications = listener.getAll();
        DateTime time = DateTime
                .now()
                .withHourOfDay(hour)
                .withMinuteOfHour(minute);
        Reminder newNotification = new Reminder(time,m);
        for (Reminder oldNotification : notifications) {
            if (Reminder.equal(oldNotification, newNotification)) {
                newNotification = merge(newNotification, oldNotification);
                listener.delete(oldNotification);
            }
        }
        listener.add(newNotification);
        */

    }





}
