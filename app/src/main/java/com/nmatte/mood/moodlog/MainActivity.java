package com.nmatte.mood.moodlog;

import android.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

// TODO: add mood level selector
// TODO: add log table
// TODO: add recyclerview of past dates
// TODO: add chart view of logs

public class MainActivity
       extends ActionBarActivity
       implements DeleteMedicationDialog.DeleteMedicationListener,
                  AddMedicationDialog.AddMedicationListener {

    MedTableHelper MTHelper;
    ListView listView;
    ArrayAdapter<String> medNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MTHelper = new MedTableHelper(this);

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
        medNames = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice);
        medNames.addAll(MTHelper.getMedNames());
        listView.setAdapter(medNames);


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
        String name = MTHelper.getMedNames().get(position);

        Bundle b = new Bundle();
        b.putCharSequence("name",name);

        DialogFragment dialog = new DeleteMedicationDialog();
        dialog.setArguments(b);
        dialog.show(getFragmentManager(),"Delete Med Dialog");
    }

    @Override
    public void onDeleteDialogPositiveClick(String name) {
        MTHelper.deleteMedication(name);
        medNames.clear();
        medNames.addAll(MTHelper.getMedNames());
        medNames.notifyDataSetChanged();

    }

    public void addMedication(View view) {
        DialogFragment dialog = new AddMedicationDialog();
        dialog.show(getFragmentManager(), "Add Med Dialog");
    }

    @Override
    public void onAddDialogPositiveClick(String name) {
        MTHelper.addMedication(name);
        medNames.clear();
        medNames.addAll(MTHelper.getMedNames());
        medNames.notifyDataSetChanged();

    }
}
