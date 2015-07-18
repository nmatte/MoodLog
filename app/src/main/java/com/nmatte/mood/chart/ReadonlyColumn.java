package com.nmatte.mood.chart;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;

import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.logbookentries.MoodList;
import com.nmatte.mood.logbookentries.SingleEntryActivity;
import com.nmatte.mood.medications.MedList;
import com.nmatte.mood.util.CalendarDatabaseUtil;

import java.util.Calendar;

public class ReadonlyColumn extends LinearLayout {
    final LogbookEntry entry;

    public ReadonlyColumn(Context context) {
        super(context);
        entry = new LogbookEntry(Calendar.getInstance());
    }

    public ReadonlyColumn(Context context, LogbookEntry newEntry, Calendar refDate){
        super(context);
        if (newEntry == null){
            this.entry = new LogbookEntry(Calendar.getInstance());
        } else {
            this.entry = newEntry;
        }

        this.setOrientation(VERTICAL);
        int dateNum = CalendarDatabaseUtil.dayDiff(refDate,entry.getDate());
        this.addView(new TextCellView(context, String.valueOf(dateNum)));
        this.addView(new MoodList(context, false, entry));
        init(context);
    }

    public Intent makeIntent(){
        Intent intent = new Intent(getContext(), SingleEntryActivity.class);
        intent.setAction(SingleEntryActivity.INTENT_FROM_OTHER_ACTIVITY);
        intent.putExtra(SingleEntryActivity.DATE_INT_TAG, entry.getDateInt());
        return intent;
    }

    private void initBlank(Context context){
        this.addView(new CellView(context));
        this.addView(new CellView(context));
        this.addView(new CellView(context));
        MedList medList = new MedList(context,true,false);
        medList.updateList(context);
        this.addView(medList);

    }

    private void init(Context context){
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
