package com.nmatte.mood.views.chart;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.nmatte.mood.moodlog.R;

public class SelectorWrapper extends FrameLayout {
    Context context;

    public SelectorWrapper(Context context, View child) {
        super(context);
        this.context = context;
        this.addView(child);
        init();
    }

    private void init(){
        inflate(context, R.layout.view_selector_wrapper, this);
    }

}
