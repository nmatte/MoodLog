package com.nmatte.mood.chart;

import android.content.Context;
import android.widget.LinearLayout;

import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.logbookentries.MoodList;
import com.nmatte.mood.medications.MedList;

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
            initBlank(context);
        } else {
            init(context,entry);
        }
    }

    private void initBlank(Context context){
        this.addView(new CellView(context));
        this.addView(new CellView(context));
        this.addView(new CellView(context));
        MedList medList = new MedList(context,true,false);
        medList.updateList(context);
        this.addView(medList);

    }

    private void init(Context context, LogbookEntry entry){
        String anx = String.valueOf(entry.getAnxValue());
        String irr = String.valueOf(entry.getIrrValue());
        String hours = String.valueOf(entry.getHoursSleptValue());
        this.addView(new TextCellView(context, anx));
        this.addView(new TextCellView(context, irr));
        this.addView(new TextCellView(context, hours));
        MedList medList = new MedList(context,true,false);
        medList.updateList(context);
        medList.setCheckedMeds(entry.getMedications());
        this.addView(medList);
    }
}
