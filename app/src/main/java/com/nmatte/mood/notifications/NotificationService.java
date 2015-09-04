package com.nmatte.mood.notifications;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.nmatte.mood.moodlog.R;

public class NotificationService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() == Intent.ACTION_BOOT_COMPLETED){
            AlarmManagerHelper.setAlarms(this);
        } else if (intent.getAction() == AlarmManagerHelper.NMATTE_NOTIFICATION_ACTION){
            Bundle args = intent.getExtras();
            String medString = args.getString(AlarmManagerHelper.MEDICATION_IDS);
            simpleNotification(medString);
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    private void simpleNotification(String medString){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Medication reminder")
                .setContentText("replace me!");

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
