package com.nmatte.mood.models.modules;

import android.content.ContentValues;

import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.database.entries.ChartEntryContract;
import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.models.components.NumComponent;
import com.nmatte.mood.util.DateUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class ModuleConfig {
    BoolModule boolMod;
    MoodModule moodMod;
    NumModule  numMod;
    NoteModule noteMod;
    LogDateModule dateMod;
    ContentValues defaults = new ContentValues();

    public ModuleConfig(BoolModule boolMod, MoodModule moodMod, NumModule numMod, NoteModule noteMod) {
        this.boolMod = boolMod;
        this.moodMod = moodMod;
        this.numMod = numMod;
        this.noteMod = noteMod;
        this.dateMod = new LogDateModule();
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

    public ArrayList<Module> all() {
        ArrayList<Module> modules = new ArrayList<>();

        if (dateMod != null) {
            modules.add(dateMod);
        }
        if (moodMod != null) {
            modules.add(moodMod);
        }
        if (boolMod != null) {
            modules.add(boolMod);
        }
        if (numMod != null) {
            modules.add(numMod);
        }
        if (noteMod != null) {
            modules.add(noteMod);
        }


        return modules;
    }

    public ArrayList<ModuleAdapter> adapters() {
        ArrayList<ModuleAdapter> adapters = new ArrayList<>();

        if (dateMod != null) {
            adapters.add(dateMod.getViewAdapter());
        }
        if (moodMod != null) {
            adapters.add(moodMod.getViewAdapter());
        }
        if (boolMod != null) {
            adapters.add(boolMod.getViewAdapter());
        }
        if (numMod != null) {
            adapters.add(numMod.getViewAdapter());
        }
        if (noteMod != null) {
            adapters.add(noteMod.getViewAdapter());
        }

        return adapters;
    }

    public ChartEntry getBlank(DateTime date) {
        ContentValues defaults = getDefaults();
        defaults.put(dateColumn(), DateUtils.getDateInt(date));
        return new ChartEntry(defaults);
    }
}
