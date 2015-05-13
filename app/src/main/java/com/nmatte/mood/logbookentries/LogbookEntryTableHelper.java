package com.nmatte.mood.logbookentries;

/**
 * Created by Nathan on 4/2/2015.
 */
public class LogbookEntryTableHelper {

    private boolean [] parseMoodString (String moodString){
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
}
