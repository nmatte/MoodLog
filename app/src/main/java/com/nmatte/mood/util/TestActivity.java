package com.nmatte.mood.util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.cell.TextCellView;
import com.nmatte.mood.logbookentries.MoodModule;
import com.nmatte.mood.moodlog.R;

public class TestActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        FrameLayout main =  (FrameLayout) findViewById(R.id.mainLayout);
        View.inflate(this, R.layout.view_moodmodule_large_label, main);
        LinearLayout checkboxLayout = (LinearLayout) findViewById(R.id.moodLabelLayout);
        for (TextCellView cellView : MoodModule.getLabelViews(this)){
            checkboxLayout.addView(cellView);
        }

    }

}
