package com.nmatte.mood.settings;

import android.app.DialogFragment;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.nmatte.mood.logbookentries.FlexibleLogbookEntry;
import com.nmatte.mood.logbookentries.FlexibleLogbookEntryTableHelper;
import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.logbookentries.LogbookEntryTableHelper;
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
import java.util.Calendar;

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

    public void migrateEntries(View v){
        Calendar start = Calendar.getInstance();
        start.set(Calendar.DAY_OF_MONTH, 1);

        ArrayList<LogbookEntry> oldEntries = LogbookEntryTableHelper.getEntryGroup(this, start, Calendar.getInstance());

        createDefaultNumItems();

        for (LogbookEntry e : oldEntries){
            FlexibleLogbookEntryTableHelper.addOrUpdateEntry(this,convertEntry(e));
        }
    }

    private void createDefaultNumItems(){
        NumItem anx = new NumItem(0,"Anxiety",3,0);
        NumItemTableHelper.addNumItem(this, anx);

        NumItem irr = new NumItem(0,"Irritability",3,0);
        NumItemTableHelper.addNumItem(this,irr);

        NumItem sleep = new NumItem(0,"Sleep",24,0);
        NumItemTableHelper.addNumItem(this,sleep);
    }

    private FlexibleLogbookEntry convertEntry(LogbookEntry e){
        ArrayList<NumItem> numItems = NumItemTableHelper.getAll(this);
        NumItem anx = null;
        NumItem irr = null;
        NumItem sleep = null;

        for (NumItem item : numItems){
            if (item.getName().equals("Anxiety"))
                anx = item;
            if (item.getName().equals("Irritability"))
                irr = item;
            if (item.getName().equals("Sleep"))
                sleep = item;
        }

        SimpleArrayMap<NumItem,Integer> numValues = new SimpleArrayMap<>();
        numValues.put(anx,e.getAnxValue());
        numValues.put(irr,e.getIrrValue());
        numValues.put(sleep,e.getHoursSleptValue());



        ArrayList<BoolItem> boolItems = BoolItemTableHelper.getAll(this);
        SimpleArrayMap<BoolItem,Boolean> boolValues = new SimpleArrayMap<>();
        for (BoolItem item : boolItems){
            if (e.getBoolItems().contains(item))
                boolValues.put(item,true);
            else
                boolValues.put(item,false);
        }


        return new FlexibleLogbookEntry(e.getDate(),e.getMoods(),numValues,boolValues);
    }
}
