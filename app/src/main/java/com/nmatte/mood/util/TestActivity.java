package com.nmatte.mood.util;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.nmatte.mood.logbookentries.editentry.NoteView;
import com.nmatte.mood.moodlog.R;

public class TestActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        FrameLayout main =  (FrameLayout) findViewById(R.id.mainLayout);
        main.addView(new NoteView(this));

    }

}
