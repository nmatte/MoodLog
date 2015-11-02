package com.nmatte.mood.logbookitems.boolitems;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.nmatte.mood.logbookitems.ChartChangeEvent;
import com.nmatte.mood.moodlog.R;

import de.greenrobot.event.EventBus;

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
        View mainLayout = inflate(context,R.layout.row_boolitem_edit,this);
        itemName = (EditText) mainLayout.findViewById(R.id.itemName);
        delButton = (ImageButton) mainLayout.findViewById(R.id.delButton);
        saveButton = (ImageButton) mainLayout.findViewById(R.id.saveButton);
        editButton = (ImageButton) mainLayout.findViewById(R.id.editButton);
        saveButton.setOnClickListener(saveButtonClickListener);
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

    }

    public void setEditable(boolean isEditable){
        Animation out = AnimationUtils.loadAnimation(context, R.anim.abc_fade_out);
        Animation in = AnimationUtils.loadAnimation(context,R.anim.abc_fade_in);

        if (isEditable){
            editButton.startAnimation(out);
            editButton.setVisibility(INVISIBLE);

            delButton.startAnimation(in);
            delButton.setVisibility(VISIBLE);

            saveButton.startAnimation(in);
            saveButton.setVisibility(VISIBLE);
            itemName.setEnabled(true);

        } else {
            delButton.startAnimation(out);
            delButton.setVisibility(INVISIBLE);

            saveButton.startAnimation(out);
            saveButton.setVisibility(INVISIBLE);

            editButton.startAnimation(in);
            editButton.setVisibility(VISIBLE);
            itemName.setEnabled(false);
        }
    }

    private OnClickListener saveButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            if (validateFields()){
                setEditable(false);
                EventBus.getDefault().post(new SaveBoolItemEvent(getBoolItem()));
                //EventBus.getDefault().postSticky(new ChartChangeEvent());
                EventBus.getDefault().post(new ChartChangeEvent());
            }
        }
    };

    private boolean validateFields(){
        String name = itemName.getText().toString();

        boolean result = true;

        if (name.length() < 1){
            itemName.setError(context.getString(R.string.logbookitem_name_blank_error));
            result = false;
        }

        return result;
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
