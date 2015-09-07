package com.nmatte.mood.chart;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.cell.CellView;
import com.nmatte.mood.chart.cell.ReadonlyCheckbox;
import com.nmatte.mood.chart.cell.TextCellView;
import com.nmatte.mood.chart.cell.TextCellViewBuilder;
import com.nmatte.mood.logbookentries.ChartEntry;
import com.nmatte.mood.logbookentries.MoodList;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.numitems.NumItem;

import java.util.ArrayList;


public class ReadonlyColumn extends LinearLayout {

    final ChartEntry entry;
    ArrayList<NumItem> numItems;
    ArrayList<BoolItem> boolItems;
    Context context;
    float lastXtouch;
    float lastYtouch;


    public ReadonlyColumn(Context context, ChartEntry newEntry, ArrayList<NumItem> numItems, ArrayList<BoolItem> boolItems){
        super(context);
        this.entry = newEntry;
        this.numItems = numItems;
        this.boolItems = boolItems;
        this.context = context;
        init();

    }

    private void init(){
        this.setOrientation(VERTICAL);
        this.setOnTouchListener(touchListener);
        int dateNum = entry.getLogDate().getDayOfMonth();
        this.addView(new TextCellViewBuilder(context).setText(String.valueOf(dateNum)).build());
        addMoodModule();
        for (NumItem numItem : numItems){
            if (entry.getNumItems().containsKey(numItem)){
                TextCellView newCell = new TextCellViewBuilder(context)
                        .setText(String.valueOf(entry.getNumItems().get(numItem)))
                        .build();
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


    // this listener records the last touch coordinates on the column. These coordinates can be used
    // in the circular reveal animation when opening the EditEntryView.
    OnTouchListener touchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            lastXtouch = event.getX();
            lastYtouch = event.getY();
            return false;
        }
    };

    public float getLastXtouch(){
        return lastXtouch;
    }


    public  float getLastYtouch(){
        return lastYtouch;
    }
}
