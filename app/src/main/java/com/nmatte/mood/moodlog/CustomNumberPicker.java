package com.nmatte.mood.moodlog;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nmatte.mood.logbookitems.numitems.NumItem;


public class CustomNumberPicker extends RelativeLayout {
    ImageButton minusButton;
    ImageButton plusButton;
    TextView textCellView;

    NumItem numItem;

    int currentNum;
    int maxNum;

    public CustomNumberPicker(Context context, NumItem item){
        super(context);
        View view = inflate(context,R.layout.custom_number_picker,null);

        minusButton = (ImageButton) view.findViewById(R.id.minusButton);
        plusButton = (ImageButton) view.findViewById(R.id.plusButton);
        this.numItem = item;
        textCellView = (TextView) view.findViewById(R.id.textCellView);
        currentNum = item.getDefaultNum();
        maxNum = item.getMaxNum();

        minusButton.setOnClickListener(minusListener);
        plusButton.setOnClickListener(plusListener);

        addView(view);
    }

    public CustomNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CustomNumberPicker,0,0);

        View v = inflate(context,R.layout.custom_number_picker,null);
        minusButton = (ImageButton) v.findViewById(R.id.minusButton);
        plusButton = (ImageButton) v.findViewById(R.id.plusButton);
        textCellView = (TextView) v.findViewById(R.id.textCellView);
        currentNum = a.getInt(R.styleable.CustomNumberPicker_defaultNum,0);
        maxNum = a.getInt(R.styleable.CustomNumberPicker_max,100);

        textCellView.setBackgroundColor(0xFFFFFFFF);

        minusButton.setOnClickListener(minusListener);
        plusButton.setOnClickListener(plusListener);

        addView(v);
   }

    private OnClickListener plusListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentNum < maxNum){
                setCurrentNum(currentNum + 1);
            } else if (currentNum == maxNum){
                setCurrentNum(0);
            }
        }
    };

    private OnClickListener minusListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentNum > 0){
                setCurrentNum(currentNum - 1);
            } else if (currentNum == 0) {
                setCurrentNum(maxNum);
            }
        }
    };

    public int getCurrentNum(){
        return currentNum;
    }

    public void setCurrentNum(int newNum){
        this.currentNum = newNum;
        textCellView.setText(String.valueOf(newNum));
    }

    public NumItem getNumItem(){
        if (numItem == null){
            numItem = new NumItem(0,"",maxNum,0);
        }
        return numItem;
    }
}
