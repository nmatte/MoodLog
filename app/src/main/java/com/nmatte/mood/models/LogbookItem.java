package com.nmatte.mood.models;

/**
 * LogbookItem is a field that the user can add to their logbook.
 * This abstract class covers the common aspects of BoolItems and NumItems.
 * They all have an id, name, and (optional) color.
 */
abstract public class LogbookItem {
    protected Long id;
    protected String name;
    protected int color = 0x000000;
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

    public Long getID() {
        if (this.id == null) {
            return (long) -1;
        }
        return id;
    }

    public String getName() {
        if (name == null) {
            name = "";
        }
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
