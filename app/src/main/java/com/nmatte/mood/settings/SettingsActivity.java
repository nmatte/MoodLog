package com.nmatte.mood.settings;

import android.app.DialogFragment;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.nmatte.mood.medications.MedList;
import com.nmatte.mood.medications.MedTableHelper;
import com.nmatte.mood.medications.Medication;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.notifications.AddMedNotificationDialog;
import com.nmatte.mood.notifications.AlarmManagerHelper;
import com.nmatte.mood.notifications.DeleteMedNotificationDialog;
import com.nmatte.mood.notifications.MedNotification;
import com.nmatte.mood.notifications.MedNotificationTableHelper;
import com.nmatte.mood.notifications.NotificationList;

import java.util.ArrayList;

/**
 * Created by Nathan on 6/16/2015.
 */
public class SettingsActivity
        extends ActionBarActivity
        implements NotificationList.NotificationListListener,
        AddMedNotificationDialog.AddNotificationListener,
        DeleteMedNotificationDialog.DeleteNotificationListener,
        MedList.MedListListener{
    MedTableHelper MTHelper;
    MedNotificationTableHelper MRTHelper;
    NotificationList notificationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        MTHelper = new MedTableHelper(this);
        MRTHelper = new MedNotificationTableHelper(this);
        notificationList = (NotificationList) findViewById(R.id.notificationList);
        notificationList.updateList(this);
    }


    public void notificationTest(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
               .setSmallIcon(R.drawable.notification_icon)
               .setContentTitle("Test!")
               .setContentText("TEEEEEEEST");

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(001,builder.build());

    }

    @Override
    public void add(MedNotification notification) {

    }

    @Override
    public void newNotificationDialog() {
        DialogFragment dialog = new AddMedNotificationDialog();
        dialog.show(getFragmentManager(),"Add Notification Dialog");
    }

    @Override
    public void delete(MedNotification notification) {
        DeleteMedNotificationDialog dialog = new DeleteMedNotificationDialog();
        Bundle b = new Bundle();
        b.putInt("time", notification.timeID);

        dialog.setArguments(b);
        dialog.show(getFragmentManager(),"Delete MedNotification Dialog");

    }

    @Override
    public ArrayList<MedNotification> getMedReminderList() {
        return MRTHelper.getMedReminderList();

    }

    @Override
    public void delete(Medication medication) {
        //no-op
    }

    @Override
    public void addNew() {
    }

    @Override
    public ArrayList<Medication> getMedList() {
        return MTHelper.getMedicationList();
    }


    @Override
    public void onAddDialogPositiveClick(MedNotification notification) {
        MRTHelper.addNotification(notification);
        AlarmManagerHelper.setAlarms(this);
        notificationList.updateList(this);
    }

    @Override
    public void onDeleteDialogPositiveClick(int timeID) {
        MRTHelper.deleteMedReminder(timeID);
        notificationList.updateList(this);
    }
}
