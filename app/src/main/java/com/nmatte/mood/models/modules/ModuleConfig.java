package com.nmatte.mood.models.modules;

import com.nmatte.mood.database.entries.ChartEntryContract;
import com.nmatte.mood.database.modules.ModuleContract;
import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.models.components.NumComponent;

import java.util.ArrayList;

public class ModuleConfig {
    BoolModule boolMod;
    MoodModule moodMod;
    NumModule  numMod;
    NoteModule noteMod;

    public ModuleConfig(ArrayList<Module> modules) {
        for (Module module :
                modules) {
            switch(module.name) {
                case ModuleContract.BOOL_MODULE_NAME:
                    this.boolMod = (BoolModule) module;
                    break;
                case ModuleContract.MOOD_MODULE_NAME:
                    this.moodMod = (MoodModule) module;
                    break;
                case ModuleContract.NUM_MODULE_NAME:
                    this.numMod = (NumModule) module;
                    break;
                case ModuleContract.NOTE_MODULE_NAME:
                    this.noteMod = (NoteModule) module;
            }
        }
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
