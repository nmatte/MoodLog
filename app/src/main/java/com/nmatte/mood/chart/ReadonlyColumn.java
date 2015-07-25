package com.nmatte.mood.chart;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.cell.CellView;
import com.nmatte.mood.chart.cell.TextCellView;
import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.logbookentries.MoodList;
import com.nmatte.mood.logbookitems.boolitems.BoolItemList;
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
        this.addView(new CellView(context));
        this.addView(new CellView(context));
        this.addView(new CellView(context));
        BoolItemList boolItemList = new BoolItemList(context,true,false);
        boolItemList.updateList(context);
        this.addView(boolItemList);
    }

    private void init(Context context){
        String anx = String.valueOf(entry.getAnxValue());
        String irr = String.valueOf(entry.getIrrValue());
        String hours = String.valueOf(entry.getHoursSleptValue());
        this.addView(new TextCellView(context, anx));
        this.addView(new TextCellView(context, irr));
        this.addView(new TextCellView(context, hours));
        BoolItemList boolItemList = new BoolItemList(context,true,false);
        boolItemList.setEnabled(false);
        boolItemList.updateList(context);
        boolItemList.setCheckedMeds(entry.getBoolItems());
        this.addView(boolItemList);
    }



}
