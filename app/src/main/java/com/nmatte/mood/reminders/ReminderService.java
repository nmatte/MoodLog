package com.nmatte.mood.reminders;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.nmatte.mood.moodlog.R;

public class ReminderService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            AlarmManagerHelper.setAlarms(this);
            Log.d("Reminders","Reminders set on boot");
        } else if (intent.getAction().equals(AlarmManagerHelper.NMATTE_NOTIFICATION_ACTION)){
            Log.d("Reminders", "Reminder intent received");
            simpleNotification(intent.getStringExtra(AlarmManagerHelper.REMINDER_MESSAGE));
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    private void simpleNotification(String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Reminder")
                .setContentText(message);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1,builder.build());
    }

    public void notificationTest() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Test!")
                .setContentText("TEEEEEEEST");

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1,builder.build());

    }
}
