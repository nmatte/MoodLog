package com.nmatte.mood.models;

import com.nmatte.mood.models.modules.LogDateModule;
import com.nmatte.mood.models.modules.Module;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class ChartEntry{


    private final DateTime logDate;

    ArrayList<Module> modules;

    public ChartEntry(DateTime logDate, ArrayList<Module> modules) {
        this.logDate = logDate;
        modules.add(new LogDateModule(logDate));
        modules.addAll(modules);
    }

    public ChartEntry(DateTime logDate){
        this.logDate = logDate;
        modules.add(new LogDateModule(logDate));
    }


    public ArrayList<Module> getModules() {
        return modules;
    }

    public DateTime getLogDate() {
        return logDate;
    }


}
