package com.nmatte.mood.logbookitems.boolitems;

import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.logbookitems.LogbookItem;

import java.util.ArrayList;

public class BoolItem extends LogbookItem {


    public BoolItem(Long id, String name){
        super(id,name);
    }

    public BoolItem(String name){
        super(name);
    }

    public BoolItem(){
        super();
    }


    public static SimpleArrayMap<BoolItem,Boolean>
        refreshMap(ArrayList<BoolItem> newItems, SimpleArrayMap<BoolItem,Boolean> oldMap){
        SimpleArrayMap<BoolItem,Boolean> result = new SimpleArrayMap<>();

        for (BoolItem newItem : newItems){
            if (oldMap.containsKey(newItem))
                result.put(newItem,oldMap.get(newItem));
            else
                result.put(newItem,false);
        }

        return result;
    }


    public static ArrayList<String> mapToStringArray(SimpleArrayMap<BoolItem,Boolean> map){
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < map.size(); i++){
            String keyString = map.keyAt(i).toString();
            String valString = map.valueAt(i) ? "T" : "F";

            result.add(keyString + MAP_TO + valString);
        }
        return result;
    }

    public static SimpleArrayMap<BoolItem,Boolean> mapFromStringArray(ArrayList<String> mapStrings){
        SimpleArrayMap<BoolItem,Boolean> result = new SimpleArrayMap<>();

        for (String pair : mapStrings){
            String [] keyAndVal = pair.split(MAP_TO);
            BoolItem key = new BoolItem(keyAndVal[0].split(FIELD_SEPARATOR));
            boolean val = keyAndVal[1].equals("T");
            result.put(key,val);
        }

        return result;
    }

    private BoolItem (String [] valStrings){
        super(Long.valueOf(valStrings[0]), valStrings[1]);
    }

    public static BoolItem fromString(String itemString){
        return new BoolItem(itemString.split(FIELD_SEPARATOR));
    }


    @Override
    public String toString(){
        return String.valueOf(id) + FIELD_SEPARATOR + name;
    }

    public String getColumnName(){
        return "B" + id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof  BoolItem))
            return false;
        else {
            BoolItem rhs = (BoolItem) o;
            if (rhs.getID() == null || getID() == null)
                return false;
            return this.getID().equals(rhs.getID());
        }
    }

    @Override
    public int hashCode() {
        long prime = 331;
        long hash = 1;
        hash = hash * ((id == null? 0 : id) + prime);
        return (int) hash;
    }
}
