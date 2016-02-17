package com.nmatte.mood.models.modules;

import android.content.Context;

import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.adapters.MoodModuleAdapter;

import java.util.ArrayList;

public class MoodModule extends Module {

    ArrayList<Boolean> fullValues;
    boolean isMini = false;

    public MoodModule(String values){
        this.fullValues = new ArrayList<>();
        for (String value : values.split(" ")){
            fullValues.add(value.equals("T"));
        }
    }

    public MoodModule(ArrayList<Boolean> fullValues){
        this.fullValues = fullValues;
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
}
