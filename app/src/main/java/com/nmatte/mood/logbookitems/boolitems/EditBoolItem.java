package com.nmatte.mood.logbookitems.boolitems;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nmatte.mood.moodlog.R;

public class EditBoolItem extends RelativeLayout {
    BoolItem boolItem = null;
    public EditText itemName;
    public ImageButton delButton;
    public ImageButton saveButton;
    public ImageButton editButton;
    Context context;


    public EditBoolItem(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public EditBoolItem(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.context = context;
        init();
    }

    public EditBoolItem(Context context, BoolItem boolItem){
        super(context);
        this.context = context;
        this.boolItem = boolItem;
        init();
    }

    private void init() {
        View mainLayout = inflate(context,R.layout.row_boolitem_edit,null);
        itemName = (EditText) mainLayout.findViewById(R.id.itemName);
        delButton = (ImageButton) mainLayout.findViewById(R.id.delButton);
        saveButton = (ImageButton) mainLayout.findViewById(R.id.saveButton);
        editButton = (ImageButton) mainLayout.findViewById(R.id.editButton);



        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditable(false);
            }
        });
        editButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditable(true);
            }
        });

        if (boolItem != null)
            itemName.setText(boolItem.getName());
        else
            setEditable(true);

        addView(mainLayout);
    }

    public void setEditable(boolean isEditable){
        if (isEditable){
            delButton.setVisibility(VISIBLE);
            saveButton.setVisibility(VISIBLE);
            editButton.setVisibility(GONE);
            itemName.setEnabled(true);
        } else {
            delButton.setVisibility(GONE);
            saveButton.setVisibility(GONE);
            editButton.setVisibility(VISIBLE);
            itemName.setEnabled(false);
        }
    }





    @Nullable
    public BoolItem getBoolItem(){
        if (boolItem == null) {
            boolItem = new BoolItem();
        }

        if (itemName.getText().length() > 0 && boolItem != null){
            boolItem.setName(itemName.getText().toString());
        }
        return boolItem;
    }


}
