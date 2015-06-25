package com.nmatte.mood.notifications;

import android.content.Context;

import com.nmatte.mood.medications.MedTableHelper;
import com.nmatte.mood.medications.Medication;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MedNotification {
    Calendar time;
    long intentID;
    ArrayList<Medication> medications;
    public final int timeID;

    MedNotification(Calendar time, ArrayList<Medication> medications){
        this.intentID = time.getTimeInMillis();
        this.medications = medications;
        this.timeID = timeAsInt();
        this.time = time;
    }

    MedNotification(int hour, int minute, ArrayList<Medication> medications){
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY,hour);
        time.set(Calendar.MINUTE,minute);
        this.intentID = time.getTimeInMillis();
        this.medications = medications;
        this.time = time;
        this.timeID = timeAsInt();
    }

    MedNotification(int timeID, long intentID, ArrayList<Medication> medications ){
        this.intentID = intentID;
        this.medications = medications;
        this.timeID = timeID;
        Calendar temp = Calendar.getInstance();
        temp.setTimeInMillis(intentID);
        this.time = temp;
    }


    public String calendarString (){
        String result = DateFormat
                .getTimeInstance(DateFormat.SHORT)
                .format(time.getTime());
        return result;
    }

    public static boolean equal(MedNotification lhs, MedNotification rhs){
        return (lhs.timeAsInt() == rhs.timeAsInt());
    }

    private int timeAsInt(){
        int hour = time.get(Calendar.HOUR_OF_DAY);
        int minute = time.get(Calendar.MINUTE);
        return hour * 100 + minute;
    }

    public static String medDisplayString(MedNotification notification, Context context) {
        ArrayList<Long> medicationIDs = new ArrayList<>();
        for (Medication m : notification.medications){
            medicationIDs.add(m.getID());
        }

        String result = "";
        for (String name : MedTableHelper.mapIDsToNames(medicationIDs,context)){
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
        for (String name : MedTableHelper.mapIDsToNames(medicationIDs,context)){
            result += name + "\n";
        }
        return result;

    }

    public String medIDString(){
        return Medication.IDString(medications);
    }
}
