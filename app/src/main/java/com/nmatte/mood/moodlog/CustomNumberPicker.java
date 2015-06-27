package com.nmatte.mood.moodlog;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class CustomNumberPicker extends RelativeLayout {
    TextView minusButton;
    TextView plusButton;
    TextView numIndicator;

    int currentNum;
    int maxNum;

    public CustomNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CustomNumberPicker,0,0);

        View v = inflate(context,R.layout.custom_number_picker,null);
        minusButton = (TextView) v.findViewById(R.id.minusButton);
        plusButton = (TextView) v.findViewById(R.id.plusButton);
        numIndicator = (TextView) v.findViewById(R.id.numIndicator);

        currentNum = a.getInt(R.styleable.CustomNumberPicker_defaultNum,0);

        numIndicator.setText(Integer.toString(currentNum));

        maxNum = a.getInt(R.styleable.CustomNumberPicker_max,100);

        minusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentNum > 0){
                    currentNum--;
                    numIndicator.setText(Integer.toString(currentNum));
                }

            }
        });

        plusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentNum < maxNum){
                    currentNum++;
                    numIndicator.setText(Integer.toString(currentNum));
                }
            }
        });

        addView(v);
   }

    public int getCurrentNum(){
        return currentNum;
    }

    public void setCurrentNum(int newNum){
        this.currentNum = newNum;
        numIndicator.setText(String.valueOf(newNum));
    }
}
