package com.nmatte.mood.logbookentries;


import java.util.HashMap;
import java.util.Map;

public class LogbookEntry {

    String moodString = "";
    int date = 0;
    int irrValue = 0;
    int anxValue = 0;
    int hoursSleptValue = 0;
    HashMap<Integer,Boolean> medications;

    public LogbookEntry (){
        medications = new HashMap<Integer, Boolean>();

    }

    public String getSummaryString(){
        String summary = moodString + "\n" +
                         date + "\n" +
                         irrValue + "\n" +
                         anxValue + "\n" +
                         hoursSleptValue;

        return summary;

    }

    public String getMoodString() {
        return moodString;
    }

    public void setMoodString(String moodString) {
        this.moodString = moodString;
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


}
