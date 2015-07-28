package com.nmatte.mood.logbookitems.numitems;

import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.logbookitems.LogbookItem;

import java.util.ArrayList;

public class NumItem extends LogbookItem{
    private int maxNum;
    private int defaultNum;

    public NumItem(long id) {
        super(id);
    }

    public NumItem(long id, String name) {
        super(id, name);
    }

    public NumItem(String name) {
        super(name);
    }

    public NumItem(long id, String name, int maxNum, int defaultNum){
        super (id,name);
        this.maxNum = maxNum;
        this.defaultNum = defaultNum;
    }

    public static String dataToString(SimpleArrayMap<NumItem,Integer> values){
        String result = "";
        // intended format: "1:3 13:2 14:30" etc
        for (int i = 0; i < values.size(); i++){
            NumItem item = values.keyAt(i);
            result += item.getID() + ":";
            result += values.get(item) + " ";
        }
        return result;
    }

    public static SimpleArrayMap<NumItem,Integer> dataFromString(ArrayList<NumItem> numItems, String values){
        return mapToNew(numItems, extractValues(values));
    }

    public static SimpleArrayMap<Long,Integer> extractValues(String values){
        SimpleArrayMap<Long,Integer> result = new SimpleArrayMap<>();

        // convert from format "1:3 13:2 14:30"
        for(String keyValString : values.split(" ")){
            // each string should be "1:3" "13:2" etc
            String[] keyValArray = keyValString.split(":");
            if (keyValArray.length == 2){
                result.put(Long.valueOf(keyValArray[0]),Integer.valueOf(keyValArray[1]));
            }
        }
        return result;
    }

    public static SimpleArrayMap<NumItem,Integer> mapToNew (ArrayList<NumItem> newItems, SimpleArrayMap<Long,Integer> idMap){
        SimpleArrayMap<NumItem,Integer> result = new SimpleArrayMap<>();
        for (NumItem item : newItems){
            if(idMap.containsKey(item.getID()))
                result.put(item,idMap.get(item.getID()));
            else
                result.put(item,0);
        }
        return result;
    }

    public int getDefaultNum() {
        return defaultNum;
    }

    public void setDefaultNum(int defaultNum) {
        this.defaultNum = defaultNum;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public String toString(){
        return String.valueOf(id) + FIELD_SEPARATOR +
                name + FIELD_SEPARATOR +
                maxNum + FIELD_SEPARATOR +
                defaultNum;
    }

    public static ArrayList<String> mapToStringArray(SimpleArrayMap<NumItem,Integer> map){
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < map.size(); i++){
            String keyString = map.keyAt(i).toString();
            String valString = String.valueOf(map.valueAt(i));

            result.add(keyString + MAP_TO + valString);
        }
        return result;
    }

    public static SimpleArrayMap<NumItem,Integer> mapFromStringArray(ArrayList<String> mapStrings){
        SimpleArrayMap<NumItem,Integer> result = new SimpleArrayMap<>();

        for (String pair : mapStrings){
            String [] keyAndVal = pair.split(MAP_TO);
            NumItem key = new NumItem(keyAndVal[0].split(FIELD_SEPARATOR));
            int val = Integer.valueOf(keyAndVal[1]);
            result.put(key,val);
        }

        return result;
    }

    private NumItem (String [] valStrings){
        super(Long.valueOf(valStrings[0]), valStrings[1]);
        maxNum = Integer.valueOf(valStrings[2]);
        defaultNum = Integer.valueOf(valStrings[3]);
    }
}
