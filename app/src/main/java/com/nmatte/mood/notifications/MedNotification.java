package com.nmatte.mood.notifications;

import android.content.Context;

import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;

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

    public static String medDisplayString(MedNotification notification, Context context) {
        ArrayList<Long> medicationIDs = new ArrayList<>();
        for (BoolItem m : notification.boolItems){
            medicationIDs.add(m.getID());
        }

        String result = "";
        for (String name : BoolItemTableHelper.mapIDsToNames(medicationIDs, context)){
            result += name + "\n";
        }
        return result;
    }

    public static String medDisplayString(String idString, Context context){
        String [] idStrings = idString.split(" ");
        ArrayList<Long> medicationIDs = new ArrayList<>();

        for (String id : idStrings){
            long idNumber = Long.valueOf(id);
            medicationIDs.add(idNumber);
        }
        String result = "";
        for (String name : BoolItemTableHelper.mapIDsToNames(medicationIDs, context)){
            result += name + "\n";
        }
        return result;

    }

    public String medIDString(){
        return "";
    }
}
