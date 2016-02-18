package com.nmatte.mood.models.modules;

import android.content.Context;

import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.adapters.MoodModuleAdapter;

import java.util.ArrayList;

public class MoodModule extends Module {

    ArrayList<Boolean> fullValues;
    boolean isMini = false;

    public MoodModule(ArrayList<Boolean> fullValues){
        this.fullValues = fullValues;
    }

    public static ArrayList<Boolean> parseMoodString (String moodString) {
        ArrayList<Boolean> result = new ArrayList<>();
        String[] boolStrings = moodString.split(" ");
        for (String mood : boolStrings) {
            if (!mood.equals("")) {
                result.add(mood.equals("T"));
            }
        }
        return result;
    }

    private static ArrayList<Boolean> getEmptyMoods() {
        ArrayList<Boolean> result = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            result.add(false);
        }
        return result;
    }

    public boolean isMini() {
        return isMini;
    }

    public void setIsMini(boolean isMini) {
        this.isMini = isMini;
    }

    public void set(int index, boolean value){
        fullValues.set(index,value);
    }

    public boolean get(int index) {
        return fullValues.get(index);
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (Boolean value : fullValues) {
            builder
                    .append(value ? "T" : "F")
                    .append(" ");
        }
        return builder.toString();
    }

    @Override
    public ModuleAdapter getAdapter(Context context) {
        return new MoodModuleAdapter(context, this);
    }

    @Override
    public String moduleName() {
        return "MOOD_MODULE";
    }
}
