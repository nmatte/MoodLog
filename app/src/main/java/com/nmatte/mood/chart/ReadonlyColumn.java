package com.nmatte.mood.chart;

import android.content.Context;
import android.widget.LinearLayout;

import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.moodlog.MoodList;

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
}
