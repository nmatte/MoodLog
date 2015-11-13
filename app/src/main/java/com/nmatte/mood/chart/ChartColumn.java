package com.nmatte.mood.chart;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.cell.CellView;
import com.nmatte.mood.chart.cell.CheckboxCellView;
import com.nmatte.mood.chart.cell.TextCellView;
import com.nmatte.mood.chart.cell.TextCellViewBuilder;
import com.nmatte.mood.logbookentries.ChartEntry;
import com.nmatte.mood.logbookentries.MoodModule;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.settings.PreferencesContract;

import java.util.ArrayList;


public class ChartColumn extends LinearLayout {

    final ChartEntry entry;
    ArrayList<NumItem> numItems;
    ArrayList<BoolItem> boolItems;
    Context context;
    float lastXtouch;
    float lastYtouch;


    enum Mode {
        ENTRY,
        LABEL
    }


    public ChartColumn(Context context, ChartEntry newEntry, ArrayList<NumItem> numItems, ArrayList<BoolItem> boolItems){
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

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        if(settings.getBoolean(PreferencesContract.LARGE_MOOD_MODULE_ENABLED,true))
            addMoodModule();

        int grayColor = getResources().getColor(R.color.gray_cell_bg);
        int whiteColor = getResources().getColor(R.color.white);
        boolean grayToggle = addNumItems(whiteColor,grayColor,false);
        addBoolItems(whiteColor,grayColor,grayToggle);

    }

    private void addMoodModule(){
        for (CheckboxCellView cellView : MoodModule.getCellViews(context,entry.getMoods())){
            addView(cellView);
        }
    }

    /**
     *
     * @param whiteColor The first color to be used (default white).
     * @param grayColor The second color to be used (default gray).
     * @param grayToggle Whether to start with white or gray; true is gray, false is white.
     * @return The last value of grayToggle, for use in other functions such as addBoolItems().
     */
    private boolean addNumItems(int whiteColor, int grayColor, boolean grayToggle){
        int color;
        for (NumItem numItem : numItems){
            color = grayToggle ? grayColor : whiteColor;
            grayToggle = !grayToggle;
            String cellText = "";

            if (entry.getNumItems().containsKey(numItem)){
                cellText = String.valueOf(entry.getNumItems().get(numItem));
            }
            TextCellView newCell = new TextCellViewBuilder(context)
                    .setText(cellText)
                    .setHorizontalAlignment(TextCellView.TextAlignment.CENTER)
                    .setVerticalAlignment(TextCellView.TextAlignment.CENTER)
                    .setBackgroundColor(color)
                    .setBackground(CellView.Background.VERTICAL)
                    .build();
            newCell.setEnabled(false);
            newCell.setBackgroundColor(color);
            newCell.setBackground(CellView.Background.VERTICAL);
            addView(newCell);


        }
        return grayToggle;
    }

    /**
     *
     * @param whiteColor The first color to be used (default white).
     * @param grayColor The second color to be used (default gray).
     * @param grayToggle Whether to start with white or gray; true is gray, false is white.
     * @return The last value of grayToggle, for use in other functions such as addNumItems().
     */
    private boolean addBoolItems(int whiteColor, int grayColor, boolean grayToggle){
        int color;
        for (BoolItem boolItem : boolItems){
            color = grayToggle ? grayColor : whiteColor;
            grayToggle = !grayToggle;
            if (entry.getBoolItems().containsKey(boolItem)){
                CheckboxCellView newCell = new CheckboxCellView(context);
                newCell.setBackgroundColor(color);
                newCell.setBackground(CellView.Background.VERTICAL);
                newCell.setChecked(entry.getBoolItems().get(boolItem));
                addView(newCell);
            } else {
                addView(new CellView(context,color));
            }
        }
        return grayToggle;
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

    /**
     * Returns the X coordinate of the most recent touch event on this view.
     * @return The last touch event's X coordinate.
     */
    public float getLastXtouch(){
        return lastXtouch;
    }


    public  float getLastYtouch(){
        return lastYtouch;
    }
}
