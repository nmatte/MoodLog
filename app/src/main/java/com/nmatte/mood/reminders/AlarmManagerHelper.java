package com.nmatte.mood.reminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

public class AlarmManagerHelper extends BroadcastReceiver {
    static final String NMATTE_NOTIFICATION_ACTION = "NmatteNotification";
    static final String REMINDER_MESSAGE = "ReminderMessage";

    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
    }

    public static void setAlarms(Context context){
        cancelAlarms(context);
        ArrayList<Reminder> reminderList = ReminderTableHelper.getAll(context);
        for (Reminder reminder : reminderList){
            PendingIntent pendingIntent = createIntent(context,reminder);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    reminder.getTime().getMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        }

    }
// FIXME alarms not getting canceled?
    public static void cancelAlarms(Context context){
        ArrayList<Reminder> reminderList = ReminderTableHelper.getAll(context);
        if (reminderList != null) {
            for (Reminder reminder : reminderList) {
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(createIntent(context,reminder));
            }
        }
    }

    private static PendingIntent createIntent(Context context, Reminder reminder){
        Intent intent = new Intent(context.getApplicationContext(), ReminderService.class);
        intent.putExtra(REMINDER_MESSAGE, reminder.getMessage());
        intent.setAction(NMATTE_NOTIFICATION_ACTION);
        return PendingIntent.getService(context, reminder.timeOfDay(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
