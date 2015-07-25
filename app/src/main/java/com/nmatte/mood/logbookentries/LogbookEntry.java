package com.nmatte.mood.logbookentries;


import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.util.CalendarDatabaseUtil;

import java.util.ArrayList;
import java.util.Calendar;

public class LogbookEntry {

    ArrayList<Boolean> moods;
    int irrValue = 0,
        anxValue = 0,
        hoursSleptValue = 0;
    Calendar date;
    boolean blank = false;
    ArrayList<BoolItem> boolItems;

    public static final String DATE_TAG = "LogbookEntryDate",
            MOOD_TAG = "LogbookEntryMood",
            IRR_TAG = "LogbookEntryIrr",
            ANX_TAG = "LogbookEntryAnx",
            SLEEP_TAG = "LogbookEntrySleep",
            MED_TAG = "LogbookEntryMeds";

    public LogbookEntry (){
        this.moods = new ArrayList<Boolean>();
        this.boolItems = new ArrayList<>();
        this.date = Calendar.getInstance();
    }

    public LogbookEntry (Calendar date){
        this.moods = new ArrayList<Boolean>();
        this.boolItems = new ArrayList<>();
        this.date = date;
    }

    public LogbookEntry(Calendar date, String moodString, int irr, int anx, int sleep,  String medString){
        this.moods = parseMoodString(moodString);
        this.irrValue = irr;
        this.anxValue = anx;
        this.hoursSleptValue = sleep;
        this.date = date;
        this.boolItems = BoolItem.parseIDString(medString);
    }

    public LogbookEntry(int dateInt, String moodString, int irr, int anx, int sleep,  String medString){
        this.moods = parseMoodString(moodString);
        this.irrValue = irr;
        this.anxValue = anx;
        this.hoursSleptValue = sleep;
        Calendar dateCal = Calendar.getInstance();
        dateCal.set(Calendar.YEAR, dateInt / 1000);
        dateCal.set(Calendar.DAY_OF_YEAR, dateInt % 1000);
        this.date = dateCal;
        this.boolItems = BoolItem.parseIDString(medString);

    }




    public Calendar getDate() {
        return date;
    }

    public int getDateInt(){
        return CalendarDatabaseUtil.calendarToInt(date);
    }

    public void setDate(Calendar date) {
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

    public String moodString(){
        String result = "";
        for (int i = 0; i < moods.size(); i++){
            result += moods.get(i) ? "T " : "F ";
        }
        return result;
    }

    public String medicationString() {

        return BoolItem.IDString(boolItems);
    }

    public boolean isBlank(){
        return blank;
    }



    private static ArrayList<Boolean> parseMoodString (String moodString){
        ArrayList<Boolean> result = new ArrayList<>();
        String [] boolStrings = moodString.split(" ");
        for (String mood: boolStrings){
            if (!mood.equals("")) {
                result.add(mood.equals("T"));
            }
        }
        return result;
    }

    public void setBoolItems(ArrayList boolItems) {
        this.boolItems = boolItems;
    }

    public ArrayList<BoolItem> getBoolItems() {
        return boolItems;
    }

}
