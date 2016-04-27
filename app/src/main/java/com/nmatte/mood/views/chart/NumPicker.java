package com.nmatte.mood.views.chart;

import android.content.Context;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.nmatte.mood.moodlog.R;


public class NumPicker extends RelativeLayout {
    ImageButton minusButton;
    ImageButton plusButton;
    TextView textCellView;
    Context context;
    NumChangeListener numChangeListener = null;

    int currentNum;
    int maxNum;

//    private OnClickListener plusListener = v -> {
//        setCurrentNum(getCurrentNum() + 1);
//
//        if (numChangeListener != null)
//            numChangeListener.onChange(getCurrentNum());
//    };
//
//    private OnClickListener minusListener = v -> {
//        setCurrentNum(getCurrentNum() - 1);
//
//        if (numChangeListener != null)
//            numChangeListener.onChange(getCurrentNum());
//    };

    public NumPicker(Context context, int currentNum, int maxNum, NumChangeListener listener){
        super(context);

        this.context = context;
        this.currentNum = currentNum;
        this.maxNum = maxNum;
        this.numChangeListener = listener;
        init();
    }

    private void init(){
        inflate(context, R.layout.view_custom_number_picker, this);

        minusButton = (ImageButton) findViewById(R.id.minusButton);
        plusButton = (ImageButton) findViewById(R.id.plusButton);
        textCellView = (TextView) findViewById(R.id.textCellView);
        textCellView.setText(String.valueOf(currentNum));

        RxView
                .clicks(minusButton)
                .subscribe(aVoid -> setCurrentNum(getCurrentNum() - 1));

        RxView
                .clicks(plusButton)
                .subscribe(aVoid -> setCurrentNum(getCurrentNum() + 1));

//        minusButton.setOnClickListener(minusListener);
//        plusButton.setOnClickListener(plusListener);
    }


    public int getCurrentNum(){
        return currentNum;
    }

    public void setCurrentNum(int newNum){
        if (newNum < 0) {
            this.currentNum = maxNum;
        } else if (newNum > maxNum) {
            this.currentNum = 0;
        } else {
            this.currentNum = newNum;
        }

        textCellView.setText(String.valueOf(currentNum));
        this.numChangeListener.onChange(currentNum);
    }


    public interface NumChangeListener {
         void onChange(int change);
    }
}
