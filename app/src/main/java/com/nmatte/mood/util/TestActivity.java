package com.nmatte.mood.util;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.nmatte.mood.chart.column.SelectorWrapper;
import com.nmatte.mood.logbookentries.ChartEntry;
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.logbookitems.numitems.NumItemTableHelper;
import com.nmatte.mood.moodlog.R;

import org.joda.time.DateTime;

public class TestActivity extends Activity{

    RelativeLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        main =  (RelativeLayout) findViewById(R.id.mainLayout);
        SelectorWrapper col = new SelectorWrapper(this);
        col.getColumn().refresh(this,
                new ChartEntry(DateTime.now()),
                BoolItemTableHelper.getAll(this),
                NumItemTableHelper.getAll(this));
        main.addView(col);


    }


}
