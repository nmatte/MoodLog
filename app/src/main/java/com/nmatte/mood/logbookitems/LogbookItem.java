package com.nmatte.mood.logbookitems;

public class LogbookItem {
    protected long id;
    protected String name;


    protected static String ARRAY_SEPARATOR = "\t";

    // the user probably won't name something using this...probably.
    protected static String FIELD_SEPARATOR = ";-;";
    protected static String MAP_TO = "-->";

    public LogbookItem(long id, String name){
        this.id = id;
        this.name = name;
    }

    public LogbookItem(String name){
        this(0,null);
    }

    public LogbookItem(long id){
        this(id,null);
    }

    public LogbookItem(){
        this(0,null);
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
