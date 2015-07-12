package com.nmatte.mood.chart;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.logbookentries.LogbookEntryTableHelper;
import com.nmatte.mood.moodlog.R;

import java.util.Calendar;


public class ChartActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ChartMainFragment mainFragment = (ChartMainFragment) getFragmentManager().findFragmentById(R.id.chartMainFragment);

        Calendar tmpStartDate = Calendar.getInstance();
        tmpStartDate.roll(Calendar.DAY_OF_YEAR,-5);
        Calendar endDate = Calendar.getInstance();


        mainFragment.refreshColumns(tmpStartDate,endDate);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
