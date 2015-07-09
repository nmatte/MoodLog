package com.nmatte.mood.chart;

import android.content.Context;
import android.widget.LinearLayout;

import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.moodlog.MoodList;

import java.util.Calendar;

public class ReadonlyColumn extends LinearLayout {
    public ReadonlyColumn(Context context) {
        super(context);
    }

    public ReadonlyColumn(Context context, LogbookEntry entry, int dateNum){
        super(context);
        this.setOrientation(VERTICAL);
        this.addView(new TextCellView(context, String.valueOf(dateNum)));
        this.addView(new MoodList(context, false, entry));
        if(entry == null) {
            this.addView(new CellView(context));
            this.addView(new CellView(context));
            this.addView(new CellView(context));
        } else {
            String anx = String.valueOf(entry.getAnxValue());
            String irr = String.valueOf(entry.getIrrValue());
            String hours = String.valueOf(entry.getHoursSleptValue());
            this.addView(new TextCellView(context, anx));
            this.addView(new TextCellView(context, irr));
            this.addView(new TextCellView(context, hours));
        }
    }

    public ReadonlyColumn(Context context, LogbookEntry entry, Calendar dateRef){
        super(context);
        this.setOrientation(VERTICAL);
        int thisDayOfYear = entry.getDate().get(Calendar.DAY_OF_YEAR);
        int dateRefDay = dateRef.get(Calendar.DAY_OF_YEAR);
        int dateNum = 1;
        if (thisDayOfYear - dateRefDay >= 0){
            // days of year within same year. Add 1 so first day appears as day 1, etc.
            dateNum = (thisDayOfYear - dateRefDay) + 1;
        } else {
            // the entry's date is in a year after the dateRef. Account for wraparound.
            int firstYearDiff = dateRef.getActualMaximum(Calendar.DAY_OF_YEAR) - dateRefDay;
            dateNum = firstYearDiff + thisDayOfYear + 1;

        }
        this.addView(new TextCellView(context, String.valueOf(dateNum)));
        this.addView(new MoodList(context, false, entry));
        if(entry == null) {
            this.addView(new CellView(context));
            this.addView(new CellView(context));
            this.addView(new CellView(context));
        } else {
            String anx = String.valueOf(entry.getAnxValue());
            String irr = String.valueOf(entry.getIrrValue());
            String hours = String.valueOf(entry.getHoursSleptValue());
            this.addView(new TextCellView(context, anx));
            this.addView(new TextCellView(context, irr));
            this.addView(new TextCellView(context, hours));
        }
    }
}
