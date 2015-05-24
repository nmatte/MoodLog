package com.nmatte.mood.chart;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nmatte.mood.logbookentries.LogbookEntry;
import com.nmatte.mood.moodlog.R;

import java.util.Random;


public class ChartActivity extends Activity {

    final String [] labels = {"Date","Severe","","Moderate","","Mild","","Normal","","Mild","","Moderate",
            "","Severe","Anxiety","Irritability","Hours Slept"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);


        final LinearLayout m = (LinearLayout) findViewById(R.id.mainLayout);
        final ColumnView labelColumn = new ColumnView(this,null,0,labels);
        final HorizontalScrollView scrollView = new HorizontalScrollView(this);

        m.post(new Runnable() {
            @Override
            public void run() {
                float labelColumnWidth = m.getWidth()/4;
                float scrollViewWidth = m.getWidth() - labelColumnWidth;
                labelColumn.getLayoutParams().width = (int) labelColumnWidth;
                scrollView.getLayoutParams().width = (int) scrollViewWidth;

            }
        });
        m.addView(labelColumn);
        m.addView(scrollView);
        LinearLayout l = new LinearLayout(this);
        scrollView.addView(l);

        Random RNG = new Random();
        final ColumnView [] columns = new ColumnView[28];

        for(int i = 0; i < columns.length; i++){
            LogbookEntry e = new LogbookEntry();
            e.setAnxValue(RNG.nextInt(4));
            e.setIrrValue(RNG.nextInt(4));
            e.setHoursSleptValue(RNG.nextInt(25));
            e.setDate(i+1);
            columns[i] = new ColumnView(getApplicationContext(),e,0,null);
        }
        l.post(new Runnable(){
            public void run(){
                float width = m.getWidth()/14;
                for(int i = 0; i < columns.length; i++){
                    columns[i].getLayoutParams().width = (int) width;
                }

            }
        });
        for(ColumnView c : columns){
            l.addView(c);
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


        return super.onOptionsItemSelected(item);
    }
}
