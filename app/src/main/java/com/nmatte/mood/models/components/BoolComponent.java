package com.nmatte.mood.models.components;

import android.database.Cursor;

import com.nmatte.mood.database.components.ComponentContract;

/**
 * These are one of the building blocks of the chart.
 * They represent checkboxes. They are also used in the MoodModule, for instance.
 */
public class BoolComponent extends LogbookComponent {
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
