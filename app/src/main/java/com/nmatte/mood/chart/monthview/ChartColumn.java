package com.nmatte.mood.chart.monthview;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
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
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.logbookitems.numitems.NumItemTableHelper;
import com.nmatte.mood.moodlog.CustomNumberPicker;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.settings.PreferencesContract;

import org.joda.time.DateTime;

import java.util.ArrayList;


public class ChartColumn extends LinearLayout {

    public void setEntry(ChartEntry entry) {
        this.entry = entry;
    }

    ChartEntry entry;
    ArrayList<NumItem> numItems;
    ArrayList<BoolItem> boolItems;
    Context context;
    float lastXtouch;
    float lastYtouch;



    Mode mode = Mode.ENTRY_READ;


    public enum Mode {
        ENTRY_READ,
        ENTRY_EDIT,
        LABEL
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }


    public ChartColumn(Context context, ChartEntry newEntry, ArrayList<NumItem> numItems, ArrayList<BoolItem> boolItems, Mode mode){
        super(context);
        this.entry = newEntry;
        this.numItems = numItems;
        this.boolItems = boolItems;
        this.context = context;
        this.mode = mode;
        init();

    }

    public ChartColumn(Context context, AttributeSet attrs){
        super(context, attrs);
        entry = new ChartEntry(DateTime.now());
        numItems = NumItemTableHelper.getAll(context);
        boolItems = BoolItemTableHelper.getAll(context);
        this.context = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ChartColumn, 0, 0);
        int value = a.getIndex(R.styleable.ChartColumn_view_mode);
        switch(value){
            case 0:
                mode = Mode.ENTRY_EDIT;
                break;
            case 1:
                mode = Mode.ENTRY_READ;
                break;
            case 2:
                mode = Mode.LABEL;
                break;
            default:
                mode = Mode.ENTRY_EDIT;
                break;
        }
        init();
    }

    public void refresh(Context newContext){
        this.context = newContext;
        removeAllViews();
        String cellText = (mode == Mode.ENTRY_READ) ?
                String.valueOf(entry.getLogDate().getDayOfMonth()) : "Date";

        this.addView(new TextCellViewBuilder(context).setText(cellText).build());


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        if(settings.getBoolean(PreferencesContract.LARGE_MOOD_MODULE_ENABLED,true))
            addMoodModule();

        int grayColor = getResources().getColor(R.color.gray_cell_bg);
        int whiteColor = getResources().getColor(R.color.white);

        boolean grayToggle = addNumItems(whiteColor,grayColor,false);
        addBoolItems(whiteColor, grayColor, grayToggle);
    }

    private void init(){
        this.setOrientation(VERTICAL);
        this.setOnTouchListener(touchListener);
        if (mode == Mode.ENTRY_EDIT){
            this.setClickable(true);
            this.setEnabled(true);
        }


        refresh(context);

    }

    private void addMoodModule(){
        if (mode == Mode.ENTRY_READ){
            for (CheckboxCellView cellView : MoodModule.getCellViews(context,entry.getMoods(),mode)){
                addView(cellView);
            }
        }
        if (mode == Mode.LABEL){
            for (TextCellView cellView : MoodModule.getLabelViews(context)){
                addView(cellView);
            }
        }
        if (mode == Mode.ENTRY_EDIT) {
            int i = 0;
            for (CheckboxCellView cellView : MoodModule.getCellViews(context,entry.getMoods(),mode)){
                final int index = i;
                cellView.setOnChangeListener(new CheckboxCellView.OnChangeListener() {
                    @Override
                    public void onChange(boolean value) {
                        entry.getMoods().set(index,value);
                    }
                });
                addView(cellView);
                i++;
            }
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

        for (final NumItem numItem : numItems) {
            color = grayToggle ? grayColor : whiteColor;
            grayToggle = !grayToggle;

            if (mode == Mode.ENTRY_READ) {
                TextCellViewBuilder b = new TextCellViewBuilder(context)
                        .setBackgroundColor(color)
                        .setVerticalAlignment(TextCellView.TextAlignment.CENTER)
                        .setHorizontalAlignment(TextCellView.TextAlignment.CENTER)
                        .setBackground(CellView.Background.VERTICAL);
                if (entry.getNumItems().containsKey(numItem)) {
                    b.setText(String.valueOf(entry.getNumItems().get(numItem)));
                }
                this.addView(b.build());
            }
            if (mode == Mode.LABEL) {
                TextCellViewBuilder b = new TextCellViewBuilder(context)
                        .setBackgroundColor(color)
                        .setVerticalAlignment(TextCellView.TextAlignment.CENTER);
                 b.setText(numItem.getName());
                this.addView(b.build());
            }
            if (mode == Mode.ENTRY_EDIT){
                final CustomNumberPicker numPicker = new CustomNumberPicker(context,numItem);
                numPicker.setBackgroundColor(color);
                numPicker.setNumChangeListener(new CustomNumberPicker.NumChangeListener() {
                    @Override
                    public void onChange(int change) {
                        entry.getNumItems().put(numItem, change);
                    }
                });
                this.addView(numPicker);
            }

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
        for (final BoolItem boolItem : boolItems) {
            color = grayToggle ? grayColor : whiteColor;
            grayToggle = !grayToggle;
            if (mode == Mode.ENTRY_READ) {
                CheckboxCellView checkboxCellView = new CheckboxCellView(context,Mode.ENTRY_READ);
                checkboxCellView.setBackgroundColor(color);
                checkboxCellView.setBackground(CellView.Background.VERTICAL);
                if (entry.getBoolItems().containsKey(boolItem)) {
                    checkboxCellView.setChecked(entry.getBoolItems().get(boolItem));
                }
                this.addView(checkboxCellView);
            }
            if (mode == Mode.LABEL) {
                TextCellView newCell = new TextCellViewBuilder(context)
                        .setText(boolItem.getName())
                        .setBackgroundColor(color)
                        .setVerticalAlignment(TextCellView.TextAlignment.CENTER)
                        .build();
                this.addView(newCell);
            }
            if (mode == Mode.ENTRY_EDIT){
                CheckboxCellView cellView = new CheckboxCellView(context, Mode.ENTRY_EDIT);
                cellView.setOnChangeListener(new CheckboxCellView.OnChangeListener() {
                    @Override
                    public void onChange(boolean value) {
                        entry.getBoolItems().put(boolItem,value);
                    }
                });
                cellView.setBackgroundColor(color);
                this.addView(cellView);
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
