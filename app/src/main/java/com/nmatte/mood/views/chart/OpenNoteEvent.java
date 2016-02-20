package com.nmatte.mood.views.chart;


import com.nmatte.mood.models.modules.NoteModule;

public class OpenNoteEvent {
    NoteModule module;

    public OpenNoteEvent(NoteModule module){
        this.module = module;
    }

    public NoteModule getModule() {
        return module;
    }
}
