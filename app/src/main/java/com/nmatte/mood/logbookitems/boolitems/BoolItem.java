package com.nmatte.mood.logbookitems.boolitems;

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

}
