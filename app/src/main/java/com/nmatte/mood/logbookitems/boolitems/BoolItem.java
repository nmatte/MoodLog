package com.nmatte.mood.logbookitems.boolitems;

import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;

import com.nmatte.mood.logbookitems.LogbookItem;
import com.nmatte.mood.logbookitems.numitems.NumItem;

import java.util.ArrayList;

public class BoolItem extends LogbookItem {


    public BoolItem(long id, String name){
        super(id,name);
    }

    public BoolItem(String name){
        super(name);
    }

    public BoolItem(long id){
        super(id);
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


    @Override
    public String toString(){
        return String.valueOf(id) + FIELD_SEPARATOR + name;
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
            return this.getID() == rhs.getID();
        }
    }

    @Override
    public int hashCode() {
        int prime = 331;
        int hash = 1;
        hash = hash * ((int) getID() + prime);
        return hash;
    }
}
