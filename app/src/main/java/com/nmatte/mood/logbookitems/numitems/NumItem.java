package com.nmatte.mood.logbookitems.numitems;

import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.logbookitems.LogbookItem;

import java.util.ArrayList;

public class NumItem extends LogbookItem{
    private int maxNum;
    private int defaultNum;


    public NumItem(Long id, String name) {
        super(id, name);
    }

    public NumItem(Long id, String name, int maxNum, int defaultNum){
        super (id,name);
        this.maxNum = maxNum;
        this.defaultNum = defaultNum;
    }

    public NumItem(String name, int maxNum, int defaultNum){
        super(name);
        this.maxNum = maxNum;
        this.defaultNum = defaultNum;
    }

    private NumItem (String [] valStrings){
        super(Long.valueOf(valStrings[0]), valStrings[1]);
        maxNum = Integer.valueOf(valStrings[2]);
        defaultNum = Integer.valueOf(valStrings[3]);
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

    public static ArrayList<String> getColumnNames (ArrayList<NumItem> items){
        ArrayList<String> result = new ArrayList<>();
        for (NumItem item: items){
            result.add(item.getColumnName());
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

    @Override
    public String toString(){
        return name;
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
            if (rhs.getID() == null || getID() == null)
                return false;
            return this.getID().equals(rhs.getID());
        }
    }

    @Override
    public int hashCode() {
        long prime = 457;
        long hash = 1;
        hash = hash * (( getID() + prime));
        return (int) hash;
    }

    public String getColumnName() {
        return "N" + id.toString();
    }
}
