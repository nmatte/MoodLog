package com.nmatte.mood.views.chart;


import com.nmatte.mood.models.ChartEntry;

public class CloseNoteEvent {
    ChartEntry entry;

    public CloseNoteEvent (ChartEntry entry){
        this.entry = entry;
    }

    public ChartEntry getEntry() {
        return entry;
    }


}
