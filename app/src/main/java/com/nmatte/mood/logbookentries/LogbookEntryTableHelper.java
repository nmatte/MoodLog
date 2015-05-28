package com.nmatte.mood.logbookentries;

import java.util.ArrayList;

/**
 * Created by Nathan on 4/2/2015.
 */
public class LogbookEntryTableHelper {

    private static boolean [] parseMoodString (String moodString){
        boolean [] result = new boolean[13];

        for (int i = 0; i < result.length; i++){
            if(moodString != null && moodString.length() < 2){
                result[i] = false;
            } else {
                String nextInt = moodString.substring(0,2);
                int indexOfTrue = Integer.parseInt(nextInt);
                if (i == indexOfTrue){
                    result[i] = true;
                    moodString = moodString.substring(2);
                } else {
                    result[i] = false;
                }
            }
        }
        return result;
    }

    public static String makeMoodString(ArrayList<Boolean> checkedItems){
        String result = "";
        for (int i = 0; i < checkedItems.size(); i++){
            result += checkedItems.get(i)?
                    (i < 10)? "0" + String.valueOf(i)  : String.valueOf(i)
                    :
                    "";
        }
        return result;
    }
}
