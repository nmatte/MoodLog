package com.nmatte.mood.chart;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.nmatte.mood.chart.cell.CellView;
import com.nmatte.mood.chart.column.ChartColumn;
import com.nmatte.mood.chart.column.CloseNoteEvent;
import com.nmatte.mood.chart.column.OpenNoteEvent;
import com.nmatte.mood.chart.datedialog.DateRangeDialog;
import com.nmatte.mood.chart.datedialog.OpenEndDateDialogEvent;
import com.nmatte.mood.chart.datedialog.OpenStartDateDialogEvent;
import com.nmatte.mood.chart.datedialog.SaveEndDateDialogEvent;
import com.nmatte.mood.chart.monthview.ChartMonthView;
import com.nmatte.mood.chart.monthview.ScrollViewWithListener;
import com.nmatte.mood.logbookentries.ChartEntry;
import com.nmatte.mood.logbookentries.database.ChartEntryTableHelper;
import com.nmatte.mood.logbookentries.editentry.CloseEditEntryEvent;
import com.nmatte.mood.logbookentries.editentry.NoteView;
import com.nmatte.mood.logbookentries.editentry.OpenEditEntryEvent;
import com.nmatte.mood.logbookitems.ChartChangeEvent;
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.logbookitems.numitems.NumItemTableHelper;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.reminders.ReminderActivity;
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

    FloatingActionButton faButton;
    ChartColumn labelColumn;
    ChartMonthView monthFragment;
    LinearLayout mainLayout;
    Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        detectFirstStartup();
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        EventBus.getDefault().getStickyEvent(ChartChangeEvent.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshFragments();
        Log.i("onResume", "Refreshing fragments...");
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

    /**
     * Detects if the app has been started before via a flag in settings.
     * @return Whether this is the first startup.
     */
    private boolean detectFirstStartup(){
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        if (!settings.contains(PreferencesContract.FIRST_STARTUP)){
            settings.edit().putBoolean(PreferencesContract.FIRST_STARTUP,false).apply();
            Log.i("Startup","First startup detected");
            return true;
        } else {
            return false;
        }
    }

    private void initViews(){
        monthFragment = (ChartMonthView) getFragmentManager().findFragmentById(R.id.chartMainFragment);
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        labelColumn = (ChartColumn) findViewById(R.id.labelColumn);
        labelColumn.setMode(ChartColumn.Mode.LABEL);
        labelColumn.setBoolItems(BoolItemTableHelper.getAll(this));
        labelColumn.setNumItems(NumItemTableHelper.getAll(this));
        faButton = (FloatingActionButton) findViewById(R.id.fabDone);
        labelColumn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startSettingsActivity();
                return false;
            }
        });
        faButton.hide();
        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faButton.hide();
                EventBus.getDefault().post(new CloseEditEntryEvent());
            }
        });
        // FIXME: 9/8/15 fix erratic show/hide behavior
        final ScrollViewWithListener scroll = (ScrollViewWithListener) findViewById(R.id.scrollView);
        scroll.setScrollListener(new ScrollViewWithListener.ScrollListener() {
            @Override
            public void onScrollUp() {
                if (!faButton.isShown() && monthFragment.isEditEntryViewOpen())
                    faButton.show();
                Log.i("ScrollViewWithListener", "onScrollUp called");
            }

            @Override
            public void onScrollDown() {
                if (faButton.isShown())
                    faButton.hide();
                Log.i("ScrollViewWithListener", "onScrollDown called");


            }
        });
    }

    private void refreshFragments(){
        monthFragment.refreshColumns(getChartStartDate(), getChartEndDate());
        labelColumn.refresh(this);
        mainLayout.invalidate();
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
                .append(ChartEntry.getMonthDayString(startDate))
                .append("-")
                .append(ChartEntry.getMonthDayString(endDate))
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
        refreshPickDateButton(getChartStartDate(), getChartEndDate());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.pickDates){
            EventBus.getDefault().post(new OpenStartDateDialogEvent());
        }
        if (id == R.id.largeCells){
            PreferenceManager
                    .getDefaultSharedPreferences(this)
                    .edit()
                    .putInt(PreferencesContract.CELL_SIZE, CellView.Size.LARGE.sizeCode())
                    .apply();
            refreshFragments();
        }
        if (id == R.id.mediumCells){
            PreferenceManager
                    .getDefaultSharedPreferences(this)
                    .edit()
                    .putInt(PreferencesContract.CELL_SIZE, CellView.Size.MEDIUM.sizeCode())
                    .apply();
            refreshFragments();
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
        startRemindersActivity();
    }

    public void onTestItemClick(MenuItem item) {
        startTestActivity();
    }

    private void startRemindersActivity(){
        Intent intent = new Intent(this, ReminderActivity.class);
        startActivity(intent);
    }

    private void startSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void startTestActivity(){
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    public void onEvent(OpenEditEntryEvent event){
        faButton.show();
    }

    public void onEvent(OpenStartDateDialogEvent event){
        DialogFragment d = new DateRangeDialog();
        Bundle args = new Bundle();
        args.putBoolean(DateRangeDialog.BOOL_IS_START_PICKER, true);
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
        monthFragment.refreshColumns(event.getStartDate(), event.getEndDate());
        refreshPickDateButton(event.getStartDate(), event.getEndDate());

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

    public void onEvent(OpenNoteEvent event){
        NoteView noteView = (NoteView) findViewById(R.id.entryNoteView);
        noteView.setEntry(event.getEntry());
        noteView.setVisibility(View.VISIBLE);
        if (faButton.isShown())
            faButton.hide();
        if (monthFragment.isEditEntryViewOpen())
            noteView.setMode(ChartColumn.Mode.ENTRY_EDIT);
        else
            noteView.setMode(ChartColumn.Mode.ENTRY_READ);
        noteView.animateUp();
    }

    public void onEvent(CloseNoteEvent event){
        if(monthFragment.isEditEntryViewOpen())
            faButton.show();
        NoteView noteView = (NoteView) findViewById(R.id.entryNoteView);
        ChartEntryTableHelper.addOrUpdateEntry(this,event.getEntry());
        noteView.animateDown();
    }


}
