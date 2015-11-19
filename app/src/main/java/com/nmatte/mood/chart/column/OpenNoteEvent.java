package com.nmatte.mood.chart.column;


import com.nmatte.mood.logbookentries.ChartEntry;

public class OpenNoteEvent {
    ChartEntry entry;

    public OpenNoteEvent(ChartEntry entry){
        this.entry = entry;
    }

    public ChartEntry getEntry() {
        return entry;
    }
}
