package com.nmatte.mood.settings;

import android.app.DialogFragment;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.logbookitems.numitems.NumItemTableHelper;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.notifications.AddMedNotificationDialog;
import com.nmatte.mood.notifications.AlarmManagerHelper;
import com.nmatte.mood.notifications.DeleteMedNotificationDialog;
import com.nmatte.mood.notifications.MedNotification;
import com.nmatte.mood.notifications.MedNotificationTableHelper;
import com.nmatte.mood.notifications.NotificationList;

import java.util.ArrayList;

public class SettingsActivity
        extends ActionBarActivity
        implements NotificationList.NotificationListListener,
        AddMedNotificationDialog.AddNotificationListener,
        DeleteMedNotificationDialog.DeleteNotificationListener{
    NotificationList notificationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        notificationList = (NotificationList) findViewById(R.id.notificationList);
        notificationList.updateList(this);
    }


    public void notificationTest(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
               .setSmallIcon(R.drawable.notification_icon)
               .setContentTitle("Test!")
               .setContentText("TEEEEEEEST");

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(001, builder.build());

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
        MedNotificationTableHelper MRTHelper = new MedNotificationTableHelper(this);
        return MRTHelper.getMedReminderList();

    }


    @Override
    public ArrayList<BoolItem> getMedList() {
        return BoolItemTableHelper.getAll(this);
    }


    @Override
    public void onAddDialogPositiveClick(MedNotification notification) {
        MedNotificationTableHelper MRTHelper = new MedNotificationTableHelper(this);
        MRTHelper.addNotification(notification);
        AlarmManagerHelper.setAlarms(this);
        notificationList.updateList(this);
    }

    @Override
    public void onDeleteDialogPositiveClick(int timeID) {
        MedNotificationTableHelper MRTHelper = new MedNotificationTableHelper(this);
        MRTHelper.deleteMedReminder(timeID);
        AlarmManagerHelper.setAlarms(this);
        notificationList.updateList(this);
    }


    private void createDefaultNumItems(){
        NumItem anx = new NumItem(0,"Anxiety",3,0);
        NumItemTableHelper.addNumItem(this, anx);

        NumItem irr = new NumItem(0,"Irritability",3,0);
        NumItemTableHelper.addNumItem(this,irr);

        NumItem sleep = new NumItem(0,"Sleep",24,0);
        NumItemTableHelper.addNumItem(this,sleep);
    }
}
