package com.nmatte.mood.notifications;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.moodlog.R;

import org.joda.time.DateTime;

import java.util.ArrayList;

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
        ArrayList<BoolItem> getMedList();
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
            medicationText.setText("replace me!");

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

    public void addNotification(int hour, int minute, ArrayList<BoolItem> m){
        notifications = listener.getMedReminderList();
        DateTime time = DateTime
                .now()
                .withHourOfDay(hour)
                .withMinuteOfHour(minute);
        MedNotification newNotification = new MedNotification(time,m);
        for (MedNotification oldNotification : notifications) {
            if (MedNotification.equal(oldNotification, newNotification)) {
                newNotification = merge(newNotification, oldNotification);
                listener.delete(oldNotification);
            }
        }
        listener.add(newNotification);


    }

    private MedNotification merge (MedNotification n1, MedNotification n2){
        ArrayList<BoolItem> resultMed = n1.boolItems;
        for(BoolItem med : n2.boolItems){
            if (!resultMed.contains(med))
                resultMed.add(med);
        }
        return new MedNotification(n1.time,resultMed);

    }


}
