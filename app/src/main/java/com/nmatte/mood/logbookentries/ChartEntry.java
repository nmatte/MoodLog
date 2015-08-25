package com.nmatte.mood.logbookentries;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.logbookitems.LogbookItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.numitems.NumItem;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class ChartEntry implements Parcelable{

    private final DateTime logDate;
    private ArrayList<Boolean> moods;
    private SimpleArrayMap<NumItem,Integer> numItems;
    private SimpleArrayMap<BoolItem,Boolean> boolItems;

    public static final String PARCEL_TAG = "LogbookParcel",
            DATE_TAG = "LogbookEntryDate",
    MOOD_TAG = "LogbookEntryMood",
    BOOL_TAG = "LogbookEntryBoolItems",
    NUM_TAG = "LogbookEntryNumItems";


    public ChartEntry(){
        this(DateTime.now());
    }

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


    // parcel constructor
    private ChartEntry(Parcel in){
        logDate = new DateTime(in.readLong());
        moods = parseMoodString(in.readString());
        numItems = NumItem.mapFromStringArray(in.createStringArrayList());
        boolItems = BoolItem.mapFromStringArray(in.createStringArrayList());

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



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(logDate.getMillis());
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

    public DateTime getLogDate() {
        return logDate;
    }

    public int getDateInt() {
        return Integer.valueOf(logDate.toString("YYYYDDD"));
    }
}
