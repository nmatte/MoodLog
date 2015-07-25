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

import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.logbookentries.LogbookEntryFragment;
import com.nmatte.mood.logbookentries.LogbookEntryTableHelper;
import com.nmatte.mood.logbookentries.SingleEntryActivity;
import com.nmatte.mood.logbookentries.SingleEntryDialog;
import com.nmatte.mood.logbookitems.boolitems.AddBoolDialog;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.boolitems.DeleteBoolDialog;
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.settings.PreferencesContract;
import com.nmatte.mood.settings.SettingsActivity;
import com.nmatte.mood.util.CalendarDatabaseUtil;

import java.util.ArrayList;
import java.util.Calendar;


public class ChartActivity extends ActionBarActivity
        implements AddBoolDialog.AddMedicationListener,
        DeleteBoolDialog.DeleteBoolItemListener,
        SingleEntryDialog.SingleEntryDialogListener
{

    LogbookEntryFragment entryFragment;
    ChartMainFragment chartMainFragment;
    ListView navList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);


        initStartDate();
        initFragments();
        initNavbar();
    }

    private void initFragments(){
        chartMainFragment = (ChartMainFragment) getFragmentManager().findFragmentById(R.id.chartMainFragment);
        chartMainFragment.setRetainInstance(false);

        entryFragment = (LogbookEntryFragment) getFragmentManager().findFragmentById(R.id.singleEntryFragment);
        entryFragment.setRetainInstance(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshFragments();
    }

    private void refreshFragments(){
        Calendar endDate = Calendar.getInstance();

        chartMainFragment.refreshColumns(getStartDate(), endDate);
        LogbookEntry todayEntry = LogbookEntryTableHelper.getEntryToday(this);
        if(todayEntry == null){
            todayEntry = new LogbookEntry();
        }
        entryFragment.setEntry(todayEntry);
    }

    private void initStartDate(){
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        if (!settings.contains(PreferencesContract.CHART_START_DATE)){
            Calendar newStartDate = Calendar.getInstance();
            newStartDate.set(Calendar.DAY_OF_MONTH,newStartDate.getActualMinimum(Calendar.DAY_OF_MONTH));

            settings.edit()
                    .putInt(PreferencesContract.CHART_START_DATE, CalendarDatabaseUtil.calendarToInt(newStartDate))
                    .apply();
        }
    }


    private void initNavbar(){
        navList = (ListView) findViewById(R.id.drawerList);
        final ArrayList<String> navItems = new ArrayList<>();
        navItems.add("Settings");
        navList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, navItems));
        View header = getLayoutInflater().inflate(R.layout.navlist_header, null);
        navList.addHeaderView(header);
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        startSettingsActivity();
                        break;
                }

            }
        });
    }


    private void startSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void startSingleEntryActivity(Calendar date){
        Intent intent = new Intent(this, SingleEntryActivity.class);
        intent.setAction(SingleEntryActivity.INTENT_FROM_OTHER_ACTIVITY);
        intent.putExtra(SingleEntryActivity.DATE_INT_TAG,CalendarDatabaseUtil.calendarToInt(date));
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
            result = CalendarDatabaseUtil.intToCalendar(dateInt);
        }

        return result;
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

    @Override
    protected void onPause() {
        super.onPause();
        LogbookEntryTableHelper.addOrUpdateEntry(this,entryFragment.getEntry());
    }

    @Override
    public void onAddDialogPositiveClick(String name) {
        LogbookEntry currentEntry = entryFragment.getEntry();
        BoolItemTableHelper.addBoolItem(this, new BoolItem(name));
        entryFragment.setEntry(currentEntry);
    }

    @Override
    public void onDeleteDialogPositiveClick(String name) {
        LogbookEntry currentEntry = entryFragment.getEntry();
        BoolItemTableHelper.deleteBoolItemWithName(this, name);
        entryFragment.setEntry(currentEntry);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onSaveEntryPositiveClick(LogbookEntry entry) {
        LogbookEntryTableHelper.addOrUpdateEntry(this,entry);
    }
}
