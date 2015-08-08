package com.nmatte.mood.chart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nmatte.mood.logbookentries.ChartEntry;
import com.nmatte.mood.logbookentries.ChartEntryTableHelper;
import com.nmatte.mood.logbookentries.SingleEntryDialog;
import com.nmatte.mood.logbookitems.boolitems.AddBoolDialog;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.boolitems.DeleteBoolDialog;
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.settings.PreferencesContract;
import com.nmatte.mood.settings.SettingsActivity;
import com.nmatte.mood.util.CalendarUtil;
import com.nmatte.mood.util.CalendarUtil;
import com.nmatte.mood.util.TestActivity;

import java.util.ArrayList;
import java.util.Calendar;


public class ChartActivity extends ActionBarActivity
        implements
        SingleEntryDialog.SingleEntryDialogListener
{

    ChartMainFragment chartMainFragment;
    ListView navList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);


        initStartDate();
        initFragments();
    }

    private void initFragments(){
        chartMainFragment = (ChartMainFragment) getFragmentManager().findFragmentById(R.id.chartMainFragment);
        chartMainFragment.setRetainInstance(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshFragments();
    }

    private void refreshFragments(){
        Calendar endDate = Calendar.getInstance();

        chartMainFragment.refreshColumns(getStartDate(), endDate);
    }

    private void initStartDate(){
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        if (!settings.contains(PreferencesContract.CHART_START_DATE)){
            Calendar newStartDate = Calendar.getInstance();
            newStartDate.set(Calendar.DAY_OF_MONTH,newStartDate.getActualMinimum(Calendar.DAY_OF_MONTH));

            settings.edit()
                    .putInt(PreferencesContract.CHART_START_DATE, CalendarUtil.calendarToInt(newStartDate))
                    .apply();
        }
    }


    private void startSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void startTestActivity(){
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    private Calendar getStartDate(){
        Calendar result;
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        int dateInt = settings.getInt(PreferencesContract.CHART_START_DATE,0);
        if (dateInt == 0){
            Calendar newStartDate = Calendar.getInstance();
            newStartDate.set(Calendar.DAY_OF_MONTH,newStartDate.getActualMinimum(Calendar.DAY_OF_MONTH));
            result = newStartDate;
        } else {
            result = CalendarUtil.intToCalendar(dateInt);
        }

        return result;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chart, menu);
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

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onSaveEntryPositiveClick(ChartEntry entry) {
        ChartEntryTableHelper.addOrUpdateEntry(this, entry);
    }

    public void onSettingsItemClick(MenuItem item) {
        startSettingsActivity();
    }

    public void onRemindersItemClick(MenuItem item) {
    }

    public void onTestItemClick(MenuItem item) {
        startTestActivity();
    }
}
