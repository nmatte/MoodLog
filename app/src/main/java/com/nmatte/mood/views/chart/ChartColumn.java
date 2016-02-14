package com.nmatte.mood.views.chart;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.nmatte.mood.adapters.BoolModuleAdapter;
import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.adapters.MoodModuleAdapter;
import com.nmatte.mood.adapters.NoteModuleAdapter;
import com.nmatte.mood.adapters.NumModuleAdapter;
import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.settings.PreferencesContract;

import org.joda.time.DateTime;

import java.util.ArrayList;

import static com.nmatte.mood.views.chart.ChartColumn.Mode.ENTRY_EDIT;
import static com.nmatte.mood.views.chart.ChartColumn.Mode.ENTRY_READ;
import static com.nmatte.mood.views.chart.ChartColumn.Mode.LABEL;


public class ChartColumn extends LinearLayout {
    ChartEntry entry;

    boolean moodEnabled = false;
    Context context;
    float lastXtouch;
    float lastYtouch;
    int xOffset = 0;
    boolean tutorialFinished = false;
    boolean moodModuleIsMini = false;

    Mode mode = ENTRY_READ;
    /**
     * this listener records the last touch coordinates on the column. These coordinates can be used
     * in the circular reveal animation when opening the EditEntryView.
     */

    OnTouchListener touchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            lastXtouch = event.getX();
            lastYtouch = event.getY();
            return false;
        }
    };


    public ChartColumn(Context context, ChartEntry newEntry, Mode mode){
        super(context);
        this.entry = newEntry;
        this.context = context;
        this.mode = mode;
        init();

    }

    public ChartColumn(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ChartColumn, 0, 0);
        boolean editMode = a.getBoolean(R.styleable.ChartColumn_mode_edit, false);
        boolean labelMode = a.getBoolean(R.styleable.ChartColumn_mode_label,false);
        if (editMode) {
            mode = ENTRY_EDIT;
        } else {
            mode = ENTRY_READ;
        }
        if (labelMode){
            mode = LABEL;
        }
        init();
    }

    private void init(){
        this.setOrientation(VERTICAL);
        this.setOnTouchListener(touchListener);
        if (mode == Mode.ENTRY_EDIT){
            this.setClickable(true);
            this.setEnabled(true);
        }

        if (mode == ENTRY_READ) {
            this.setBackground(context.getResources().getDrawable(R.drawable.drop_shadow_vertical));
        }


        refresh(context);
    }

    public void refresh(Context newContext){
        this.context = newContext;
        if (entry == null){
            entry = new ChartEntry(DateTime.now());
        }
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

        tutorialFinished = settings.getBoolean(PreferencesContract.TUTORIAL_FINISHED, false);

        removeAllViews();
        if(tutorialFinished) {
            addDateRow();
            if (settings.getBoolean(PreferencesContract.FULL_MOOD_MODULE_ENABLED, false)) {
                moodEnabled = true;
                addMoodModule();
            } else if (settings.getBoolean(PreferencesContract.MINI_MOOD_MODULE_ENABLED, false)) {
                moodEnabled = true;
                moodModuleIsMini = true;
                addMoodModule();
            }
            int grayColor = getResources().getColor(R.color.gray_cell_bg);
            int whiteColor = getResources().getColor(R.color.white);

            boolean grayToggle = addNumItems(whiteColor, grayColor, false);
            addBoolItems(whiteColor, grayColor, grayToggle);

            if(settings.getBoolean(PreferencesContract.NOTE_MODULE_ENABLED,false))
                addNoteModule();
        } else {
            removeAllViews();

        }
    }

    private void addDateRow(){
        boolean isToday = entry.getLogDate().getDayOfYear() == DateTime.now().getDayOfYear();
        TextCellViewBuilder b = new TextCellViewBuilder(context);
        switch(mode){
            case ENTRY_READ:
                b.setText(String.valueOf(entry.getLogDate().getDayOfMonth()));
                if (isToday)
                    b.setStroke(TextCellView.Stroke.BOLD);
                b.setHorizontalAlignment(TextCellView.TextAlignment.CENTER);
                break;
            case ENTRY_EDIT:
                if (entry != null){
                    String text = isToday ? "Today" : entry.getLogDate().toString(ChartEntry.EDIT_ENTRY_FORMATTER);
                    b.setText(text);
                }

                b.setHorizontalAlignment(TextCellView.TextAlignment.CENTER);
                break;
            case LABEL:
                b.setText("Date");
                if (moodEnabled)
                    b.setXoffset(context.getResources().getDimension(R.dimen.chart_cell_width_m));
                break;
            default:
                b.setText("");
        }
        this.addView(b.build());
    }

    private void addNoteModule() {
        addAdapter(new NoteModuleAdapter(context, getEntry().getNote()));
    }

    private void addMoodModule(){
        addAdapter(new MoodModuleAdapter(context, getEntry().getMoods(), moodModuleIsMini));

    }

    private void addAdapter(ModuleAdapter adapter){
        ArrayList<View> views = new ArrayList<>();

        switch (mode) {
            case ENTRY_EDIT:
                views = adapter.getEditViews();
                break;
            case ENTRY_READ:
                views = adapter.getReadViews();
                break;
            case LABEL:
                views = adapter.getLabelViews();
        }

        for (View view : views) {
            addView(view);
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
        ArrayList<View> views = new ArrayList<>();
        NumModuleAdapter adapter = new NumModuleAdapter(context, entry.getNumItems());

        switch (mode) {
            case ENTRY_READ:
                views = adapter.getReadViews();
                break;
            case ENTRY_EDIT:
                views = adapter.getEditViews();
                break;
            case LABEL:
                views = adapter.getLabelViews();
                break;
        }

        // TODO: move gray-white alternation elsewhere (maybe when a new one is saved?)
        for (View view : views) {
            color = grayToggle ? grayColor : whiteColor;
            grayToggle = !grayToggle;

            view.setBackgroundColor(color);
            addView(view);
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
        ArrayList<View> views = new ArrayList<>();
        BoolModuleAdapter adapter = new BoolModuleAdapter(context, entry.getBoolItems());

        switch (mode) {
            case ENTRY_READ:
                views = adapter.getReadViews();
                break;
            case ENTRY_EDIT:
                views = adapter.getEditViews();
                break;
            case LABEL:
                views = adapter.getLabelViews();
                break;
        }

        for (View view : views) {
            color = grayToggle ? grayColor : whiteColor;
            grayToggle = !grayToggle;

            view.setBackgroundColor(color);
            addView(view);
        }

        return grayToggle;
    }

    /**
     * Returns the X coordinate of the most recent touch event on this view.
     * @return The last touch event's X coordinate.
     */
    public float getLastXtouch(){
        return lastXtouch;
    }
    /**
     * Returns the Y coordinate of the most recent touch event on this view.
     * @return The last touch event's Y coordinate.
     */
    public float getLastYtouch(){
        return lastYtouch;
    }

    public ChartEntry getEntry() {
        return entry;
    }

    public void setEntry(ChartEntry entry) {
        this.entry = entry;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public enum Mode {
        ENTRY_READ,
        ENTRY_EDIT,
        LABEL
    }
}

