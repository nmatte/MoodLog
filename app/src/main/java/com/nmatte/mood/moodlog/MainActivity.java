package com.nmatte.mood.moodlog;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.nmatte.mood.chart.ChartActivity;
import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.medications.AddMedicationDialog;
import com.nmatte.mood.medications.DeleteMedicationDialog;
import com.nmatte.mood.medications.MedList;
import com.nmatte.mood.medications.Medication;


// TODO: add log table
// TODO: add recyclerview of past dates
// TODO: add chart view of logs

public class MainActivity
       extends ActionBarActivity
       implements AddMedicationDialog.AddMedicationListener,
        DeleteMedicationDialog.DeleteMedicationListener,
        MedList.MedListLongClickListener

                  {

    private LogbookEntry currentEntry = new LogbookEntry();

    static final int GET_MOOD_STRING = 1;

    static final String SELECTOR_FRAGMENT_TAG = "selector",
            CHART_ACTIVITY = "Monthly Chart",SETTINGS_ACTIVITY="Settings",MAIN_ACTIVITY="Today";

    SelectorButtonFragment buttonFragment;
    SelectorFragment selectorFragment;
    MainFragment mainFragment;
    ListView navList;

    boolean doneIsVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navList = (ListView) findViewById(R.id.drawerList);
        final String [] navItems = new String[] {CHART_ACTIVITY,SETTINGS_ACTIVITY,MAIN_ACTIVITY};
        navList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
        View header = getLayoutInflater().inflate(R.layout.navlist_header,null);
        navList.addHeaderView(header);
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        break;
                    case 1:
                        startChartActivity();
                        break;
                }

            }
        });
        initFragments();
 }

     private void startChartActivity(){
         Intent intent = new Intent(this, ChartActivity.class);
         startActivity(intent);
     }

    private void initFragments(){
        buttonFragment = (SelectorButtonFragment) getFragmentManager().findFragmentById(R.id.buttonFragment);
        mainFragment = (MainFragment) getFragmentManager().findFragmentById(R.id.mainFragment);
        selectorFragment = (SelectorFragment) getFragmentManager().findFragmentByTag(SELECTOR_FRAGMENT_TAG);
        if(selectorFragment == null) {
            selectorFragment = new SelectorFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.mainFrame, selectorFragment, SELECTOR_FRAGMENT_TAG)
                    .hide(selectorFragment)
                    .commit();
        } else {
            getFragmentManager()
                    .beginTransaction()
                    .hide(selectorFragment)
                    .commit();
        }
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

        if(id == R.id.action_done){
            hideFragment();
            updateMoods();

            return true;
        }

        if(id == R.id.show_info){
            showInfo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateMoods() {
        // TODO: refactor using ArrayList<Boolean>
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_done).setVisible(doneIsVisible);
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


    public void showInfo() {
        String text = currentEntry.getSummaryString();
        Toast.makeText(this, text,Toast.LENGTH_SHORT).show();
    }



    public void showFragment(View view) {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        selectorFragment.getView().getLayoutParams().width = buttonFragment.getView().getWidth();
        getFragmentManager()
            .beginTransaction()
            .setCustomAnimations(R.animator.slide_left, R.animator.slide_right)
            .show(selectorFragment)

            .addToBackStack(null)
            .commit();
        doneIsVisible = true;
        invalidateOptionsMenu();
}

    public void hideFragment(){
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_left, R.animator.slide_right)
                .hide(selectorFragment)
                .commit();
        doneIsVisible = false;
        invalidateOptionsMenu();

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
        dialog.show(getFragmentManager(),"Delete Med Dialog");
    }
}
