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

    public NumItem(String itemString) {
        String [] valStrings = itemString.split(FIELD_SEPARATOR);
        id = Long.valueOf(valStrings[0]);
        name = valStrings[1];
        maxNum = Integer.valueOf(valStrings[2]);
        defaultNum = Integer.valueOf(valStrings[3]);
    }

    public NumItem(long id, String name, int maxNum, int defaultNum){
        super (id,name);
        this.maxNum = maxNum;
        this.defaultNum = defaultNum;
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


    public static SimpleArrayMap<NumItem,Integer>
        refreshMap(ArrayList<NumItem> newItems, SimpleArrayMap<NumItem,Integer> oldMap){
        SimpleArrayMap<NumItem,Integer> result = new SimpleArrayMap<>();

        for (NumItem newItem : newItems){
            if (oldMap.containsKey(newItem))
                result.put(newItem,oldMap.get(newItem));
            else
                result.put(newItem,0);
        }
        return result;
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

    @Override
    public String toString(){
        return String.valueOf(id) + FIELD_SEPARATOR +
                name + FIELD_SEPARATOR +
                maxNum + FIELD_SEPARATOR +
                defaultNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof  NumItem))
            return false;
        else {
            NumItem rhs = (NumItem) o;
            return this.getID() == rhs.getID();
        }
    }

    @Override
    public int hashCode() {
        int prime = 457;
        int hash = 1;
        hash = hash * ((int) getID() + prime);
        return hash;
    }
}
