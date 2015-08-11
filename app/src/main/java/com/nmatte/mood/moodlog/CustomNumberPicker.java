package com.nmatte.mood.moodlog;

import android.content.Context;
import android.content.res.Resources;
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
    Context context;

    NumItem numItem;

    int currentNum;
    int maxNum;

    public CustomNumberPicker(Context context, NumItem item){
        super(context);

        this.context = context;
        numItem = item;
        init();


    }

    public CustomNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CustomNumberPicker,0,0);
        int defaultNum = a.getInt(R.styleable.CustomNumberPicker_defaultNum,0);
        int maxNum = a.getInt(R.styleable.CustomNumberPicker_max, 100);
        numItem = new NumItem(null,"",maxNum,defaultNum);
        init();

   }

    private void init(){
        inflate(context,R.layout.custom_number_picker,this);

        minusButton = (ImageButton) findViewById(R.id.minusButton);
        plusButton = (ImageButton) findViewById(R.id.plusButton);
        textCellView = (TextView) findViewById(R.id.textCellView);
        currentNum = numItem.getDefaultNum();
        maxNum = numItem.getMaxNum();
        textCellView.setText(String.valueOf(currentNum));

        minusButton.setOnClickListener(minusListener);
        plusButton.setOnClickListener(plusListener);
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
            numItem = new NumItem(null,"",maxNum,0);
        }
        return numItem;
    }
}
