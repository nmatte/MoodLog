package com.nmatte.mood.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

public class TextCellView extends CellView {
    Paint blackPaint;
    String text = "";


    public TextCellView(Context context){
        super(context);
        init();

    }

    public TextCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
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

        blackPaint = adjustTextSize(getWidth(),text,blackPaint);

        float textX = getWidth() / 2 - blackPaint.measureText(text) / 2;
        float textY = getHeight() / 6;
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
