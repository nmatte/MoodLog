package com.nmatte.mood.logbookentries;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.logbookitems.LogbookItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.util.CalendarUtil;

import java.util.ArrayList;
import java.util.Calendar;

public class ChartEntry implements Parcelable{
    private Calendar date;
    private ArrayList<Boolean> moods;
    private SimpleArrayMap<NumItem,Integer> numItems;
    private SimpleArrayMap<BoolItem,Boolean> boolItems;
    private boolean isBlank = false;

    public static final String PARCEL_TAG = "LogbookParcel",
            DATE_TAG = "LogbookEntryDate",
    MOOD_TAG = "LogbookEntryMood",
    BOOL_TAG = "LogbookEntryBoolItems",
    NUM_TAG = "LogbookEntryNumItems";

    public ChartEntry(){
        this(Calendar.getInstance());
    }

    public ChartEntry(Calendar date){
        this(date,
            getEmptyMoods(),
            new SimpleArrayMap<NumItem,Integer>(),
            new SimpleArrayMap<BoolItem,Boolean>());
    }

    public ChartEntry(Calendar date, ArrayList<Boolean> moods,
                      SimpleArrayMap<NumItem, Integer> numItems, SimpleArrayMap<BoolItem, Boolean> boolItems) {
        this.date = date;
        this.moods = moods;
        this.numItems = numItems;
        this.boolItems = boolItems;
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
        return CalendarUtil.calendarToInt(date);
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public boolean isBlank() {
        return isBlank;
    }

    public ChartEntry setIsBlank(boolean isBlank) {
        this.isBlank = isBlank;
        return this;
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



    @Override
    public int describeContents() {
        return 0;
    }

    Parcelable.Creator<ChartEntry> CREATOR = new Creator<ChartEntry>() {
        @Override
        public ChartEntry createFromParcel(Parcel source) {
            return new ChartEntry(source);
        }

        @Override
        public ChartEntry[] newArray(int size) {
            return new ChartEntry[size];
        }
    };

    private ChartEntry(Parcel in){
        Calendar newDate = CalendarUtil.intToCalendar(in.readInt());
        moods = parseMoodString(in.readString());
        numItems = NumItem.mapFromStringArray(in.createStringArrayList());
        boolItems = BoolItem.mapFromStringArray(in.createStringArrayList());

    }

    public static ChartEntry getBlankEntry(Calendar date){
        return new ChartEntry(date).setIsBlank(true);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getDateInt());
        dest.writeString(getMoodString());
        dest.writeStringList(NumItem.mapToStringArray(numItems));
        dest.writeStringList(BoolItem.mapToStringArray(boolItems));
    }

    private static ArrayList<Boolean> getEmptyMoods(){
        ArrayList<Boolean> result = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            result.add(false);
        }
        return result;
    }
}