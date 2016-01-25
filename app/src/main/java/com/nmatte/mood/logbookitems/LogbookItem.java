package com.nmatte.mood.logbookitems;

import android.support.annotation.Nullable;

/*
A LogbookItem is a field that the user can add to their logbook. So far only NumItems and BoolItems
are available--user can track an integer value or a boolean value.
 */
abstract public class LogbookItem {
    protected Long id;

    protected String name;
    protected boolean isVisible;


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

    public void setId(Long id) {
        if (this.id == null) {
            this.id = id;
        }
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

    public boolean isVisible(){
        return this.isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}
