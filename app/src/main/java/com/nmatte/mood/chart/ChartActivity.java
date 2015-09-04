package com.nmatte.mood.chart;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nmatte.mood.chart.datedialog.DateRangeDialog;
import com.nmatte.mood.chart.datedialog.OpenEndDateDialogEvent;
import com.nmatte.mood.chart.datedialog.OpenStartDateDialogEvent;
import com.nmatte.mood.chart.datedialog.SaveEndDateDialogEvent;
import com.nmatte.mood.logbookentries.editentry.CloseEditEntryEvent;
import com.nmatte.mood.logbookentries.editentry.OpenEditEntryEvent;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.settings.PreferencesContract;
import com.nmatte.mood.settings.SettingsActivity;
import com.nmatte.mood.util.TestActivity;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

import de.greenrobot.event.EventBus;


public class ChartActivity extends AppCompatActivity
{

    ChartMainFragment chartMainFragment;
    Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initFragments();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshFragments();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void initFragments(){
        chartMainFragment = (ChartMainFragment) getFragmentManager().findFragmentById(R.id.chartMainFragment);
        chartMainFragment.setRetainInstance(false);
    }

    private void refreshFragments(){
        chartMainFragment.refreshColumns(getChartStartDate(), getChartEndDate());
    }

    private void startSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void startTestActivity(){
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    private DateTime getChartStartDate(){

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        if(settings.getBoolean(PreferencesContract.BOOL_CHART_REMEMBER_DATES,false)){
            if(settings.contains(PreferencesContract.LONG_CHART_START_DATE)){
                long millis = settings.getLong(PreferencesContract.LONG_CHART_START_DATE,0);
                if (millis != 0)
                    return new DateTime(millis);
            }
        }
        return DateTime.now().withDayOfMonth(1);
    }

    private DateTime getChartEndDate(){
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        if(settings.getBoolean(PreferencesContract.BOOL_CHART_REMEMBER_DATES,false)){
            if(settings.contains(PreferencesContract.LONG_CHART_END_DATE)){
                long millis = settings.getLong(PreferencesContract.LONG_CHART_END_DATE,0);
                if (millis != 0)
                    return new DateTime(millis);
            }
        }
        return DateTime.now().dayOfMonth().withMaximumValue();
    }



    private void refreshPickDateButton(DateTime startDate, DateTime endDate){
        if(endDate.isBefore(startDate)){
            DateTime tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }
        DateTimeFormatter fmt = DateTimeFormat.shortDate().withLocale(Locale.getDefault());
        String title = new StringBuilder()
                .append(startDate.toString(fmt))
                .append("-")
                .append(endDate.toString(fmt))
                .toString();
        menu.findItem(R.id.pickDates).setTitle(title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chart, menu);
        this.menu = menu;

        MenuItem editEntryDoneButton = menu.findItem(R.id.editEntryDoneButton);
        editEntryDoneButton.setVisible(false);

        refreshPickDateButton(getChartStartDate(),getChartEndDate());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.editEntryDoneButton){
            item.setVisible(false);
            EventBus.getDefault().post(new CloseEditEntryEvent());
        }

        if (id == R.id.pickDates){
            EventBus.getDefault().post(new OpenStartDateDialogEvent());
        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void onSettingsItemClick(MenuItem item) {
        startSettingsActivity();
    }

    public void onRemindersItemClick(MenuItem item) {
    }

    public void onTestItemClick(MenuItem item) {
        startTestActivity();
    }

    public void onEvent(OpenEditEntryEvent event){
        MenuItem doneButton = menu.findItem(R.id.editEntryDoneButton);
        doneButton.setVisible(true);
    }

    public void onEvent( OpenStartDateDialogEvent event){
        DialogFragment d = new DateRangeDialog();
        Bundle args = new Bundle();
        args.putBoolean(DateRangeDialog.BOOL_IS_START_PICKER,true);
        d.setArguments(args);
        d.show(getFragmentManager(), "foo");
    }

    public void onEvent(OpenEndDateDialogEvent event){
        DialogFragment dialog = new DateRangeDialog();
        Bundle args = new Bundle();
        args.putBoolean(DateRangeDialog.BOOL_IS_START_PICKER,false);
        args.putLong(DateRangeDialog.LONG_START_DATE_VALUE, event.getDate().getMillis());
        Log.i("Date Range Dialog", "Start date chosen: " + event.getDate().toString("MM/dd/YYYY"));
        dialog.setArguments(args);
        dialog.show(getFragmentManager(), "bar");
    }

    public void onEvent(SaveEndDateDialogEvent event){
        boolean foo = event.isRememberDates();
        Log.i("Date Range Dialog", "End date chosen: " + event.getEndDate().toString("MM/dd/YYYY"));
        chartMainFragment.refreshColumns(event.getStartDate(),event.getEndDate());
        refreshPickDateButton(event.getStartDate(),event.getEndDate());

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        if (event.isRememberDates()){
            settings
                    .edit()
                    .putBoolean(PreferencesContract.BOOL_CHART_REMEMBER_DATES,event.isRememberDates())
                    .putLong(PreferencesContract.LONG_CHART_START_DATE,event.getStartDate().getMillis())
                    .putLong(PreferencesContract.LONG_CHART_END_DATE,event.getEndDate().getMillis())
                    .apply();
        } else{
            settings
                    .edit()
                    .putBoolean(PreferencesContract.BOOL_CHART_REMEMBER_DATES,event.isRememberDates())
                    .apply();
        }
    }
}
