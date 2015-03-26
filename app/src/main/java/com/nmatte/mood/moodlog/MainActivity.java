package com.nmatte.mood.moodlog;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
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
                  AddMedicationDialog.AddMedicationListener,
                  SeekBar.OnSeekBarChangeListener{

    private LogbookEntry currentEntry;

    TextView irrLabel;
    TextView anxLabel;
    TextView hoursSleptLabel;

    static final int GET_MOOD_STRING = 1;


    MedTableHelper MTHelper;
    ListView listView;
    ArrayAdapter<String> medNames;
    MedListAdapter medAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MTHelper = new MedTableHelper(this);

        irrLabel = (TextView) findViewById(R.id.irritabilityLabel);
        anxLabel = (TextView) findViewById(R.id.anxietyLabel);
        hoursSleptLabel = (TextView) findViewById(R.id.hoursSleptLabel);

        SeekBar irrSeekBar = (SeekBar) findViewById(R.id.irrSeekBar);
        SeekBar anxSeekBar = (SeekBar) findViewById(R.id.anxSeekBar);
        SeekBar hoursSleptSeekBar = (SeekBar) findViewById(R.id.hoursSleptSeekBar);

        irrSeekBar.setOnSeekBarChangeListener(this);
        anxSeekBar.setOnSeekBarChangeListener(this);
        hoursSleptSeekBar.setOnSeekBarChangeListener(this);

        currentEntry = new LogbookEntry();


        listView = (ListView) findViewById(R.id.listView);
        // Listener for long press on an item in the medication list (to delete)
        AdapterView.OnItemLongClickListener l = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteMedAtPosition(position);
                return true;
            }
        };
        listView.setOnItemLongClickListener(l);
        View v = this.getLayoutInflater().inflate(R.layout.medication_list_footer,null);
        listView.addFooterView(v);
        medAdapter = new MedListAdapter(MTHelper.getMedications(),this);
        listView.setAdapter(medAdapter);


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

        return super.onOptionsItemSelected(item);
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
      //  medNames.clear();
       // medNames.addAll(MTHelper.getMedNames());
      //  medNames.notifyDataSetChanged();

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
       // medNames.clear();
     //   medNames.addAll(MTHelper.getMedNames());
   //     medNames.notifyDataSetChanged();
    }

    // Seekbar listeners
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        switch (seekBar.getId()){
            case R.id.irrSeekBar:
                currentEntry.setIrrValue(progress);
                irrLabel.setText("Irritability: " + progress);
                break;
            case R.id.anxSeekBar:
                currentEntry.setAnxValue(progress);
                anxLabel.setText("Anxiety: " + progress);
                break;
            case R.id.hoursSleptSeekBar:
                currentEntry.setHoursSleptValue(progress);
                hoursSleptLabel.setText("Hours slept last night: " + progress);
                break;
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //no-op
     }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //no-op
    }

    // Start mood selector dialog.
    public void showMoodSelector(View v){
        Intent intent = new Intent(this, SelectorActivity.class);
        startActivityForResult(intent,GET_MOOD_STRING);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = "";
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){

            result =(String) data.getCharSequenceExtra("result");
            currentEntry.setMoodString(result);
            GraphColumnView gcv = (GraphColumnView) findViewById(R.id.moodPreview);
            gcv.setMoodString(currentEntry.getMoodString());
        }


    }

    public void showInfo(View view) {

        String text = currentEntry.getSummaryString();
        Toast.makeText(this, text,Toast.LENGTH_SHORT).show();
    }
}
