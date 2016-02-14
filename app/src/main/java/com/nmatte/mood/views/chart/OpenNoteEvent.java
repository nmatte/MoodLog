package com.nmatte.mood.views.chart;


import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.NoteModule;

public class OpenNoteEvent {
    ChartEntry entry;

    public OpenNoteEvent(NoteModule entry){
        this.entry = entry;
    }

    public ChartEntry getEntry() {
        return entry;
    }
}
