package com.nmatte.mood.views.chart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.nmatte.mood.moodlog.R;

public class SelectorWrapper extends FrameLayout {
    Context context;
    ChartColumn column;

    public SelectorWrapper(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SelectorWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();

    }

    private void init(){
        inflate(context, R.layout.chartcolumn_with_selector, this);
        column = (ChartColumn) findViewById(R.id.column);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public ChartColumn getColumn(){
        return column;
    }

}
