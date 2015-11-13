package com.nmatte.mood.logbookitems;

import android.app.Fragment;
import android.content.Context;
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

import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.logbookitems.boolitems.CustomizeBoolItem;
import com.nmatte.mood.logbookitems.boolitems.SaveBoolItemEvent;
import com.nmatte.mood.logbookitems.numitems.CustomizeNumItem;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.logbookitems.numitems.NumItemTableHelper;
import com.nmatte.mood.logbookitems.numitems.SaveNumItemEvent;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.settings.PreferencesContract;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class LogbookCustomizeFragment extends Fragment {
    CheckBox largeMoodModuleCheckbox;
    LinearLayout mainLayout;
    LinearLayout boolItemLayout;
    LinearLayout numItemLayout;
    ImageButton addBoolButton;
    ImageButton addNumButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View mainView = inflater.inflate(R.layout.fragment_logbook_customize,container);
        initLayout(mainView);

        ArrayList<NumItem> numItems = NumItemTableHelper.getAll(getActivity());
        for (final NumItem item : numItems){
            addNewNumItem(item,false);
        }

        ArrayList<BoolItem> boolItems = BoolItemTableHelper.getAll(getActivity());
        for (final BoolItem item : boolItems){
            addNewBoolItem(item);
        }

        return mainView;
    }

    private void initLayout(View mainView){
        mainLayout = (LinearLayout) mainView.findViewById(R.id.logbookEditorLayout);

        largeMoodModuleCheckbox = (CheckBox) mainView.findViewById(R.id.largeMoodModuleCheckbox);
        boolean largeMoodModuleEnabled = PreferenceManager
                .getDefaultSharedPreferences(getActivity())
                .getBoolean(PreferencesContract.LARGE_MOOD_MODULE_ENABLED,false);
        largeMoodModuleCheckbox.setChecked(largeMoodModuleEnabled);

        boolItemLayout = (LinearLayout) mainView.findViewById(R.id.boolItemList);
        numItemLayout = (LinearLayout) mainView.findViewById(R.id.numItemList);

        addBoolButton = (ImageButton) mainView.findViewById(R.id.addBoolButton);
        addBoolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewBoolItem(null);
            }
        });

        addNumButton = (ImageButton) mainView.findViewById(R.id.addNumButton);
        final EditText addNumEditText = (EditText) mainView.findViewById(R.id.addNumEditText);

        addNumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewNumItem(new NumItem(null, addNumEditText.getText().toString()), true);
                addNumEditText.clearComposingText();
            }
        });


        InputMethodManager inputManager =
                (InputMethodManager) getActivity().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                addNumEditText.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }



    public void addNewBoolItem(BoolItem item){
        final CustomizeBoolItem rowView = new CustomizeBoolItem(getActivity(),item);
        rowView.delButton.setOnClickListener(getDeleteBoolListener(rowView));
        boolItemLayout.addView(rowView);
    }

    public void addNewNumItem(NumItem item,boolean isNewItem){
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
        BoolItem saved = BoolItemTableHelper.insertOrUpdate(getActivity(),event.getItem());
        if (saved == null){
            Log.i("BoolItemTableHelper", "failed to save item");
        }
        Log.i("BoolItem saved", "Saved BoolItem" + saved.getName() + " with ID " + saved.getID().toString());
    }

    public void onEvent(SaveNumItemEvent event){
        NumItem saved = NumItemTableHelper.insertOrUpdate(getActivity(),event.getItem());
        Log.i("NumItem saved", "Saved NumItem"  + saved.getName() + " with ID " + saved.getID().toString());
    }


}
