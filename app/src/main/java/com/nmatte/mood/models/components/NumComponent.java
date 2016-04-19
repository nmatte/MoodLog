package com.nmatte.mood.models.components;

import android.content.ContentValues;
import android.database.Cursor;

import com.nmatte.mood.database.components.ComponentContract;

/**
 * These are one of the building blocks of the chart.
 * They represent number pickers.
 */
public class NumComponent extends LogbookComponent {
    private int maxNum;
    private int defaultNum;

    public NumComponent(Long id, String name) {
        super(id, name);
    }

    public NumComponent(String name){
        super(name);
    }

    public NumComponent(Long id, String name, int maxNum, int defaultNum){
        super (id,name);
        this.maxNum = maxNum;
        this.defaultNum = defaultNum;
    }

    public NumComponent(String name, int maxNum, int defaultNum){
        super(name);
        this.maxNum = maxNum;
        this.defaultNum = defaultNum;
    }

    public NumComponent(Cursor cursor) {
        super("");
        this.name       = cursor.getString(cursor.getColumnIndex(ComponentContract.NAME_COLUMN));

        this.id         = cursor.getLong(cursor.getColumnIndex(ComponentContract.ID_COLUMN));
        this.moduleId   = cursor.getLong(cursor.getColumnIndex(ComponentContract.PARENT_MODULE_COLUMN));

        this.color      = cursor.getInt(cursor.getColumnIndex(ComponentContract.COLOR_COLUMN));
        this.maxNum     = cursor.getInt(cursor.getColumnIndex(ComponentContract.Num.ITEM_MAX_COLUMN));
        this.defaultNum = cursor.getInt(cursor.getColumnIndex(ComponentContract.Num.ITEM_DEFAULT_COLUMN));
    }

    @Override
    public ContentValues asValues() {
        ContentValues values = new ContentValues();
        values.put(ComponentContract.NAME_COLUMN, this.getName());
        values.put(ComponentContract.COLOR_COLUMN, this.getColor());
        values.put(ComponentContract.PARENT_MODULE_COLUMN, this.getModuleId());
        values.put(ComponentContract.Num.ITEM_DEFAULT_COLUMN, this.getDefaultNum());
        values.put(ComponentContract.Num.ITEM_MAX_COLUMN, this.getMaxNum());

        if (id != -1) {
            values.put(ComponentContract.ID_COLUMN, this.getId());
        }

        return values;
    }

    public int getDefaultNum() {
        return defaultNum;
    }

    public void setDefaultNum(int defaultNum) {
        this.defaultNum = defaultNum;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof NumComponent))
            return false;
        else {
            NumComponent rhs = (NumComponent) o;
            return this.toString().equals(rhs.toString());
        }
    }

    @Override
    public int hashCode() {
        long prime = 457;
        long hash = 1;
        hash = hash * (( getId() + prime));
        return (int) hash;
    }

    @Override
    protected String prefix() {
        return "N";
    }
}
