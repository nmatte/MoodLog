package com.nmatte.mood.util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.nmatte.mood.chart.column.ChartColumn;
import com.nmatte.mood.moodlog.R;

public class TestActivity extends Activity{

    RelativeLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        main =  (RelativeLayout) findViewById(R.id.mainLayout);
        View frame = getLayoutInflater().inflate(R.layout.chartcolumn_with_selector, main);
        ChartColumn col = (ChartColumn) frame.findViewById(R.id.column);
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


}
