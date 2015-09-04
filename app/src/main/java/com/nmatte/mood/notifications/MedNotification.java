package com.nmatte.mood.notifications;

import com.nmatte.mood.logbookitems.boolitems.BoolItem;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.util.ArrayList;

public class MedNotification {
    DateTime time;
    long intentID;
    ArrayList<BoolItem> boolItems;
    public final int timeID;

    MedNotification(DateTime time, ArrayList<BoolItem> boolItems){
        this.intentID = time.getMillis();
        this.boolItems = boolItems;
        this.timeID = timeAsInt();
        this.time = time;
    }


    MedNotification(int timeID, long intentID, ArrayList<BoolItem> boolItems){
        this.intentID = intentID;
        this.boolItems = boolItems;
        this.timeID = timeID;
        this.time = new DateTime(intentID);
    }


    public String calendarString (){
        String result = DateFormat
                .getTimeInstance(DateFormat.SHORT)
                .format(time.toDate());
        return result;
    }

    public static boolean equal(MedNotification lhs, MedNotification rhs){
        return (lhs.timeAsInt() == rhs.timeAsInt());
    }

    private int timeAsInt(){
        int hour = time.hourOfDay().get();
        int minute = time.minuteOfDay().get();
        return hour * 100 + minute;
    }



    public String medIDString(){
        return "";
    }
}
