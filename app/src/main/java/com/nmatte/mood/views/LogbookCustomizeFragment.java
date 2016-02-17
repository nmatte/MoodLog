package com.nmatte.mood.views;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.nmatte.mood.controllers.SaveBoolItemEvent;
import com.nmatte.mood.controllers.SaveNumItemEvent;
import com.nmatte.mood.database.BoolItemTableHelper;
import com.nmatte.mood.database.NumItemTableHelper;
import com.nmatte.mood.models.BoolComponent;
import com.nmatte.mood.models.NumComponent;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.settings.PreferencesContract;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class LogbookCustomizeFragment extends Fragment {
    CheckBox largeMoodModuleCheckbox;
    CheckBox miniMoodModuleCheckbox;
    CheckBox noteModuleCheckbox;
    LinearLayout mainLayout;
    LinearLayout boolItemLayout;
    LinearLayout numItemLayout;
    ImageButton addBoolButton;
    ImageButton addNumButton;
    EditText addNumEditText;
    EditText addBoolEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View mainView = inflater.inflate(R.layout.fragment_logbook_customize,container);
        initLayout(mainView);

        ArrayList<NumComponent> numItems = NumItemTableHelper.getAll(getActivity());
        for (final NumComponent item : numItems){
            addNewNumItem(item,false);
        }

        ArrayList<BoolComponent> boolItems = BoolItemTableHelper.getAll(getActivity());
        for (final BoolComponent item : boolItems){
            addNewBoolItem(item);
        }

        return mainView;
    }

    private void initLayout(View mainView){
        mainLayout = (LinearLayout) mainView.findViewById(R.id.logbookEditorLayout);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());


        // MODULES
        largeMoodModuleCheckbox = (CheckBox) mainView.findViewById(R.id.largeMoodModuleCheckbox);
        largeMoodModuleCheckbox.setChecked(
                prefs.getBoolean(PreferencesContract.FULL_MOOD_MODULE_ENABLED, false)
        );

        miniMoodModuleCheckbox = (CheckBox) mainView.findViewById(R.id.miniMoodModuleCheckbox);
        miniMoodModuleCheckbox.setChecked(
                prefs.getBoolean(PreferencesContract.MINI_MOOD_MODULE_ENABLED, false)
        );

        noteModuleCheckbox = (CheckBox) mainView.findViewById(R.id.noteModuleCheckbox);
        noteModuleCheckbox.setChecked(
                prefs.getBoolean(PreferencesContract.NOTE_MODULE_ENABLED,false)
        );


        // NUM ITEMS
        numItemLayout = (LinearLayout) mainView.findViewById(R.id.numItemList);
        addNumButton = (ImageButton) mainView.findViewById(R.id.addNumButton);
        addNumEditText = (EditText) mainView.findViewById(R.id.addNumEditText);
        addNumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewNumItem(new NumComponent(null, addNumEditText.getText().toString()), true);
                addNumEditText.getText().clear();
            }
        });


        // BOOL ITEMS
        boolItemLayout = (LinearLayout) mainView.findViewById(R.id.boolItemList);
        addBoolButton = (ImageButton) mainView.findViewById(R.id.addBoolButton);
        addBoolEditText = (EditText) mainView.findViewById(R.id.addBoolEditText);
        addBoolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewBoolItem(new BoolComponent(addBoolEditText.getText().toString()));
                addBoolEditText.getText().clear();
            }
        });



        // hide keyboard on activity start
        InputMethodManager inputManager =
                (InputMethodManager) getActivity().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                addNumEditText.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }



    public void addNewBoolItem(BoolComponent item){
        final CustomizeBoolItem rowView = new CustomizeBoolItem(getActivity(),item);
        rowView.delButton.setOnClickListener(getDeleteBoolListener(rowView));
        boolItemLayout.addView(rowView);
    }

    public void addNewNumItem(NumComponent item,boolean isNewItem){
        if (isNewItem){
            final CustomizeNumItem rowView = new CustomizeNumItem(getActivity(),null);
            rowView.setNumItem(item);
            rowView.delButton.setOnClickListener(getDeleteNumItemListener(rowView));
            numItemLayout.addView(rowView);
        } else {
            final CustomizeNumItem rowView = new CustomizeNumItem(getActivity(),item);
            rowView.delButton.setOnClickListener(getDeleteNumItemListener(rowView));
            numItemLayout.addView(rowView);
        }

    }


    private View.OnClickListener getDeleteBoolListener(final CustomizeBoolItem customizeBoolItem){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int viewIndex = boolItemLayout.indexOfChild(customizeBoolItem);
                boolItemLayout.removeView(customizeBoolItem);
                String text = "Deleted " + customizeBoolItem.itemName.getText();
                Snackbar.make(mainLayout,text, Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolItemLayout.addView(customizeBoolItem,viewIndex);
                            }
                        })
                        .show();
            }
        };
    }

    private View.OnClickListener getDeleteNumItemListener(final CustomizeNumItem customizeNumItem){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int viewIndex = numItemLayout.indexOfChild(customizeNumItem);
                numItemLayout.removeView(customizeNumItem);
                String text = "Deleted " + customizeNumItem.itemName.getText();
                Snackbar.make(mainLayout,text,Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                numItemLayout.addView(customizeNumItem,viewIndex);
                            }
                        })
                        .show();
            }
        };
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(SaveBoolItemEvent event){
        BoolComponent saved = BoolItemTableHelper.save(getActivity(), event.getItem());
        if (saved == null){
            Log.i("BoolItemTableHelper", "failed to save item");
        }
        Log.i("BoolComponent saved", "Saved BoolComponent" + saved.getName() + " with ID " + saved.getID().toString());
    }

    public void onEvent(SaveNumItemEvent event){
        NumComponent saved = NumItemTableHelper.save(getActivity(), event.getItem());
        Log.i("NumComponent saved", "Saved NumComponent"  + saved.getName() + " with ID " + saved.getID().toString());
    }


}
