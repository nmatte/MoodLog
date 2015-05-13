package com.nmatte.mood.logbookentries;


import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.medications.Medication;

import java.util.ArrayList;

public class LogbookEntry {

    ArrayList<Boolean> moods;
    int date = 0,
        irrValue = 0,
        anxValue = 0,
        hoursSleptValue = 0;
    SimpleArrayMap<Medication,Boolean> medications;

    public LogbookEntry (){
        moods = new ArrayList<Boolean>();
        medications = new SimpleArrayMap<>();
    }

    public String getSummaryString(){
        String summary = date + "\n" +
                         irrValue + "\n" +
                         anxValue + "\n" +
                         hoursSleptValue;

        return summary;

    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getIrrValue() {
        return irrValue;
    }

    public void setIrrValue(int irrValue) {
        this.irrValue = irrValue;
    }

    public int getAnxValue() {
        return anxValue;
    }

    public void setAnxValue(int anxValue) {
        this.anxValue = anxValue;
    }

    public int getHoursSleptValue() {
        return hoursSleptValue;
    }

    public void setHoursSleptValue(int hoursSleptValue) {
        this.hoursSleptValue = hoursSleptValue;
    }

    public ArrayList<Boolean> getMoods() {
        return moods;
    }

    public void setMoods(ArrayList<Boolean> moods) {
        this.moods = moods;
    }

    public SimpleArrayMap<Medication, Boolean> getMedications() {
        return medications;
    }

    public void setMedications(SimpleArrayMap<Medication, Boolean> medications) {
        this.medications = medications;
    }
}
