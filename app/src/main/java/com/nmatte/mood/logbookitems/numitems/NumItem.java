package com.nmatte.mood.logbookitems.numitems;

import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.logbookitems.LogbookItem;

public class NumItem extends LogbookItem{
    private int defaultNum;
    private int maxNum;

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

    public static String itemDataString(SimpleArrayMap<NumItem,Integer> values){
        String result = "";
        // intended format: "1:3 13:2 14:30" etc
        for (int i = 0; i < values.size(); i++){
            NumItem item = values.keyAt(i);
            result += item.getID() + ":";
            result += values.get(item) + " ";
        }
        return result;
    }

    public static SimpleArrayMap<NumItem,Integer> dataFromString(String values){
        SimpleArrayMap<NumItem,Integer> result = new SimpleArrayMap<>();

        // convert from format "1:3 13:2 14:30"
        for(String keyValString : values.split(" ")){
            // each string should be "1:3" "13:2" etc
            String[] keyValArray = keyValString.split(":");
                if (keyValArray.length == 2){
                    result.put(new NumItem(Integer.valueOf(keyValArray[0])),Integer.valueOf(keyValArray[1]));
                }
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
}
