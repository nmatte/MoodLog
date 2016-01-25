package com.nmatte.mood.util;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.nmatte.mood.controllers.chart.IntroDialog;
import com.nmatte.mood.moodlog.R;

public class TestActivity extends Activity{

    RelativeLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);



    }


    public void dialogTestClick(View view) {
        DialogFragment d = new IntroDialog();
        d.show(getFragmentManager(),"intro-dialog-test");
    }
}
