package com.nmatte.mood.models.components;

import android.database.Cursor;

import com.nmatte.mood.database.components.LogbookItemContract;

/**
 * These are one of the building blocks of the chart.
 * They represent checkboxes. They are also used in the MoodModule, for instance.
 */
public class BoolComponent extends LogbookComponent {

    public BoolComponent(long id, String name, long moduleId) {
        super(id, name, moduleId);
    }

    public BoolComponent(Long id, String name, int color) {
        this(id, name);
    }

    public BoolComponent(Long id, String name){
        super(id, name);
    }

    public BoolComponent(String name){
        super(name);
    }

    public BoolComponent(Cursor c) {
        super("");
        int idCol = c.getColumnIndex(LogbookItemContract.ID_COLUMN);
        id = c.getLong(idCol);
        int nameCol = c.getColumnIndex(LogbookItemContract.NAME_COLUMN);
        name = c.getString(nameCol);
        int modCol = c.getColumnIndex(LogbookItemContract.PARENT_MODULE_COLUMN);
        moduleId = c.getLong(modCol);
        color = c.getInt(c.getColumnIndex(LogbookItemContract.COLOR_COLUMN));
    }

    @Override
    protected String prefix() {
        return "B";
    }

    @Override
    public int hashCode() {
        long prime = 331;
        long hash = 1;
        hash = (hash * id) + prime;
        return (int) hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof BoolComponent))
            return false;
        else {
            BoolComponent rhs = (BoolComponent) o;
            return this.toString().equals(rhs.toString());
        }
    }
}
