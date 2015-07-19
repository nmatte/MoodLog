package com.nmatte.mood.logbookentries;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.util.CalendarDatabaseUtil;

import java.util.Calendar;

public class SingleEntryActivity extends Activity {
    public static String DATE_INT_TAG = "dateInt";
    public static String INTENT_FROM_OTHER_ACTIVITY = "intentFromOtherActivity";

    LogbookEntryFragment entryFragment;
    Calendar date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().hasExtra(DATE_INT_TAG)){
            date = CalendarDatabaseUtil.intToCalendar(getIntent().getIntExtra(DATE_INT_TAG,CalendarDatabaseUtil.calendarToInt(Calendar.getInstance())));
        }
        this.setContentView(R.layout.activity_single_entry);
        initFragments();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    }

    private void initFragments(){
        entryFragment = (LogbookEntryFragment) getFragmentManager().findFragmentById(R.id.singleEntryFragment);
        LogbookEntry entry = LogbookEntryTableHelper.getEntry(this,CalendarDatabaseUtil.calendarToInt(date));
        if (entry == null){
            entry = new LogbookEntry(date);
        }
        entryFragment.setEntry(entry);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void saveAndClose(){



    }

    public void testClick(View view) {
        if (getIntent().getAction().equals(INTENT_FROM_OTHER_ACTIVITY)){
            finish();
        } else {
            LogbookEntryTableHelper.addOrUpdateEntry(this, entryFragment.getEntry());
        }
    }
}