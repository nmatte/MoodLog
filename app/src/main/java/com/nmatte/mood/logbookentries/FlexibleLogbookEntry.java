package com.nmatte.mood.logbookentries;

import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.util.CalendarDatabaseUtil;

import java.util.ArrayList;
import java.util.Calendar;

public class FlexibleLogbookEntry {
    private Calendar date;
    private boolean isBlank = false;
    private ArrayList<Boolean> moods;
    private SimpleArrayMap<NumItem,Integer> numItems;
    private SimpleArrayMap<BoolItem,Boolean> boolItems;

    public static final String DATE_TAG = "LogbookEntryDate",
    MOOD_TAG = "LogbookEntryMood",
    BOOL_TAG = "LogbookEntryBoolItems",
    NUM_TAG = "LogbookEntryNumItems";

    public FlexibleLogbookEntry(){
        this(Calendar.getInstance());
    }

    public FlexibleLogbookEntry(Calendar date){
        this(date,
            new ArrayList<Boolean>(),
            new SimpleArrayMap<NumItem,Integer>(),
            new SimpleArrayMap<BoolItem,Boolean>());
    }

    public FlexibleLogbookEntry(Calendar date, ArrayList<Boolean> moods,
                    SimpleArrayMap<NumItem,Integer> numItems, SimpleArrayMap<BoolItem,Boolean> boolItems) {
        this.date = date;
        this.moods = moods;
        this.numItems = numItems;
        this.boolItems = boolItems;
    }

    public FlexibleLogbookEntry(Calendar date, String moodString, String boolItemString, String numItemString){
        this.date = date;
        this.moods = parseMoodString(moodString);
        this.boolItems = BoolItem.dataFromString(boolItemString);
        this.numItems = NumItem.dataFromString(numItemString);
    }

    public FlexibleLogbookEntry(int date, String moodString, String boolItemString, String numItemString){
        this(CalendarDatabaseUtil.intToCalendar(date),moodString,boolItemString,numItemString);
    }

    public SimpleArrayMap<BoolItem, Boolean> getBoolItems() {
        return boolItems;
    }

    public void setBoolItems(SimpleArrayMap<BoolItem, Boolean> boolItems) {
        this.boolItems = boolItems;
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

    public boolean isBlank() {
        return isBlank;
    }

    public void setIsBlank(boolean isBlank) {
        this.isBlank = isBlank;
    }

    public ArrayList<Boolean> getMoods() {
        return moods;
    }

    public void setMoods(ArrayList<Boolean> moods) {
        this.moods = moods;
    }

    public SimpleArrayMap<NumItem, Integer> getNumItems() {
        return numItems;
    }

    public void setNumItems(SimpleArrayMap<NumItem, Integer> numItems) {
        this.numItems = numItems;
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

    public String getMoodString(){
        String result = "";
        for (int i = 0; i < moods.size(); i++){
            result += moods.get(i) ? "T " : "F ";
        }
        return result;
    }

    public String getBoolString(){
        return BoolItem.dataToString(boolItems);
    }

    public String getNumString(){
        return NumItem.dataToString(numItems);
    }

}
