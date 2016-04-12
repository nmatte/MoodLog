package com.nmatte.mood.views.chart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.nmatte.mood.models.modules.Module;
import com.nmatte.mood.models.modules.ModuleConfig;
import com.nmatte.mood.moodlog.R;

public class LabelView extends LinearLayout {
    ModuleConfig config;

    public LabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setConfig(ModuleConfig config) {
        this.config = config;

        refresh();
    }

    private void init(){
        setOrientation(VERTICAL);
        refresh();
    }

    public void refresh(){
        if (config == null) {
            this.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            for (Module module : config.all()) {
                for (View view : module.getViewAdapter().getLabelViews(getContext())){
                    this.addView(view);
                }
            }
        }
    }
}
