package com.nmatte.mood.logbookitems;

public class LogbookItem {
    protected long id;
    protected String name;

    public LogbookItem(long id, String name){
        this.id = id;
        this.name = name;
    }

    public LogbookItem(String name){
        this.name = name;
        this.id = 0;
    }

    public LogbookItem(long id){
        this.id = id;
        this.name = null;
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
