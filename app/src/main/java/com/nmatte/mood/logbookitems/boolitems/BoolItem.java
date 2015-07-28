package com.nmatte.mood.logbookitems.boolitems;

import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.logbookitems.LogbookItem;

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

    public static String IDString (ArrayList<BoolItem> boolItems){
        String result = "";
        for (BoolItem item : boolItems){
            String idString = String.valueOf(item.getID());
            result += idString + " ";
        }
        return result;
    }

    public static String dataToString(SimpleArrayMap<BoolItem,Boolean> values){
        String result = "";
        // intended format: "1:T 13:F 14:T" etc
        for (int i = 0; i < values.size(); i++){
            BoolItem item = values.keyAt(i);
            result += item.getID() + ":";
            result +=  values.get(item) ? "T" : "F";
            result += " ";
        }
        return result;
    }

    public static SimpleArrayMap<BoolItem,Boolean> dataFromString(String values){
        SimpleArrayMap<BoolItem,Boolean> result = new SimpleArrayMap<>();

        // convert from format "1:3 13:2 14:30"
        for(String keyValString : values.split(" ")){
            // each string should be "1:3" "13:2" etc
            String[] keyValArray = keyValString.split(":");
            if (keyValArray.length == 2){
                Boolean itemVal = keyValArray[1].equals("T");
                result.put(new BoolItem(Integer.valueOf(keyValArray[0])),itemVal);
            }
        }
        return result;
    }

    public static ArrayList<BoolItem> parseIDString(String itemString){
        ArrayList<BoolItem> result = new ArrayList<>();
        for (String item : itemString.split(" ")){
            if (!item.equals("")) {
                long id = Integer.valueOf(item);
                result.add(new BoolItem(id));
            }
        }
        return result;
    }

    public static SimpleArrayMap<BoolItem,Boolean> mapToList(ArrayList<BoolItem> currentItems, SimpleArrayMap<BoolItem,Boolean> oldMap){
        SimpleArrayMap<BoolItem,Boolean> result = new SimpleArrayMap<>();
        for (BoolItem item : currentItems){
            if (oldMap.containsKey(item)){
                result.put(item,oldMap.get(item));
            } else {
                result.put(item, false);
            }
        }
        return result;
    }

    public boolean equals(BoolItem other){
        return (id == other.getID());
    }

}
