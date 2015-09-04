package com.nmatte.mood.chart.cell;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.nmatte.mood.moodlog.R;

public class TextCellView extends CellView {
    Paint blackPaint;
    Context context;
    String text;
    TextAlignment alignment;
    int backgroundColor;


    public enum TextAlignment {
        ALIGN_LEFT,
        ALIGN_CENTER
    };

    protected TextCellView(Context context, String text, int backgroundColor, TextAlignment alignment){
        super(context,backgroundColor);
        this.context = context;
        this.text = text;
        this.alignment = (alignment == null)? TextAlignment.ALIGN_LEFT : alignment;
        init();
    }



    public TextCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextCellView,0,0);
        this.text = (String) a.getText(R.styleable.TextCellView_text);
        if (this.text == null)
            this.text = "";
        String alignText = (String) a.getText(R.styleable.TextCellView_alignment);

        // prefer left align
        this.alignment = TextAlignment.ALIGN_LEFT;
        if (alignText.equals("center"))
            this.alignment = TextAlignment.ALIGN_CENTER;
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

        String sampleText = text.length() <= 2 ? "00" : text.length() <= "Irritability".length() ? "Irritability" : text;
        blackPaint.setTextSize(getAdjustedTextSize(sampleText));


        float textX = 0;
        float textY = 0;
        if (alignment == TextAlignment.ALIGN_CENTER){
            textX = getWidth() / 2 - blackPaint.measureText(text) / 2;
            textY = (getHeight() * 5 )/ 6;
        }

        if (alignment == TextAlignment.ALIGN_LEFT){
            textX = (float) super.leftTransparentBound;
            textY = (getHeight() * 5 )/ 6;
        }


        canvas.drawText(text, textX, textY, blackPaint);
    }

    public String getText (){
        return text;
    }

    public TextCellView setText(String newText){
        this.text = newText;
        invalidate();
        return this;
    }



}