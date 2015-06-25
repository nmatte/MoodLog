package com.nmatte.mood.notifications;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nmatte.mood.medications.Medication;
import com.nmatte.mood.moodlog.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Nathan on 6/16/2015.
 */
public class NotificationList extends LinearLayout {
    private Context context;
    private View footer;
    ArrayList<MedNotification> notifications;
    NotificationListListener listener;

    public interface NotificationListListener {
        void add(MedNotification notification);
        void newNotificationDialog();
        void delete(MedNotification notification);
        ArrayList<MedNotification> getMedReminderList();
        ArrayList<Medication> getMedList();
    }


    public NotificationList(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.setOrientation(VERTICAL);
        try {
            this.listener = (NotificationListListener) context;
        } catch (Exception e){
            Log.e("listener","notificationlist can't find listener",e);
        }
    }

    public void updateList(Context context){
        this.removeAllViews();
        MedNotificationTableHelper DBHelper = new MedNotificationTableHelper(context);
        notifications = listener.getMedReminderList();
        LayoutInflater inflater = LayoutInflater.from(context);
        for (int i = 0; i < notifications.size(); i++) {
            final MedNotification notification = notifications.get(i);
            View notificationListItem = inflater.inflate(R.layout.notification_list_item,null);

            TextView timeText = (TextView) notificationListItem.findViewById(R.id.timeText);
            TextView medicationText = (TextView) notificationListItem.findViewById(R.id.medicationText);

            timeText.setText(notification.calendarString());
            medicationText.setText(MedNotification.medDisplayString(notification, context));

            notificationListItem.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.delete(notification);
                    return true;
                }
            });

            this.addView(notificationListItem);
        }

        View footer = inflater.inflate(R.layout.notification_list_footer,null);
        footer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.newNotificationDialog();
            }
        });
        this.addView(footer);

    }

    public void addNotification(int hour, int minute, ArrayList<Medication> m){
        notifications = listener.getMedReminderList();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,minute);
        MedNotification newNotification = new MedNotification(c,m);
        for (MedNotification oldNotification : notifications) {
            if (MedNotification.equal(oldNotification, newNotification)) {
                newNotification = merge(newNotification, oldNotification);
                listener.delete(oldNotification);
            }
        }
        listener.add(newNotification);


    }

    private MedNotification merge (MedNotification n1, MedNotification n2){
        ArrayList<Medication> resultMed = n1.medications;
        for(Medication med : n2.medications){
            if (!resultMed.contains(med))
                resultMed.add(med);
        }
        Calendar resultCal = Calendar.getInstance();
        resultCal.set(Calendar.HOUR_OF_DAY,n1.time.get(Calendar.HOUR_OF_DAY));
        resultCal.set(Calendar.MINUTE,n1.time.get(Calendar.MINUTE));

        return new MedNotification(resultCal,resultMed);

    }

    private void addTestValues(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,13);
        c.set(Calendar.MINUTE, 30);
        ArrayList<Medication> meds = new ArrayList<>();
        meds.add(new Medication(1,"Foobar 200mg"));
        meds.add(new Medication(2,"BazBar 150mg"));
        notifications.add(new MedNotification(c,meds));
    }


}
