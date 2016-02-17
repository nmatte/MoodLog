package com.nmatte.mood.models.modules;


import android.content.Context;

import com.nmatte.mood.adapters.LogDateAdapter;
import com.nmatte.mood.adapters.ModuleAdapter;

import org.joda.time.DateTime;

public class LogDateModule extends Module {
    DateTime date;

    public LogDateModule(DateTime date) {
        this.date = date;
    }

    @Override
    public ModuleAdapter getAdapter(Context context) {
        return new LogDateAdapter(context, this);
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public boolean isToday() {
        return date.getDayOfYear() == DateTime.now().getDayOfYear();
    }
}
