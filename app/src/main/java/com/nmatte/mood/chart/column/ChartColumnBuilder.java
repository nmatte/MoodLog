package com.nmatte.mood.chart.column;

import android.content.Context;

import com.nmatte.mood.logbookentries.ChartEntry;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.numitems.NumItem;

import java.util.ArrayList;

public class ChartColumnBuilder {
    ChartEntry newEntry;
    ArrayList<NumItem> numItems = new ArrayList<>();
    ArrayList<BoolItem> boolItems = new ArrayList<>();
    ChartColumn.Mode mode = ChartColumn.Mode.ENTRY_EDIT;
    Context context;

    public ChartColumnBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    public ChartColumnBuilder setNewEntry(ChartEntry newEntry) {
        this.newEntry = newEntry;
        return this;
    }

    public ChartColumnBuilder setNumItems(ArrayList<NumItem> numItems) {
        this.numItems = numItems;
        return this;
    }

    public ChartColumnBuilder setBoolItems(ArrayList<BoolItem> boolItems) {
        this.boolItems = boolItems;
        return this;
    }

    public ChartColumnBuilder setMode(ChartColumn.Mode mode) {
        this.mode = mode;
        return this;
    }



    public ChartColumn build(){
        if (newEntry == null){
            mode = ChartColumn.Mode.LABEL;
        }
        return new ChartColumn(
                context,
                newEntry,
                numItems,
                boolItems,
                mode);
    }
}
