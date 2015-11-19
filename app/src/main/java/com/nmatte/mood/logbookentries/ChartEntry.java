package com.nmatte.mood.logbookentries;

import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.logbookitems.LogbookItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.numitems.NumItem;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class ChartEntry{

    private final DateTime logDate;
    private ArrayList<Boolean> moods;
    private SimpleArrayMap<NumItem,Integer> numItems;
    private SimpleArrayMap<BoolItem,Boolean> boolItems;
    private String note = "";

    public static final String DATE_PATTERN = "YYYYDDD";

    public static final DateTimeFormatter YEAR_DAY_FORMATTER = DateTimeFormat.forPattern(DATE_PATTERN),
    EDIT_ENTRY_FORMATTER = DateTimeFormat.shortDate();


    public ChartEntry(DateTime logDate){
        this(logDate, getEmptyMoods(),new SimpleArrayMap<NumItem,Integer>(),new SimpleArrayMap<BoolItem,Boolean>());
    }

    public ChartEntry(DateTime logDate, ArrayList<Boolean> moods,
                      SimpleArrayMap<NumItem, Integer> numItems, SimpleArrayMap<BoolItem, Boolean> boolItems) {
        this.logDate = logDate;
        this.moods = moods;
        this.numItems = numItems;
        this.boolItems = boolItems;
    }

    public String getNote(){
        return (note == null) ? "" : note ;
    }

    public void setNote(String note){
        this.note = note;
    }



    public SimpleArrayMap<BoolItem, Boolean> getBoolItems() {
        return boolItems;
    }

    public void setBoolItems(SimpleArrayMap<BoolItem, Boolean> boolItems) {
        this.boolItems = boolItems;
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

    public static ArrayList<Boolean> parseMoodString (String moodString){
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


    public String getNumMapString(){
        return LogbookItem.combineStringArray(NumItem.mapToStringArray(numItems));
    }

    public String getBoolMapString(){
        return LogbookItem.combineStringArray(BoolItem.mapToStringArray(boolItems));
    }


    private static ArrayList<Boolean> getEmptyMoods(){
        ArrayList<Boolean> result = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            result.add(false);
        }
        return result;
    }

    public DateTime getLogDate() {
        return logDate;
    }

    public int getDateInt() {
        return Integer.valueOf(logDate.toString(YEAR_DAY_FORMATTER));
    }
}
