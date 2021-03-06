package com.nmatte.mood.models.components;

import android.content.ContentValues;

/**
 * LogbookComponent is a field that the user can add to their logbook.
 * This abstract class covers the common aspects of BoolComponents and NumComponents.
 * It has an id, name, and (optional) color, and belongs to a given module.
 */
abstract public class LogbookComponent {
    protected long id = -1;
    protected long moduleId = -1;
    protected String name;
    protected int color = 0x000000;
    protected boolean isVisible = true;

    public LogbookComponent(long id, long moduleId, String name, int color, boolean isVisible) {
        this.id         = id;
        this.moduleId   = moduleId;
        this.name       = name;
        this.color      = color;
        this.isVisible  = isVisible;
    }

    public LogbookComponent(long moduleId, String name, int color, boolean isVisible) {
        this.id         = -1;
        this.moduleId   = moduleId;
        this.name       = name;
        this.color      = color;
        this.isVisible  = isVisible;
    }

    public LogbookComponent(long id, String name){
        this.id     = id;
        this.name   = name;
    }

    public LogbookComponent(String name){
        this.name = name;
    }

    public abstract ContentValues asValues();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        // (id should be immutable unless it wasn't provided)
        if (this.id == -1) {
            this.id = id;
        }
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

    public long getModuleId() {
        return this.moduleId;
    }

    public void setModuleId(long newId) {
        this.moduleId = newId;
    }

    public String toString(){
        return prefix() + String.valueOf(getId()) + "_" + getName();
    }

    public String columnLabel(){
        return toString();
    }

    protected abstract String prefix();
}
