package com.nmatte.mood.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.nmatte.mood.moodlog.R;

public class TextCellView extends CellView {
    Paint blackPaint;
    String text;


    public TextCellView(Context context){
        super(context);
        init();
    }

    public TextCellView(Context context, String text){
        super(context);
        this.text = text;
        init();
    }

    public TextCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextCellView,0,0);
        this.text = (String) a.getText(R.styleable.TextCellView_text);
        if (this.text == null)
            this.text = "";
        init();
    }

    public TextCellView(Context context, int backgroundColor){
        super(context, backgroundColor);
        init();
    }


    private void init(){
        blackPaint = new Paint();
        blackPaint.setColor(BLACK);
        blackPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String sampleText = text.length() < 2 ? "00" : text;
        blackPaint.setTextSize(getAdjustedTextSize(sampleText));
        //blackPaint = adjustTextSize(getWidth(),text,blackPaint);



        float textX = getWidth() / 2 - blackPaint.measureText(text) / 2;
        float textY = (getHeight() * 5 )/ 6;

        canvas.drawText(text, textX, textY, blackPaint);
    }

    public String getText (){
        return text;
    }

    public void setText(String newText){
        this.text = newText;
        invalidate();
    }

}