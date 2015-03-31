package com.nmatte.mood.moodlog;

import android.app.DialogFragment;
import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.medications.AddMedicationDialog;
import com.nmatte.mood.medications.DeleteMedicationDialog;
import com.nmatte.mood.medications.MedListAdapter;
import com.nmatte.mood.medications.MedTableHelper;
import com.nmatte.mood.medications.Medication;

import static java.lang.Integer.parseInt;


// TODO: add log table
// TODO: add recyclerview of past dates
// TODO: add chart view of logs

public class MainActivity
       extends ActionBarActivity
       implements DeleteMedicationDialog.DeleteMedicationListener,
                  AddMedicationDialog.AddMedicationListener
                  {

    private CustomNumberPicker anxPicker, irrPicker, hoursPicker;

    private LogbookEntry currentEntry;

    static final int GET_MOOD_STRING = 1;


    MedTableHelper MTHelper;
    ListView listView;
    ArrayAdapter<String> medNames;
    MedListAdapter medAdapter;

    SelectorButtonFragment buttonFragment;
    SelectorFragment selectorFragment;
    Fragment primaryFragment;

    boolean doneIsVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MTHelper = new MedTableHelper(this);
        currentEntry = new LogbookEntry();

        buttonFragment = (SelectorButtonFragment) getFragmentManager().findFragmentById(R.id.buttonFragment);
        selectorFragment = new SelectorFragment();
    //    primaryFragment = new PrimaryFragment();

        getFragmentManager().beginTransaction()
                .add(R.id.frame,selectorFragment)
                .hide(selectorFragment)
                .commit();





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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_done){
            hideFragment();
            currentEntry.setMoodString(selectorFragment.getResultString());
            buttonFragment.setMoodString(currentEntry.getMoodString());

            return true;
        }

        if(id == R.id.show_info){
            showInfo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_done).setVisible(doneIsVisible);
        return true;
    }

                      private void deleteMedAtPosition(int position) {
   //     String name = MTHelper.getMedNames().get(position);
        Medication m = (Medication) listView.getAdapter().getItem(position);
        String name = m.getName();
        long id = m.getID();


        Bundle b = new Bundle();
        b.putCharSequence("name",name);
        b.putLong("id",id);

        DialogFragment dialog = new DeleteMedicationDialog();
        dialog.setArguments(b);
        dialog.show(getFragmentManager(),"Delete Med Dialog");
    }

    @Override
    public void onDeleteDialogPositiveClick(String name) {
        MTHelper.deleteMedication(name);
        medAdapter.setMedications(MTHelper.getMedications());
        medAdapter.notifyDataSetChanged();

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
        MTHelper.addMedication(name);
        medAdapter.setMedications(MTHelper.getMedications());
        medAdapter.notifyDataSetChanged();
    }


    public void showInfo() {

        currentEntry.setAnxValue(anxPicker.getCurrentNum());
        currentEntry.setIrrValue(irrPicker.getCurrentNum());
        currentEntry.setHoursSleptValue(hoursPicker.getCurrentNum());

        String text = currentEntry.getSummaryString();
        Toast.makeText(this, text,Toast.LENGTH_SHORT).show();
    }

    public void showFragment(View view) {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.expand, R.animator.slide_down)
                .show(selectorFragment)

                .addToBackStack(null)
                .commit();
        doneIsVisible = true;
        invalidateOptionsMenu();
    }


    public void hideFragment(){
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.expand, R.animator.collapse)
                .hide(selectorFragment)
                .commit();
        doneIsVisible = false;
        invalidateOptionsMenu();

    }
}
