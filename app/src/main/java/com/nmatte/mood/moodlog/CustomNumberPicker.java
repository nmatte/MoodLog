package com.nmatte.mood.moodlog;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nmatte.mood.chart.cell.TextCellView;


public class CustomNumberPicker extends RelativeLayout {
    TextView minusButton;
    TextView plusButton;
    TextView textCellView;

    int currentNum;
    int maxNum;

    public CustomNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CustomNumberPicker,0,0);

        View v = inflate(context,R.layout.custom_number_picker,null);
        minusButton = (TextView) v.findViewById(R.id.minusButton);
        plusButton = (TextView) v.findViewById(R.id.plusButton);
        textCellView = (TextView) v.findViewById(R.id.textCellView);
        currentNum = a.getInt(R.styleable.CustomNumberPicker_defaultNum,0);
        maxNum = a.getInt(R.styleable.CustomNumberPicker_max,100);

        minusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentNum > 0){
                    setCurrentNum(currentNum - 1);
                } else if (currentNum == 0) {
                    setCurrentNum(maxNum);
                }

            }
        });
        plusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentNum < maxNum){
                    setCurrentNum(currentNum + 1);
                } else if (currentNum == maxNum){
                    setCurrentNum(0);
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
        textCellView.setText(String.valueOf(newNum));
    }
}
