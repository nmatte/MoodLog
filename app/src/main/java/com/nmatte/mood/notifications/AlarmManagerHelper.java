package com.nmatte.mood.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmManagerHelper extends BroadcastReceiver {
    static final String NMATTE_NOTIFICATION_ACTION = "nmatte notification action";
    static final String MEDICATION_IDS = "medication ids";

    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
    }

    public static void setAlarms(Context context){
        MedNotificationTableHelper DBHelper = new MedNotificationTableHelper(context);
        cancelAlarms(context);
        ArrayList<MedNotification> reminderList = DBHelper.getMedReminderList();
        for (MedNotification reminder : reminderList){
            setAlarm(context,reminder.time,createIntent(context,reminder));
        }

    }

    public static void cancelAlarms(Context context){
        MedNotificationTableHelper DBHelper = new MedNotificationTableHelper(context);
        ArrayList<MedNotification> reminderList = DBHelper.getMedReminderList();
        if (reminderList != null) {
            for (MedNotification reminder : reminderList) {
                PendingIntent pi = createIntent(context,reminder);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pi);
            }
        }
    }

    private static PendingIntent createIntent(Context context, MedNotification notification){
        Intent intent = new Intent(context, NotificationService.class);
        intent.putExtra(MEDICATION_IDS, notification.medIDString());
        intent.setAction(NMATTE_NOTIFICATION_ACTION);
        return PendingIntent.getService(context, notification.timeID,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static void setAlarm(Context context, Calendar calendar, PendingIntent pIntent){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pIntent);
    }
}
