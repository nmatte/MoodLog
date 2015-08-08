package com.nmatte.mood.util;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.nmatte.mood.chart.ChartMainFragment;
import com.nmatte.mood.logbookentries.ChartEntry;
import com.nmatte.mood.logbookentries.ChartEntryFragment;
import com.nmatte.mood.moodlog.R;

public class TestActivity extends Activity{
    ChartEntryFragment entryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        entryFragment = (ChartEntryFragment) getFragmentManager().findFragmentById(R.id.testEntryFragment);
        entryFragment.setEntry(new ChartEntry());
    }


    public void onShowClick(View view) {
        getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .show(entryFragment)
                .commit();
    }

    public void onHideClick(View view) {
        getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .hide(entryFragment)
                .commit();
    }
}
