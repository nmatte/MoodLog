package com.nmatte.mood.models;

import android.support.annotation.Nullable;

/*
A LogbookItem is a field that the user can add to their logbook. So far only NumItems and BoolItems
are available--user can track an integer value or a boolean value.
 */
abstract public class LogbookItem {
    protected Long id;
    protected String name;
    protected int color;
    protected boolean isVisible = true;

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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isVisible(){
        return this.isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public String toString(){
        return prefix() + id.toString() + ":" + getName();
    }

    public String columnLabel(){
        return prefix() + id.toString();
    }

    protected abstract String prefix();
}
