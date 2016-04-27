package com.nmatte.mood.views.chart.columns;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.moodlog.R;

import java.util.ArrayList;

import static com.nmatte.mood.views.chart.columns.ChartColumn.Mode.ENTRY_EDIT;
import static com.nmatte.mood.views.chart.columns.ChartColumn.Mode.ENTRY_READ;
import static com.nmatte.mood.views.chart.columns.ChartColumn.Mode.LABEL;


public class ChartColumn extends LinearLayout {
    ArrayList<ModuleAdapter> adapters = new ArrayList<>();

    Context context;
    float lastXtouch;
    float lastYtouch;

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

    public ChartColumn(Context context, ArrayList<ModuleAdapter> adapters, Mode mode){
        super(context);
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

        refresh(context);
    }

    public void refresh(Context newContext){
        this.context = newContext;

        removeAllViews();

        addAllViews();
    }


    private void addAllViews() {
        for (ModuleAdapter adapter : adapters) {
            addAdapter(adapter);
        }
    }

    private void addAdapter(ModuleAdapter adapter){
//        for (View view : adapter.getViews(mode, context)) {
//            addView(view);
//        }
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

    public enum Mode {
        ENTRY_READ,
        ENTRY_EDIT,
        LABEL
    }

}

