package com.nmatte.mood.views.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nmatte.mood.models.components.NumComponent;
import com.nmatte.mood.moodlog.R;


public class CustomNumberPicker extends RelativeLayout {
    ImageButton minusButton;
    ImageButton plusButton;
    TextView textCellView;
    Context context;

    NumComponent numItem;



    NumChangeListener numChangeListener = null;

    int currentNum;
    int maxNum;
    private OnClickListener plusListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentNum < maxNum){
                setCurrentNum(currentNum + 1);
            } else if (currentNum == maxNum){
                setCurrentNum(0);
            }
            if (numChangeListener != null)
                numChangeListener.onChange(getCurrentNum());
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

            if (numChangeListener != null)
                numChangeListener.onChange(getCurrentNum());
        }
    };

    public CustomNumberPicker(Context context, NumComponent item){
        super(context);

        this.context = context;
        numItem = item;
        init();


    }

    public CustomNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomNumberPicker,0,0);
        int defaultNum = a.getInt(R.styleable.CustomNumberPicker_defaultNum,0);
        int maxNum = a.getInt(R.styleable.CustomNumberPicker_max, 100);
        numItem = new NumComponent(null,"",maxNum,defaultNum);
        init();

   }

    private void init(){
        inflate(context, R.layout.view_custom_number_picker, this);

        minusButton = (ImageButton) findViewById(R.id.minusButton);
        plusButton = (ImageButton) findViewById(R.id.plusButton);
        textCellView = (TextView) findViewById(R.id.textCellView);
        currentNum = numItem.getDefaultNum();
        maxNum = numItem.getMaxNum();
        textCellView.setText(String.valueOf(currentNum));

        minusButton.setOnClickListener(minusListener);
        plusButton.setOnClickListener(plusListener);
    }

    public void setNumChangeListener(NumChangeListener numChangeListener) {
        this.numChangeListener = numChangeListener;
    }

    public int getCurrentNum(){
        return currentNum;
    }

    public void setCurrentNum(int newNum){
        this.currentNum = newNum;
        textCellView.setText(String.valueOf(newNum));
    }

    public NumComponent getNumItem(){
        if (numItem == null){
            numItem = new NumComponent(null,"",maxNum,0);
        }
        return numItem;
    }

    public interface NumChangeListener {
         void onChange(int change);
    }
}
