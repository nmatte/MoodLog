package com.nmatte.mood.models;

import java.util.ArrayList;

public class MoodModule extends Module {

    ArrayList<Boolean> fullValues;

    public MoodModule(String values){
        this.fullValues = new ArrayList<>();
        for (String value : values.split(" ")){
            fullValues.add(value.equals("T"));
        }
    }

    public MoodModule(ArrayList<Boolean> fullValues){
        this.fullValues = fullValues;
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
}
