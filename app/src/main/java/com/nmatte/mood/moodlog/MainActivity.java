package com.nmatte.mood.moodlog;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nmatte.mood.chart.ChartActivity;
import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.logbookentries.LogbookEntryTableHelper;
import com.nmatte.mood.medications.AddMedicationDialog;
import com.nmatte.mood.medications.DeleteMedicationDialog;
import com.nmatte.mood.medications.MedList;
import com.nmatte.mood.medications.Medication;

import java.util.ArrayList;


public class MainActivity
    extends ActionBarActivity
    implements AddMedicationDialog.AddMedicationListener,
        DeleteMedicationDialog.DeleteMedicationListener,
        MedList.MedListLongClickListener
{

    private LogbookEntry currentEntry;

    static final String
        CHART_ACTIVITY = "Monthly Chart",
        SETTINGS_ACTIVITY="Settings",
        MAIN_ACTIVITY="Today",
        HISTORY_ACTIVITY="History";

    SelectorFragment selectorFragment;
    MainFragment mainFragment;
    ListView navList;
    LogbookEntryTableHelper LEHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNavbar();
        initFragments();

        LEHelper = new LogbookEntryTableHelper(this);
        currentEntry = LEHelper.getEntryToday();
        if (currentEntry == null) {
            currentEntry = new LogbookEntry();
        }
        else {
            selectorFragment.setCheckedItems(currentEntry);
            mainFragment.setValues(currentEntry);
        }
    }

    private void initNavbar(){
        navList = (ListView) findViewById(R.id.drawerList);
        final ArrayList<String> navItems = new ArrayList<>();

        navItems.add(MAIN_ACTIVITY);
        navItems.add(CHART_ACTIVITY);
        navItems.add(HISTORY_ACTIVITY);
        navItems.add(SETTINGS_ACTIVITY);
        navList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
        View header = getLayoutInflater().inflate(R.layout.navlist_header,null);
        navList.addHeaderView(header);
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        startChartActivity();
                        break;
                    case 3:
                        startHistoryActivity();
                        break;
                    case 4:
                        startSettingsActivity();
                        break;
                }

            }
        });
    }

    private void startChartActivity(){
         Intent intent = new Intent(this, ChartActivity.class);
         startActivity(intent);
    }

    private void startHistoryActivity() {
        //TODO
    }

    private void startSettingsActivity(){
        //TODO
    }

    private void initFragments(){
        mainFragment = (MainFragment) getFragmentManager().findFragmentById(R.id.mainFragment);
        selectorFragment = (SelectorFragment) getFragmentManager().findFragmentById(R.id.selectorFragment);
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    // From the add medication button. Starts add medication dialog.
    // TODO: request keyboard focus for dialog?
    public void addMedication(View view) {
        DialogFragment dialog = new AddMedicationDialog();
        dialog.show(getFragmentManager(), "Add Med Dialog");
    }

    // Listener for the add medication dialog.
    @Override
    public void onAddDialogPositiveClick(String name) {
        mainFragment.addMed(name);
    }

    public void onDeleteDialogPositiveClick(String name) {
        mainFragment.deleteMed(name);
    }

    @Override
    public void deleteMedication(Medication m) {
        String name = m.getName();
        long id = m.getID();
        Bundle b = new Bundle();
        b.putCharSequence("name",name);
        b.putLong("id",id);

        DialogFragment dialog = new DeleteMedicationDialog();
        dialog.setArguments(b);
        dialog.show(getFragmentManager(), "Delete Med Dialog");
    }

    private void updateCurrentEntry(){
        currentEntry = selectorFragment.updateEntry(currentEntry);
        currentEntry = mainFragment.updateEntry(currentEntry);
        currentEntry.setDateCurrent();
    }

    private void saveCurrentEntry(){
        updateCurrentEntry();
        LEHelper.addOrUpdateEntry(currentEntry);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveCurrentEntry();
    }


}
