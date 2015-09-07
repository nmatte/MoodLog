package com.nmatte.mood.util;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.nmatte.mood.logbookentries.editentry.EditEntryLayout;
import com.nmatte.mood.moodlog.R;

public class TestActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void circularRevealTest(View view) {
        setUpTestLayout();
        View myView = findViewById(R.id.circRevealView);

// get the center for the clipping circle
        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

// get the final radius for the clipping circle
        int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

// create the animator for this view (the start radius is zero)
        Animator anim =
                  ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

        anim.setDuration(1000);
// make the view visible and start the animation
        myView.setVisibility(View.VISIBLE);
        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpTestLayout(){
        EditEntryLayout editEntryLayout = (EditEntryLayout) findViewById(R.id.editEntryLayout);

        int cx = editEntryLayout.getWidth() / 2;
        int cy = editEntryLayout.getHeight() / 2;
        int finalRadius = Math.max(editEntryLayout.getWidth(), editEntryLayout.getHeight());
        Animator anim =
                ViewAnimationUtils.createCircularReveal(editEntryLayout, cx, cy, 0, finalRadius);

        anim.setDuration(800);
        anim.start();
    }
}
