package com.nmatte.mood.chart;

import android.content.Context;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.cell.CellView;
import com.nmatte.mood.chart.cell.CheckableCellView;
import com.nmatte.mood.chart.cell.ReadonlyCheckbox;
import com.nmatte.mood.chart.cell.TextCellView;
import com.nmatte.mood.logbookentries.ChartEntry;
import com.nmatte.mood.logbookentries.MoodList;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.util.CalendarUtil;

import java.util.ArrayList;
import java.util.Calendar;


public class ReadonlyColumn extends LinearLayout {

    final ChartEntry entry;
    ArrayList<NumItem> numItems;
    ArrayList<BoolItem> boolItems;
    Calendar refDate;
    Context context;


    public ReadonlyColumn(Context context, ChartEntry newEntry,
                          Calendar refDate, ArrayList<NumItem> numItems, ArrayList<BoolItem> boolItems){
        super(context);
        this.entry = newEntry;
        this.numItems = numItems;
        this.boolItems = boolItems;
        this.refDate = refDate;
        this.context = context;
        init();

    }

    private void init(){
        this.setOrientation(VERTICAL);
        int dateNum = CalendarUtil.dayDiff(refDate, entry.getDate());
        this.addView(new TextCellView(context, String.valueOf(dateNum)));
        addMoodModule();
        for (NumItem numItem : numItems){
            if (entry.getNumItems().containsKey(numItem)){
                TextCellView newCell = new TextCellView(context);
                newCell.setText(String.valueOf(entry.getNumItems().get(numItem)));
                newCell.setEnabled(false);
                addView(newCell);
            }
            else{
                addView(new CellView(context));
            }
        }
        for (BoolItem boolItem : boolItems){
            if (entry.getBoolItems().containsKey(boolItem)){
                ReadonlyCheckbox newCell = new ReadonlyCheckbox(context);
                newCell.setChecked(entry.getBoolItems().get(boolItem));
                addView(newCell);
            } else {
                addView(new CellView(context));
            }
        }
    }

    private void addMoodModule(){
        for (ReadonlyCheckbox cellView : MoodList.getCellViews(context,entry.getMoods())){
            addView(cellView);
        }
    }

    public ChartEntry getEntry() {
        return entry;
    }



}
