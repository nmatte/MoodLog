package com.nmatte.mood.logbookitems.numitems;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.nmatte.mood.moodlog.R;

import de.greenrobot.event.EventBus;

public class EditNumItem extends RelativeLayout {


    NumItem numItem = null;
    public EditText itemName;
    public ImageButton delButton;
    public ImageButton saveButton;
    public ImageButton editButton;
    public EditText defaultNumText;
    public EditText maxNumText;
    Context context;

    public EditNumItem(Context context) {
        super(context);
        this.context = context;
        init();

    }

    public EditNumItem(Context context, NumItem numItem){
        super(context);
        this.context = context;
        this.numItem = numItem;
        init();
    }

    private void init() {
        View mainLayout = inflate(context, R.layout.row_numitem_edit, this);
        itemName = (EditText) mainLayout.findViewById(R.id.itemName);

        delButton = (ImageButton) mainLayout.findViewById(R.id.delButton);
        saveButton = (ImageButton) mainLayout.findViewById(R.id.saveButton);
        editButton = (ImageButton) mainLayout.findViewById(R.id.editButton);

        defaultNumText = (EditText) mainLayout.findViewById(R.id.defaultNumEditText);
        maxNumText = (EditText) mainLayout.findViewById(R.id.maxNumEditText);

        saveButton.setOnClickListener(saveButtonClickListener);
        editButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditable(true);
            }
        });


        if (numItem != null){
            itemName.setText(numItem.getName());
            defaultNumText.setText(String.valueOf(numItem.getDefaultNum()));
            maxNumText.setText(String.valueOf(numItem.getMaxNum()));
        } else {
            setEditable(true);
        }


    }

    public void setNumItem(NumItem numItem) {
        this.numItem = numItem;
        itemName.setText(numItem.getName());
        defaultNumText.setText(String.valueOf(numItem.getDefaultNum()));
        maxNumText.setText(String.valueOf(numItem.getMaxNum()));
    }

    private OnClickListener saveButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(validateFields()){
                setEditable(false);
                EventBus.getDefault().post(new SaveNumItemEvent(getNumItem()));
            }
        }
    };

    public void setEditable(boolean isEditable){
        Animation out= AnimationUtils.loadAnimation(context, R.anim.abc_fade_out);
        Animation in  = AnimationUtils.loadAnimation(context, R.anim.abc_fade_in);

        if (isEditable){

            editButton.startAnimation(out);
            editButton.setVisibility(INVISIBLE);


            delButton.startAnimation(in);
            delButton.setVisibility(VISIBLE);

            saveButton.startAnimation(in);
            saveButton.setVisibility(VISIBLE);
            itemName.setEnabled(true);
            maxNumText.setEnabled(true);
            defaultNumText.setEnabled(true);

        } else {
            delButton.startAnimation(out);
            delButton.setVisibility(INVISIBLE);

            saveButton.startAnimation(out);
            saveButton.setVisibility(INVISIBLE);

            editButton.startAnimation(in);
            editButton.setVisibility(VISIBLE);
            itemName.setEnabled(false);
            maxNumText.setEnabled(false);
            defaultNumText.setEnabled(false);
        }
    }

    private boolean validateFields(){
        String name = itemName.getText().toString();
        String maxNumString = maxNumText.getText().toString();
        String defaultNumString = defaultNumText.getText().toString();

        boolean result = true;

        if (name.length() < 1){
            itemName.setError(context.getString(R.string.logbookitem_name_blank_error));
            result = false;
        }

        if (maxNumString.length() < 1){
            // blank field
            maxNumText.setError("Max num field can't be blank.");
            result = false;
        } else if (Integer.valueOf(maxNumString) < 1){
            // field not blank, but invalid input
            maxNumText.setError("Max num must be greater than 0.");
            result = false;
        }

        /*
        default num is less important and pre-populated. Doesn't give error message, but automatically
        corrects the value.
         */
        if (defaultNumString.length() < 1){
            defaultNumText.setText("0");
        } else if (Integer.valueOf(defaultNumString) < 1){
            defaultNumText.setText("0");
        } else if (maxNumString.length() > 1){
            if (Integer.valueOf(defaultNumString) > Integer.valueOf(maxNumString))
                defaultNumText.setText(maxNumString);
        }

        return result;
    }

    public NumItem getNumItem(){
        String name = itemName.getText().toString();
        String maxNumString = maxNumText.getText().toString();
        String defaultNumString = defaultNumText.getText().toString();
        int maxNum = 0;
        int defaultNum = 0;

        if (maxNumString.length() > 0)
            maxNum = Integer.valueOf(maxNumString);
        if (defaultNumString.length() > 0)
            defaultNum = Integer.valueOf(defaultNumString);


        if (numItem == null){
            numItem = new NumItem(name, maxNum, defaultNum);
            return numItem;
        } else {
            numItem = new NumItem(numItem.getID(),name,maxNum,defaultNum);
            return numItem;
        }
    }

}
