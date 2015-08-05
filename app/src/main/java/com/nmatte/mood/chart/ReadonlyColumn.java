package com.nmatte.mood.chart;

import android.content.Context;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.cell.CellView;
import com.nmatte.mood.chart.cell.CheckableCellView;
import com.nmatte.mood.chart.cell.TextCellView;
import com.nmatte.mood.logbookentries.ChartEntry;
import com.nmatte.mood.logbookentries.MoodList;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.util.CalendarUtil;

import java.util.ArrayList;
import java.util.Calendar;

// TODO fix bug where long click doesn't register on moods

public class ReadonlyColumn extends LinearLayout {
    final ChartEntry entry;
    ArrayList<NumItem> numItems;
    ArrayList<BoolItem> boolItems;


    public ReadonlyColumn(Context context, ChartEntry newEntry,
                          Calendar refDate, ArrayList<NumItem> numItems, ArrayList<BoolItem> boolItems){
        super(context);
        if (newEntry == null){
            this.entry = new ChartEntry();
        } else {
            this.entry = newEntry;
        }
        this.numItems = numItems;
        this.boolItems = boolItems;


        this.setOrientation(VERTICAL);
        int dateNum = CalendarUtil.dayDiff(refDate, entry.getDate());
        this.addView(new TextCellView(context, String.valueOf(dateNum)));
        addMoodModule(context);
        if (entry.isBlank()){
            initBlank(context);
        } else {
            init(context);
        }
    }

    private void initBlank(Context context){
        for (int i = 0; i < (numItems.size() + boolItems.size()); i++){
            addView(new CellView(context));
        }
    }

    private void init(Context context){
        for (NumItem numItem : numItems){
            if (entry.getNumItems().containsKey(numItem)){
                TextCellView newCell = new TextCellView(context);
                newCell.setText(String.valueOf(entry.getNumItems().get(numItem)));
                newCell.setEnabled(false);
            }
            else{
                addView(new CellView(context));
            }
        }
        for (BoolItem boolItem : boolItems){
            if (entry.getBoolItems().containsKey(boolItem)){
                CheckableCellView newCell = new CheckableCellView(context);
                newCell.setChecked(entry.getBoolItems().get(boolItem));
                newCell.setEnabled(false);
            } else {
                addView(new CellView(context));
            }
        }
    }

    private void addMoodModule(Context context){
        for (CheckableCellView cellView : MoodList.getCellViews(context,entry.getMoods())){
            addView(cellView);
        }
    }



}
