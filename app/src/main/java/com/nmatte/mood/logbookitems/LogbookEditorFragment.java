package com.nmatte.mood.logbookitems;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.logbookitems.boolitems.EditBoolItem;
import com.nmatte.mood.logbookitems.numitems.EditNumItem;
import com.nmatte.mood.moodlog.R;

import java.util.ArrayList;

public class LogbookEditorFragment extends Fragment {
    LinearLayout mainLayout;
    LinearLayout boolItemLayout;
    LinearLayout numItemLayout;
    ImageButton addBoolButton;
    ImageButton addNumButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_logbook_editor,container);
        mainLayout = (LinearLayout) mainView.findViewById(R.id.logbookEditorLayout);
        boolItemLayout = (LinearLayout) mainView.findViewById(R.id.boolItemList);
        numItemLayout = (LinearLayout) mainView.findViewById(R.id.numItemList);
        addBoolButton = (ImageButton) mainView.findViewById(R.id.addBoolButton);
        addBoolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewBoolItem();
            }
        });
        addNumButton = (ImageButton) mainView.findViewById(R.id.addNumButton);
        addNumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewNumItem();
            }
        });

        ArrayList<BoolItem> boolItems = BoolItemTableHelper.getAll(getActivity());
        for (final BoolItem item : boolItems){
            EditBoolItem rowView = new EditBoolItem(getActivity(),item);
            boolItemLayout.addView(rowView);
        }


        return mainView;
    }



    public void addNewBoolItem(){
        final EditBoolItem rowView = new EditBoolItem(getActivity());
        rowView.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolItemLayout.removeView(rowView);
            }
        });
        rowView.itemName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                }
                return false;
            }
        });

        boolItemLayout.addView(rowView);
    }

    public void addNewNumItem(){
        final EditNumItem rowView = new EditNumItem(getActivity());
        rowView.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numItemLayout.removeView(rowView);
            }
        });
        numItemLayout.addView(rowView);

    }
}
