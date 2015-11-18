package com.nmatte.mood.settings;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.nmatte.mood.chart.ChartActivity;
import com.nmatte.mood.moodlog.R;

public class SettingsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }



    private void startChartActivity(){
        Intent intent = new Intent(this, ChartActivity.class);
        startActivity(intent);
    }


    public void notificationTest(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
               .setSmallIcon(R.drawable.notification_icon)
               .setContentTitle("Test!")
               .setContentText("TEEEEEEEST");

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(001, builder.build());

    }



    public void onSettingsItemClick(MenuItem item) {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerLayout.closeDrawers();
    }

    public void onRemindersItemClick(MenuItem item) {
    }

    public void onTestItemClick(MenuItem item) {
    }

    public void largeMoodModuleCheckboxClick(View view) {
        CheckBox checkBox = (CheckBox) view;
        SharedPreferences.Editor ed = PreferenceManager.getDefaultSharedPreferences(this)
                .edit();

        ed.putBoolean(PreferencesContract.LARGE_MOOD_MODULE_ENABLED, checkBox.isChecked());
        boolean success = ed.commit();

        if(success)
            Log.i("Mood module prefs","success changing mood module prefs");

    }

    public void noteModuleCheckboxClick(View view) {
        CheckBox checkBox = (CheckBox) view;
        SharedPreferences.Editor ed = PreferenceManager.getDefaultSharedPreferences(this)
                .edit();
        ed.putBoolean(PreferencesContract.NOTE_MODULE_ENABLED, checkBox.isChecked());
        ed.commit();

    }
}
