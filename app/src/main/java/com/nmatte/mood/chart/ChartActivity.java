package com.nmatte.mood.chart;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.nmatte.mood.logbookentries.editentry.CloseEditEntryEvent;
import com.nmatte.mood.logbookentries.editentry.OpenEditEntryEvent;
import com.nmatte.mood.moodlog.R;
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
    /*
    SharedPreferences settings = getPreferences(MODE_PRIVATE);
        if (!settings.contains(PreferencesContract.CHART_START_DATE)){
            Calendar newStartDate = Calendar.getInstance();
            newStartDate.set(Calendar.DAY_OF_MONTH,newStartDate.getActualMinimum(Calendar.DAY_OF_MONTH));

            settings.edit()
                    .putInt(PreferencesContract.CHART_START_DATE, CalendarUtil.calendarToInt(newStartDate))
                    .apply();
        }


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
        */

        return DateTime.now().withDayOfMonth(1);
    }

    private DateTime getChartEndDate(){
        return DateTime.now();
    }

    public String getDateRangeString(DateTime startDate, DateTime endDate){
        DateTimeFormatter fmt = DateTimeFormat.shortDate().withLocale(Locale.getDefault());
        return startDate.toString(fmt) + "-" + endDate.toString(fmt);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chart, menu);
        this.menu = menu;

        MenuItem editEntryDoneButton = menu.findItem(R.id.editEntryDoneButton);
        editEntryDoneButton.setVisible(false);

        MenuItem pickDateButton = menu.findItem(R.id.pickDates);
        pickDateButton.setTitle(getDateRangeString(getChartStartDate(),getChartEndDate()));

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
        args.putLong(DateRangeDialog.LONG_START_DATE_VALUE, event.date.getMillis());
        dialog.setArguments(args);

        dialog.show(getFragmentManager(), "bar");
    }

    public void onEvent(SaveEndDateDialogEvent event){
        boolean foo = event.isRememberDates();
        chartMainFragment.refreshColumns(event.getStartDate(),event.getEndDate());
    }


}
