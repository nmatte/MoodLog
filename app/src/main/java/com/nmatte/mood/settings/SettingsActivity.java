package com.nmatte.mood.settings;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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
        enableFullMoodModule(checkBox.isChecked());

        CheckBox miniCheckbox = (CheckBox) findViewById(R.id.miniMoodModuleCheckbox);
        if (miniCheckbox.isChecked() && checkBox.isChecked()){
            miniCheckbox.setChecked(false);
            enableMiniMoodModule(false);
        }


    }

    private void enableFullMoodModule(boolean enabled){
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putBoolean(PreferencesContract.FULL_MOOD_MODULE_ENABLED,enabled)
                .apply();
    }

    public void miniMoodModuleCheckboxClick(View view) {
        CheckBox checkBox = (CheckBox) view;
        enableMiniMoodModule(checkBox.isChecked());

        CheckBox fullCheckbox = (CheckBox) findViewById(R.id.largeMoodModuleCheckbox);

        if (fullCheckbox.isChecked() && checkBox.isChecked()){
            fullCheckbox.setChecked(false);
            enableFullMoodModule(false);
        }
    }

    private void enableMiniMoodModule(boolean enabled){
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putBoolean(PreferencesContract.MINI_MOOD_MODULE_ENABLED,enabled)
                .apply();
    }

    public void noteModuleCheckboxClick(View view) {
        CheckBox checkBox = (CheckBox) view;
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putBoolean(PreferencesContract.NOTE_MODULE_ENABLED, checkBox.isChecked())
                .apply();

    }




}
