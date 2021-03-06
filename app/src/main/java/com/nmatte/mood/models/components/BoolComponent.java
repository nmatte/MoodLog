package com.nmatte.mood.models.components;

import android.content.ContentValues;
import android.database.Cursor;

import com.nmatte.mood.database.components.ComponentContract;

/**
 * These are one of the building blocks of the chart.
 * They represent checkboxes. They are also used in the MoodModule, for instance.
 */
public class BoolComponent extends LogbookComponent {


    public BoolComponent(long id, long moduleId, String name, int color, boolean isVisible) {
        super(id, moduleId, name, color, isVisible);
    }

    public BoolComponent(long moduleId, String name, int color, boolean isVisible) {
        super(moduleId, name, color, isVisible);
    }

    public BoolComponent(String name){
        super(name);
    }

    public BoolComponent(Cursor cursor) {
        super("");
        this.name       = cursor.getString(cursor.getColumnIndex(ComponentContract.NAME_COLUMN));

        this.id         = cursor.getLong(cursor.getColumnIndex(ComponentContract.ID_COLUMN));
        this.moduleId   = cursor.getLong(cursor.getColumnIndex(ComponentContract.PARENT_MODULE_COLUMN));

        this.color      = cursor.getInt(cursor.getColumnIndex(ComponentContract.COLOR_COLUMN));
    }

    @Override
    public ContentValues asValues() {
        ContentValues values = new ContentValues();
        values.put(ComponentContract.NAME_COLUMN, this.getName());
        values.put(ComponentContract.COLOR_COLUMN, this.getColor());
        values.put(ComponentContract.PARENT_MODULE_COLUMN, this.getModuleId());

        if (id != -1) {
            values.put(ComponentContract.ID_COLUMN, this.getId());
        }

        return values;
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
            return (this.getId() == rhs.getId() && this.getName().equals(rhs.getName()));
        }
    }
}
