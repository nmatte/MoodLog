package com.nmatte.mood.util;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.nmatte.mood.moodlog.R;

public class TestActivity extends Activity{
    LinearLayout main;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        main = (LinearLayout) findViewById(R.id.mainLinearLayout);

    }

}
