package com.nmatte.mood.logbookitems;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;

abstract public class LogbookItem {
    protected final Long id;
    protected String name;


    protected static String ARRAY_SEPARATOR = "\t";

    // the user probably won't name something using this...probably.
    protected static String FIELD_SEPARATOR = ";-;";
    protected static String MAP_TO = "-->";

    public LogbookItem(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public LogbookItem(String name){
        this(null,name);
    }

    public LogbookItem(){
        this(null,null);
    }

    @Nullable
    public Long getID() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    abstract public String getColumnName();

    public static String combineStringArray(ArrayList<String> strings){
        return TextUtils.join(ARRAY_SEPARATOR,strings);
    }

    public static ArrayList<String> extractStringArray(String string){
        return new ArrayList<>(Arrays.asList(string.split(ARRAY_SEPARATOR)));
    }


}
