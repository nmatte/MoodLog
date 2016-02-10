package com.nmatte.mood.views.chart;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.nmatte.mood.models.BoolItem;
import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.MoodModule;
import com.nmatte.mood.models.NumItem;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.settings.PreferencesContract;

import org.joda.time.DateTime;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

import static com.nmatte.mood.views.chart.ChartColumn.Mode.ENTRY_EDIT;
import static com.nmatte.mood.views.chart.ChartColumn.Mode.ENTRY_READ;
import static com.nmatte.mood.views.chart.ChartColumn.Mode.LABEL;


public class ChartColumn extends LinearLayout {
    ChartEntry entry;
    ArrayList<NumItem> numItems;
    ArrayList<BoolItem> boolItems;
    boolean moodEnabled = false;
    Context context;
    float lastXtouch;
    float lastYtouch;
    int xOffset = 0;
    boolean tutorialFinished = false;

    MoodModule.Size moodSize;
    Mode mode = ENTRY_READ;
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
        numItems = new ArrayList<>();
        boolItems = new ArrayList<>();
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
                moodSize = MoodModule.Size.FULL;
                addMoodModule();
            } else if (settings.getBoolean(PreferencesContract.MINI_MOOD_MODULE_ENABLED, false)) {
                moodEnabled = true;
                moodSize = MoodModule.Size.MINI;
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


    public void refresh(Context newContext, ChartEntry entry, ArrayList<BoolItem> boolItems, ArrayList<NumItem> numItems){
        this.entry = entry;
        this.boolItems = boolItems;
        this.numItems = numItems;
        refresh(context);
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

        switch(mode){
            case ENTRY_READ:
                ImageCellView cellView = new ImageCellView(context, ENTRY_READ);
                cellView.setImageResource(R.drawable.ic_assignment_black_24dp);
                if (entry.getNote().length() > 0){
                    cellView.setChecked(true);
                    cellView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EventBus.getDefault().post(new OpenNoteEvent(entry));
                        }
                    });
                }
                addView(cellView);
                break;
            case ENTRY_EDIT:
                cellView = new ImageCellView(context, ENTRY_READ);
                if (entry.getNote().length() > 0){
                    cellView.setImageResource(R.drawable.ic_assignment_black_24dp);
                } else {
                    cellView.setImageResource(R.drawable.ic_edit_black_24dp);
                }
                cellView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new OpenNoteEvent(entry));
                    }
                });
                cellView.setChecked(true);
                addView(cellView);
                break;
            case LABEL:
                TextCellViewBuilder b = new TextCellViewBuilder(context);
                if (moodEnabled)
                    b.setXoffset(context.getResources().getDimension(R.dimen.chart_cell_width_m));

                addView(b.setStroke(TextCellView.Stroke.BOLD)
                        .setText("Notes")
                        .build());
                break;
        }




    }

    private void addMoodModule(){
        if (entry == null) {
            entry = new ChartEntry(DateTime.now());
        }
        if (mode == Mode.ENTRY_EDIT) {
            int i = 0;
            for (ImageCellView cellView : entry.getMoods().getCheckboxes(context, mode, moodSize)){
                final int index = i;
                cellView.setOnChangeListener(new ImageCellView.OnChangeListener() {
                    @Override
                    public void onChange(boolean value) {
                        entry.getMoods().set(index, value);
                    }
                });
                addView(cellView);
                if (moodSize == MoodModule.Size.FULL)
                    i++;
                else
                    i += 2;
            }
        }
        if (mode == ENTRY_READ){
            for (ImageCellView cellView : entry.getMoods().getCheckboxes(context, mode, moodSize)){
                cellView.setBackground(CellView.Background.NONE);
                addView(cellView);
            }
        }
        if (mode == Mode.LABEL){
            /*
            for (TextCellView cellView : MoodModule.getLabelViews(context)){
                cellView.setBackground(CellView.Background.NONE);
                addView(cellView);
            }
            */
            addView(MoodModule.getLabelView(context,moodSize));
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

            if (mode == ENTRY_READ) {
                TextCellViewBuilder b = new TextCellViewBuilder(context)
                        .setBackgroundColor(color)
                        .setVerticalAlignment(TextCellView.TextAlignment.CENTER)
                        .setHorizontalAlignment(TextCellView.TextAlignment.CENTER)
                        .setBackground(CellView.Background.NONE);
                if (entry.getNumItems().containsKey(numItem)) {
                    b.setText(String.valueOf(entry.getNumItems().get(numItem)));
                }
                this.addView(b.build());
            }
            if (mode == Mode.LABEL) {

                TextCellViewBuilder b = new TextCellViewBuilder(context);
                if (moodEnabled){
                    b.setXoffset(context.getResources().getDimension(R.dimen.chart_cell_width_m));
                }
                        b.setBackgroundColor(color);
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
            if (mode == ENTRY_READ) {
                ImageCellView imageCellView = new ImageCellView(context, ENTRY_READ);
                imageCellView.setBackgroundColor(color);
                imageCellView.setBackground(CellView.Background.NONE);
                if (entry.getBoolItems().containsKey(boolItem)) {
                    imageCellView.setChecked(entry.getBoolItems().get(boolItem));
                }
                this.addView(imageCellView);
            }
            if (mode == Mode.LABEL) {
                        TextCellViewBuilder b = new TextCellViewBuilder(context);
                if (moodEnabled)
                    b.setXoffset(context.getResources().getDimension(R.dimen.chart_cell_width_m));
                b
                        .setText(boolItem.getName())
                        .setBackgroundColor(color)
                        .setVerticalAlignment(TextCellView.TextAlignment.CENTER)
                        .build();
                this.addView(b.build());
            }
            if (mode == Mode.ENTRY_EDIT){
                ImageCellView cellView = new ImageCellView(context, Mode.ENTRY_EDIT);
                cellView.setOnChangeListener(new ImageCellView.OnChangeListener() {
                    @Override
                    public void onChange(boolean value) {
                        entry.getBoolItems().put(boolItem, value);
                    }
                });
                cellView.setBackgroundColor(color);
                this.addView(cellView);
            }

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

    public  float getLastYtouch(){
        return lastYtouch;
    }

    public ChartEntry getEntry() {
        return entry;
    }

    public void setEntry(ChartEntry entry) {
        this.entry = entry;
    }

    public void setBoolItems(ArrayList<BoolItem> items){
        this.boolItems = items;
    }

    public void setNumItems(ArrayList<NumItem> items){
        this.numItems = items;
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

