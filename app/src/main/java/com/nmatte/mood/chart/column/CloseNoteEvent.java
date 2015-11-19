package com.nmatte.mood.chart.column;


import com.nmatte.mood.logbookentries.ChartEntry;

public class CloseNoteEvent {
    public ChartEntry getEntry() {
        return entry;
    }

    ChartEntry entry;

    public CloseNoteEvent (ChartEntry entry){
        this.entry = entry;
    }


}
