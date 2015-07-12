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
import com.nmatte.mood.logbookentries.MainFragment;
import com.nmatte.mood.medications.AddMedicationDialog;
import com.nmatte.mood.medications.DeleteMedicationDialog;
import com.nmatte.mood.medications.MedTableHelper;
import com.nmatte.mood.medications.Medication;
import com.nmatte.mood.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity
    extends ActionBarActivity
    implements AddMedicationDialog.AddMedicationListener,
        DeleteMedicationDialog.DeleteMedicationListener
{

    private LogbookEntry currentEntry;

    static final String
        CHART_ACTIVITY = "Monthly Chart",
        SETTINGS_ACTIVITY="Settings",
        MAIN_ACTIVITY="Today";
    MainFragment mainFragment;
    ListView navList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNavbar();
        initFragments();

        currentEntry = LogbookEntryTableHelper.getEntryToday(this);
        if (currentEntry == null) {
            currentEntry = new LogbookEntry();
        }
        else {
            mainFragment.setEntry(currentEntry);
        }
    }

    private void initNavbar(){
        navList = (ListView) findViewById(R.id.drawerList);
        final ArrayList<String> navItems = new ArrayList<>();

        navItems.add(MAIN_ACTIVITY);
        navItems.add(CHART_ACTIVITY);
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

    private void startSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void initFragments(){
        mainFragment = (MainFragment) getFragmentManager().findFragmentById(R.id.mainFragment);
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

    // Listener for the add medication dialog.
    @Override
    public void onAddDialogPositiveClick(String name) {
        updateCurrentEntry();
        MedTableHelper.addMedication(this, name);
        mainFragment.setEntry(currentEntry);

    }

    public void onDeleteDialogPositiveClick(String name) {
        updateCurrentEntry();
        MedTableHelper.deleteMedication(this, name);
        mainFragment.setEntry(currentEntry);
    }
    public void delete(Medication m) {
        String name = m.getName();
        long id = m.getID();
        Bundle b = new Bundle();
        b.putCharSequence("name",name);
        b.putLong("id",id);

        DialogFragment dialog = new DeleteMedicationDialog();
        dialog.setArguments(b);
        dialog.show(getFragmentManager(), "Delete Med Dialog");
    }
    public void addNew() {
        DialogFragment dialog = new AddMedicationDialog();
        dialog.show(getFragmentManager(), "Add Med Dialog");
    }

    private void updateCurrentEntry(){
        currentEntry = mainFragment.getEntry();
        currentEntry.setDate(Calendar.getInstance());
    }

    private void saveCurrentEntry(){
        updateCurrentEntry();
        LogbookEntryTableHelper.addOrUpdateEntry(this, currentEntry);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveCurrentEntry();
    }


}
