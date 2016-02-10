package com.nmatte.mood.views.chart;


import com.nmatte.mood.models.ChartEntry;

public class OpenNoteEvent {
    ChartEntry entry;

    public OpenNoteEvent(ChartEntry entry){
        this.entry = entry;
    }

    public ChartEntry getEntry() {
        return entry;
    }
}
