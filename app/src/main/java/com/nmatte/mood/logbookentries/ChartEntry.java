package com.nmatte.mood.logbookentries;

import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.numitems.NumItem;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class ChartEntry{

    public static final String DATE_PATTERN = "YYYYDDD";
    public static final DateTimeFormatter YEAR_DAY_FORMATTER = DateTimeFormat.forPattern(DATE_PATTERN),
    EDIT_ENTRY_FORMATTER = DateTimeFormat.shortDate();
    private final DateTime logDate;
    MoodModule moods;
    private SimpleArrayMap<NumItem,Integer> numItems;
    private SimpleArrayMap<BoolItem,Boolean> boolItems;
    private String note = "";


    public ChartEntry(DateTime logDate, ArrayList<Boolean> moods,
                      SimpleArrayMap<NumItem, Integer> numItems, SimpleArrayMap<BoolItem, Boolean> boolItems) {
        this.logDate = logDate;
        this.moods = new MoodModule(moods);
        this.numItems = numItems;
        this.boolItems = boolItems;
    }

    public ChartEntry(DateTime logDate){
        this(logDate, getEmptyMoods(),new SimpleArrayMap<NumItem,Integer>(),new SimpleArrayMap<BoolItem,Boolean>());
    }

    public static ArrayList<Boolean> parseMoodString (String moodString) {
        ArrayList<Boolean> result = new ArrayList<>();
        String[] boolStrings = moodString.split(" ");
        for (String mood : boolStrings) {
            if (!mood.equals("")) {
                result.add(mood.equals("T"));
            }
        }
        return result;
    }

    private static ArrayList<Boolean> getEmptyMoods() {
        ArrayList<Boolean> result = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            result.add(false);
        }
        return result;
    }

    /**
     * Gets the date as mm/dd or dd/mm depending on DateTime's format for the locale.
     * @return The short date string for the date.
     */
    public static String getMonthDayString(DateTime workingDate){
        String dateString = workingDate.toString(EDIT_ENTRY_FORMATTER);

        // We need mm to be different from yy so we can tell them apart.
        if (workingDate.toString("yy").equals(workingDate.toString("mm")) ) {
            workingDate = workingDate.minusYears(1);
        }
        if (workingDate.toString("yy").equals(workingDate.toString("dd"))) {
            workingDate = workingDate.minusYears(1);
        }

        // it's ok to mess with the date's year because we're not going to display it.
        ArrayList<String> nums = new ArrayList<>();
        String currentNum = "";

        for (char c : dateString.toCharArray()) {
            if (Character.isDigit(c)){
                currentNum += c;
            } else {
                if (!currentNum.equals("")){
                    if (!currentNum.equals(workingDate.toString("yy")) && !currentNum.equals(workingDate.toString("yyyy"))){
                        nums.add(currentNum);
                    }
                    currentNum = "";
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        if (nums.size() >= 2){
            builder
                    .append(nums.get(0))
                    .append("/")
                    .append(nums.get(1));
        }
        return builder.toString();

    }

    public String getNote(){
        return (note == null) ? "" : note ;
    }

    public void setNote(String note){
        this.note = note;
    }

    public MoodModule getMoods() {
        return moods;
    }

    public String getMoodString(){
        return moods.toString();
    }

    public DateTime getLogDate() {
        return logDate;
    }

    public int getDateInt() {
        return Integer.valueOf(logDate.toString(YEAR_DAY_FORMATTER));
    }


    public SimpleArrayMap<NumItem, Integer> getNumItems() {
        return numItems;
    }

    public SimpleArrayMap<BoolItem, Boolean> getBoolItems() {
        return boolItems;
    }
}
