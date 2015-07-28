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

    public static SimpleArrayMap<BoolItem,Boolean> dataFromString(String values, ArrayList<BoolItem> boolItems){
        return mapToNew(boolItems,extractValues(values));
    }

    public static SimpleArrayMap<Long,Boolean> extractValues(String values){
        SimpleArrayMap<Long,Boolean> result = new SimpleArrayMap<>();

        // convert from format "1:3 13:2 14:30"
        for(String keyValString : values.split(" ")){
            // each string should be "1:3" "13:2" etc
            String[] keyValArray = keyValString.split(":");
            if (keyValArray.length == 2){
                Boolean itemVal = keyValArray[1].equals("T");
                result.put(Long.valueOf(keyValArray[0]),itemVal);
            }
        }
        return result;
    }


    public static SimpleArrayMap<BoolItem,Boolean> mapToNew(ArrayList<BoolItem> newItems, SimpleArrayMap<Long,Boolean> idMap){
        SimpleArrayMap<BoolItem,Boolean> result = new SimpleArrayMap<>();
        for (BoolItem item : newItems){
            if (idMap.containsKey(item.getID())){
                result.put(item,idMap.get(item.getID()));
            } else {
                result.put(item,false);
            }
        }
        return result;
    }

}
