package com.nmatte.mood.notifications;

import com.nmatte.mood.medications.Medication;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

/**
 * Created by Nathan on 6/17/2015.
 */
public class MedNotification {
    Calendar time;
    ArrayList<Medication> medications;

    MedNotification(Calendar time, ArrayList<Medication> medications){
        this.time = time;
        this.medications = medications;
    }

    public String calendarString (){
        String result = DateFormat
                .getTimeInstance(DateFormat.SHORT)
                .format(time.getTime());
        return result;
    }

    public String medicationString (){
        String result = "";
        for (Medication m : medications){
            result += m.getName();
            if(medications.indexOf(m) < medications.size() - 1)
                result += "\n";
        }
        return result;
    }

    public boolean isAfter(Calendar otherTime){
        // determine if time of day is after the other time
        // should work properly instead of Calendar.compareTo() because
        // the dates might be different, when only time of day matters
        int otherHour = otherTime.get(Calendar.HOUR_OF_DAY);
        int hour = time.get(Calendar.HOUR_OF_DAY);

        boolean result = false;
        if (hour > otherHour)
            result = true;
        else if ((hour == otherHour) && (time.get(Calendar.MINUTE) > otherTime.get(Calendar.MINUTE)))
            result = true;

        return result;
    }

    private int timeAsInt(){
        int hour = time.get(Calendar.HOUR_OF_DAY);
        int minute = time.get(Calendar.MINUTE);

        return (hour * 100) + minute;
    }

    static final Comparator<MedNotification> TIME_ORDER = new Comparator<MedNotification>() {
        @Override
        public int compare(MedNotification lhs, MedNotification rhs) {
            return lhs.timeAsInt() - rhs.timeAsInt();
        }
    };

    public static boolean equal(MedNotification lhs, MedNotification rhs){
        return (lhs.timeAsInt() == rhs.timeAsInt());
    }
/*
    public static MedNotification [] mergeSort(MedNotification [] notifications){
        if (notifications.length < 2)
            return notifications;

        int midIndex = notifications.length/2;

        MedNotification [] leftSide = mergeSort(Arrays.copyOfRange(notifications,0,midIndex));
        MedNotification [] rightSide = mergeSort(Arrays.copyOfRange(notifications,midIndex,notifications.length));
        return merge(leftSide,rightSide);
    }

    private static MedNotification[] merge(MedNotification[] left, MedNotification[] right){
        MedNotification[] newNotifications = new MedNotification[left.length + right.length];
        int l = 0;
        int r = 0;

        for(int i = 0; i < newNotifications.length; i++){
            if (l < left.length && r >= right.length){
                newNotifications[i] = left[l];
                l++;
            } else if (r < right.length && l >= left.length){
                newNotifications[i] = right[r];
                r++;
            }

            if ((l < left.length) && (r < right.length)){
                if (left[l].timeAsInt() < right[r].timeAsInt()){
                    newNotifications[i] = left[l];
                    l++;
                } else {
                    newNotifications[i] = right[r];
                    r++;
                }
            }
        }

        return newNotifications;
    }
*/
}
