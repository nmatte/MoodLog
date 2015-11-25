package com.nmatte.mood.util;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.nmatte.mood.moodlog.R;

public class TestActivity extends Activity{

    RelativeLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

         main =  (RelativeLayout) findViewById(R.id.mainLayout);

    }


}
