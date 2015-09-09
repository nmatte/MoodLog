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
    TextAlignment horizontalAlignment;
    TextAlignment verticalAlignment;
    int backgroundColor;


    public enum TextAlignment {
        LEFT,
        CENTER,
        BOTTOM
    };

    protected TextCellView(Context context,
                           String text,
                           int backgroundColor,
                           TextAlignment hAlignment, TextAlignment vAlignment){
        super(context,backgroundColor);
        this.context = context;
        this.text = text;
        this.horizontalAlignment = (hAlignment == null)? TextAlignment.LEFT : hAlignment;
        this.verticalAlignment = (vAlignment == null)? TextAlignment.BOTTOM : vAlignment;
        init();
    }



    public TextCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextCellView,0,0);
        this.text = (String) a.getText(R.styleable.TextCellView_text);
        if (this.text == null)
            this.text = "";
        String hAlignText = (String) a.getText(R.styleable.TextCellView_horizontal_alignment);
        String vAlignText = (String) a.getText(R.styleable.TextCellView_vertical_alignment);

        if (hAlignText == null)
            hAlignText = "";
        if (vAlignText == null)
            vAlignText = "";
        // prefer left align
        this.horizontalAlignment = TextAlignment.LEFT;
        if (hAlignText.equals("center"))
            this.horizontalAlignment = TextAlignment.CENTER;
        this.verticalAlignment = TextAlignment.BOTTOM;
        if(vAlignText.equals("center"))
            this.verticalAlignment = TextAlignment.CENTER;
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
        if (horizontalAlignment == TextAlignment.CENTER) {
            textX = getWidth() / 2 - blackPaint.measureText(text) / 2;
        } else if (horizontalAlignment == TextAlignment.LEFT){
            textX = (float) super.leftTransparentBound;

        }

        if (verticalAlignment == TextAlignment.CENTER){
            textY = getHeight() / 2 + blackPaint.getTextSize()/2;
                    //- blackPaint.getTextSize();
            //super.bottomTransparentBound;
            //super.topTransparentBound;

        } else if (verticalAlignment == TextAlignment.BOTTOM){
            textY = (getHeight() * 5 )/ 6;
            //textY = (float) super.bottomTransparentBound - blackPaint.getTextSize();
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