package com.nmatte.mood.models.modules;

import android.content.ContentValues;

import com.nmatte.mood.database.entries.ChartEntryContract;
import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.models.components.NumComponent;

import java.util.ArrayList;

public class ModuleConfig {
    BoolModule boolMod;
    MoodModule moodMod;
    NumModule  numMod;
    NoteModule noteMod;
    ContentValues defaults = new ContentValues();

    public ModuleConfig(BoolModule boolMod, MoodModule moodMod, NumModule numMod, NoteModule noteMod) {
        this.boolMod = boolMod;
        this.moodMod = moodMod;
        this.numMod = numMod;
        this.noteMod = noteMod;
        makeDefaults();
    }

    private void makeDefaults() {
        for (BoolComponent comp : boolMod.getItems()) {
            defaults.put(comp.columnLabel(), 0);
        }

        for (BoolComponent comp : moodMod.getItems()) {
            defaults.put(comp.columnLabel(), 0);
        }

        for (NumComponent comp : numMod.getItems()) {
            defaults.put(comp.columnLabel(), comp.getDefaultNum());
        }

        for (String col : strColumns()) {
            defaults.put(col, "");
        }
    }

    public ContentValues getDefaults() {
        return defaults;
    }


    public ArrayList<String> boolColumns() {
        ArrayList<String> columns = new ArrayList<>();

        for (BoolComponent comp : boolMod.getItems()) {
            columns.add(comp.columnLabel());
        }

        for (BoolComponent comp : moodMod.getItems()) {
            columns.add(comp.columnLabel());
        }

        return columns;
    }

    public ArrayList<String> numColumns() {
        ArrayList<String> columns = new ArrayList<>();

        for (NumComponent comp : numMod.getItems()) {
            columns.add(comp.columnLabel());
        }

        return columns;
    }

    public ArrayList<String> strColumns() {
        ArrayList<String> columns = new ArrayList<>();

        columns.add(ChartEntryContract.ENTRY_NOTE_COLUMN);

        return columns;
    }

    public String dateColumn () {
        return ChartEntryContract.ENTRY_DATE_COLUMN;
    }

    public ArrayList<String> allColumns() {
        ArrayList<String> columns = new ArrayList<>();

        columns.add(dateColumn());
        columns.addAll(boolColumns());
        columns.addAll(numColumns());
        columns.addAll(strColumns());

        return columns;
    }
}