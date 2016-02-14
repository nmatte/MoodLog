package com.nmatte.mood.models;

public class NoteModule extends Module {
    String note;

    NoteModule (String note) {
        this.note = note;
    }

    public String get() {
        return note;
    }

    public void set(String note) {
        this.note = note;
    }


}
