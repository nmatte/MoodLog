package com.nmatte.mood.controllers.chart;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nmatte.mood.controllers.SettingsActivity;
import com.nmatte.mood.database.components.BoolItemTableHelper;
import com.nmatte.mood.database.components.NumItemTableHelper;
import com.nmatte.mood.database.entries.ChartEntryContract;
import com.nmatte.mood.database.entries.ChartEntryTable;
import com.nmatte.mood.database.modules.ModuleContract;
import com.nmatte.mood.database.modules.ModuleTableHelper;
import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.models.components.NumComponent;
import com.nmatte.mood.models.modules.LogDateModule;
import com.nmatte.mood.models.modules.ModuleConfig;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.reminders.ReminderActivity;
import com.nmatte.mood.settings.PreferencesContract;
import com.nmatte.mood.util.DateUtils;
import com.nmatte.mood.util.TestActivity;
import com.nmatte.mood.views.chart.CellView;
import com.nmatte.mood.views.chart.ChartMonthView;
import com.nmatte.mood.views.chart.LabelView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Array;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ChartActivity extends AppCompatActivity
{

//    FloatingActionButton faButton;
    ChartMonthView monthFragment;
//    LinearLayout mainLayout;
    Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
//        EventBus.getDefault().getStickyEvent(RefreshChartEvent.class);
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

    private void init() {
        this.checkFirstStart();
        initViews();
    }

    private void checkFirstStart() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("firstStart",true)){
            prefs
                    .edit()
                    .putBoolean("firstStart",false)
                    .putBoolean(PreferencesContract.NOTE_MODULE_ENABLED,false)
                    .apply();


            setUpModules();
        }
    }

    private void initViews(){
        monthFragment = (ChartMonthView) getFragmentManager().findFragmentById(R.id.chartMainFragment);
//        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        initLabelView();
//        addFabListener();
//        addScrollViewListener();
    }

    private void initLabelView() {
        ModuleTableHelper mHelper = new ModuleTableHelper(this);

        Observable.just(mHelper)
                .map(ModuleTableHelper::getModules)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ModuleConfig>() {
                    ModuleConfig config;
                    @Override
                    public void onCompleted() {
                        LabelView labelView = (LabelView) findViewById(R.id.labelView);
                        labelView.setConfig(config);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ModuleConfig moduleConfig) {
                        config = moduleConfig;
                    }
                });
    }

//    private void addFabListener() {
//        faButton = (FloatingActionButton) findViewById(R.id.fabDone);
//
//        faButton.hide();
//        faButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                faButton.hide();
//                EventBus.getDefault().post(new ChartEvents.CloseEditEntryEvent());
//            }
//        });
//    }

//    private void addScrollViewListener() {
//        // FIXME: 9/8/15 fix erratic show/hide behavior
//        final ScrollViewWithListener scroll = (ScrollViewWithListener) findViewById(R.id.scrollView);
//        scroll.setScrollListener(new ScrollViewWithListener.ScrollListener() {
//            @Override
//            public void onScrollUp() {
//                if (!faButton.isShown() && monthFragment.isEditEntryViewOpen())
//                    faButton.show();
//            }
//
//            @Override
//            public void onScrollDown() {
//                if (faButton.isShown())
//                    faButton.hide();
//            }
//        });
//    }

    private void refreshFragments(){
        DateTime start = DateTime.now().withDayOfMonth(1);
        DateTime end = DateTime.now().dayOfMonth().withMaximumValue();

        SimpleArrayMap<DateTime, ChartEntry> values = new ChartEntryTable(this).getEntryGroup(start, end);

        Observable<ChartEntry> entries = Observable.from(DateUtils.getDatesInRange(start, end))
                .map(d -> {
                    if (values.containsKey(d)) {
                        return values.get(d);
                    } else {
                        ContentValues emptyValues = new ContentValues();
                        emptyValues.put(ChartEntryContract.ENTRY_DATE_COLUMN, DateUtils.getDateInt(d));
                        return new ChartEntry(emptyValues);
                    }
                });
        monthFragment.refreshColumns(
                entries,
                new ModuleTableHelper(this).getModules()
        );
//        mainLayout.invalidate();
    }

    private void refreshPickDateButton(DateTime startDate, DateTime endDate){
        if(endDate.isBefore(startDate)){
            DateTime tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }


        DateTimeFormatter fmt = DateTimeFormat.shortDate().withLocale(Locale.getDefault());
        String title = new StringBuilder()
                .append(new LogDateModule(startDate).getMonthDayString())
                .append("-")
                .append(new LogDateModule(endDate).getMonthDayString())
                .toString();
        menu.findItem(R.id.pickDates).setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chart, menu);
        this.menu = menu;
//        setDateButton();
//        MenuItem editEntryDoneButton = menu.findItem(R.id.editEntryDoneButton);
//        editEntryDoneButton.setVisible(false);
//        refreshPickDateButton(getChartStartDate(), getChartEndDate());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.pickDates){
            EventBus.getDefault().post(new ChartEvents.OpenStartDateDialogEvent());
        }
        if (id == R.id.largeCells){
            PreferenceManager
                    .getDefaultSharedPreferences(this)
                    .edit()
                    .putInt(PreferencesContract.CELL_SIZE, CellView.Size.LARGE.sizeCode())
                    .apply();
        }
        if (id == R.id.mediumCells){
            PreferenceManager
                    .getDefaultSharedPreferences(this)
                    .edit()
                    .putInt(PreferencesContract.CELL_SIZE, CellView.Size.MEDIUM.sizeCode())
                    .apply();
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

    private void setUpModules() {
        populateMoodModule();
        populateNumModule();
    }

    private void populateMoodModule() {
        long id = ModuleContract.Mood.ID;
        final BoolItemTableHelper bHelper = new BoolItemTableHelper(this);
        int[] colors = getResources().getIntArray(R.array.mood_colors);
        Observable.range(0, Array.getLength(colors))
                .subscribeOn(Schedulers.io())
                .map(i -> new BoolComponent(id, "MoodComponent_" + String.valueOf(i),colors[i], true))
                .doOnNext(bHelper::insert)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void populateNumModule() {
        long id = ModuleContract.Num.ID;
        NumComponent[] components = new NumComponent[] {
          new NumComponent(id, "Anxiety", 0xFFFFFF, true, 3, 0),
          new NumComponent(id, "Irritability", 0xFFFFFF, true, 3, 0),
          new NumComponent(id, "Sleep", 0xFFFFFF, true, 24, 0)
        };

        final NumItemTableHelper nHelper = new NumItemTableHelper(this);
        Observable.from(components)
                .subscribeOn(Schedulers.io())
                .doOnNext(nHelper::insert)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

//    public void onEvent(ChartEvents.OpenEditEntryEvent event){
//        faButton.show();
//    }
//
//    public void onEvent(ChartEvents.OpenStartDateDialogEvent event){
//        DialogFragment d = new DateRangeDialog();
//        Bundle args = new Bundle();
//        args.putBoolean(DateRangeDialog.BOOL_IS_START_PICKER, true);
//        d.setArguments(args);
//        d.show(getFragmentManager(), "foo");
//    }
//
//    public void onEvent(ChartEvents.OpenEndDateDialogEvent event){
//        DialogFragment dialog = new DateRangeDialog();
//        Bundle args = new Bundle();
//        args.putBoolean(DateRangeDialog.BOOL_IS_START_PICKER,false);
//        args.putLong(DateRangeDialog.LONG_START_DATE_VALUE, event.getDate().getMillis());
//        Log.i("Date Range Dialog", "Start date chosen: " + event.getDate().toString("MM/dd/YYYY"));
//        dialog.setArguments(args);
//        dialog.show(getFragmentManager(), "bar");
//    }

//    public void onEvent(ChartEvents.SaveEndDateDialogEvent event){
//        boolean foo = event.isRememberDates();
//        Log.i("Date Range Dialog", "End date chosen: " + event.getEndDate().toString("MM/dd/YYYY"));
//        monthFragment.refreshColumns(new ChartEntryTable(this).getEntryGroup(getChartStartDate(), getChartEndDate()));
//        refreshPickDateButton(event.getStartDate(), event.getEndDate());
//
//        SharedPreferences settings = getPreferences(MODE_PRIVATE);
//        if (event.isRememberDates()){
//            settings
//                    .edit()
//                    .putBoolean(PreferencesContract.BOOL_CHART_REMEMBER_DATES,event.isRememberDates())
//                    .putLong(PreferencesContract.LONG_CHART_START_DATE,event.getStartDate().getMillis())
//                    .putLong(PreferencesContract.LONG_CHART_END_DATE,event.getEndDate().getMillis())
//                    .apply();
//        } else{
//            settings
//                    .edit()
//                    .putBoolean(PreferencesContract.BOOL_CHART_REMEMBER_DATES,event.isRememberDates())
//                    .apply();
//        }
//    }

//    public void onEvent(ChartEvents.OpenNoteEvent event){
//        NoteView noteView = (NoteView) findViewById(R.id.entryNoteView);
//        noteView.setEntry(event.getEntry());
//        noteView.setVisibility(View.VISIBLE);
//        if (faButton.isShown())
//            faButton.hide();
//        if (monthFragment.isEditEntryViewOpen())
//            noteView.setMode(ChartColumn.Mode.ENTRY_EDIT);
//        else
//            noteView.setMode(ChartColumn.Mode.ENTRY_READ);
//        noteView.animateUp();
//    }

//    public void onEvent(ChartEvents.CloseNoteEvent event){
//        if(monthFragment.isEditEntryViewOpen())
//            faButton.show();
//        NoteView noteView = (NoteView) findViewById(R.id.entryNoteView);
//        // TODO
//        ChartEntryTable.addOrUpdateEntry(this,event.getEntry());
//        noteView.animateDown();
//    }

    public void onEvent(ChartEvents.StartEndDatesLoaded event) {
        Log.i("refreshPickDateButton", "refreshing dates");
        refreshPickDateButton(event.start, event.end);
    }

    private void setDateButton() {
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        Observable.just("start", "end")
                .subscribeOn(Schedulers.io())
                .map(s -> {
                    DateTime result;
                    if (s.equals("start")) {
                        if(settings.getBoolean(PreferencesContract.BOOL_CHART_REMEMBER_DATES,false) && settings.contains(PreferencesContract.LONG_CHART_START_DATE)){
                            long millis = settings.getLong(PreferencesContract.LONG_CHART_START_DATE,0);
                            if (millis != 0)
                                result = new DateTime(millis);
                        }
                        result = DateTime.now().withDayOfMonth(1);
                    } else {
                        if(settings.getBoolean(PreferencesContract.BOOL_CHART_REMEMBER_DATES,false)){
                            if(settings.contains(PreferencesContract.LONG_CHART_END_DATE)){
                                long millis = settings.getLong(PreferencesContract.LONG_CHART_END_DATE,0);
                                if (millis != 0)
                                    result = new DateTime(millis);
                            }
                        }
                        result = DateTime.now().dayOfMonth().withMaximumValue();
                    }
                    return result;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DateTime>() {
                    DateTime start = null;
                    DateTime end = null;
                    @Override
                    public void onCompleted() {
                        EventBus.getDefault().post(new ChartEvents.StartEndDatesLoaded(start, end));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(DateTime dateTime) {
                        if (start == null) {
                            start = dateTime;
                        } else {
                            end = dateTime;
                        }
                    }
                });

    }

}
