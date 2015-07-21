package com.nmatte.mood.chart;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.logbookentries.MoodList;
import com.nmatte.mood.logbookentries.SingleEntryActivity;
import com.nmatte.mood.medications.MedList;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.util.CalendarDatabaseUtil;

import java.util.Calendar;

public class ReadonlyColumn extends LinearLayout {
    final LogbookEntry entry;

    public ReadonlyColumn(Context context) {
        super(context);
        entry = new LogbookEntry(Calendar.getInstance());
    }

    public ReadonlyColumn(final Context context, LogbookEntry newEntry, Calendar refDate){
        super(context);
        if (newEntry == null){
            this.entry = new LogbookEntry(Calendar.getInstance());
        } else {
            this.entry = newEntry;
        }

        this.setOrientation(VERTICAL);
        int dateNum = CalendarDatabaseUtil.dayDiff(refDate,entry.getDate());
        this.addView(new TextCellView(context, String.valueOf(dateNum)));
        MoodList moodList = new MoodList(context, false, entry);
        moodList.setLongClickable(true);
        moodList.setEnabled(true);
        moodList.setDuplicateParentStateEnabled(true);
        moodList.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        this.addView(moodList);
        if (entry.isBlank()){
            initBlank(context);
        } else {
            init(context);
        }
    }

    private void initBlank(Context context){
        this.addView(inflate(context,R.layout.layout_cell,null));
        this.addView(inflate(context,R.layout.layout_cell,null));
        this.addView(inflate(context,R.layout.layout_cell,null));
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
        medList.setEnabled(false);
        medList.updateList(context);
        medList.setCheckedMeds(entry.getMedications());
        this.addView(medList);
    }



}
