package com.nmatte.mood.moodlog;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class SelectorActivity extends ActionBarActivity {

    final int [] colors = {0xFFFFE0D4,0xFFFFBBA7,0xFFFFE1C8,0xFFFFE0AD,0xFFFFF9B3,0xFFDDEDCF,
            0xFFC8E4B2, 0xFFAEDCB6,0xFFC5E7DB,0xFFB5E3E6,0xFFA3E0F7,0xFFABC2E3,0xFFE0DBEC};
    final String [] labels = {"Severe","","Moderate","","Mild","","Normal","","Mild","","Moderate",
            "","Severe"};

    SelectorAdapter adapter;
    String resultData  = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter = new SelectorAdapter(labels,colors,this);
        listView.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_selector, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case (R.id.action_done):
                doneSelecting();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void doneSelecting(){
        resultData = adapter.getCheckedItems();
        Intent intent = new Intent().putExtra("result", resultData);
        setResult(RESULT_OK,intent);
        finish();
    }

    public void showChecked(View view) {
        resultData = adapter.getCheckedItems();
        Intent intent = new Intent().putExtra("result", resultData);
        setResult(RESULT_OK,intent);
        finish();
    }


}
