package com.nmatte.mood.views.chart.cells;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.nmatte.mood.moodlog.R;

public class TextCellView extends CellView {
    Paint textPaint;
    Context context;
    String text = "";
    TextAlignment horizontalAlignment;
    TextAlignment verticalAlignment;
    Stroke stroke;
    float leftAlignX = 0;


    protected TextCellView(Context context,
                           String text,
                           int backgroundColor,
                           TextAlignment hAlign,
                           TextAlignment vAlign,
                           Stroke stroke,
                           float xOffset){
        super(context,backgroundColor);
        this.context             = context;
        this.text                = text;
        this.horizontalAlignment = (hAlign == null) ? TextAlignment.LEFT : hAlign;
        this.verticalAlignment   = (vAlign == null) ? TextAlignment.BOTTOM : vAlign;
        this.stroke              = stroke;
        this.leftAlignX          = xOffset;

        init();
    }

    public TextCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextCellView,0,0);

        String textAttr = (String) a.getText(R.styleable.TextCellView_text);
        String hAlignText = (String) a.getText(R.styleable.TextCellView_horizontal_alignment);
        String vAlignText = (String) a.getText(R.styleable.TextCellView_vertical_alignment);

        this.context             = context;
        this.text                = textAttr == null ? "" : textAttr;
        this.horizontalAlignment = hAlignText != null && hAlignText.equals("center") ? TextAlignment.CENTER : TextAlignment.LEFT;
        this.verticalAlignment   = vAlignText != null && vAlignText.equals("center") ? TextAlignment.CENTER: TextAlignment.BOTTOM;
        this.stroke              = Stroke.DEFAULT;

        init();
    }

    private void init(){
        textPaint = new Paint();
        textPaint.setColor(context.getResources().getColor(R.color.black));
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        textPaint.setTextSize((super.bottomTransparentBound - super.topTransparentBound) / 2);
        if (text == null)
            text = "";


        float textX = 0;
        float textY = 0;
        if (horizontalAlignment == TextAlignment.CENTER) {
            textX = getWidth() / 2 - textPaint.measureText( text) / 2;
        } else if (horizontalAlignment == TextAlignment.LEFT){
            textX = (float) super.leftTransparentBound;
            if (leftAlignX > textX)
                textX = leftAlignX;
        }

        if (verticalAlignment == TextAlignment.CENTER){
            textY = getHeight() / 2 + textPaint.getTextSize()/2;
        } else if (verticalAlignment == TextAlignment.BOTTOM){
            textY = (getHeight() * 5 )/ 6;
        }


        String displayText = text;
        if (text.length() > 2)
            displayText = truncate(text);
        if (stroke == Stroke.BOLD){
            Typeface current = textPaint.getTypeface();
            textPaint.setTypeface(Typeface.create(current,Typeface.BOLD));
        }

        canvas.drawText(displayText, textX, textY, textPaint);
    }

    public String getText (){
        return text;
    }

    public TextCellView setText(String newText){
        this.text = newText;
        invalidate();
        return this;
    }

    private String truncate (final String text){
        int maxLength = super.rightTransparentBound - super.leftTransparentBound;

        if(textPaint.measureText(text) <= maxLength)
            return text;


        for (int i = text.length(); i > 0; i--){
            String resultText = text.substring(0,i);
            resultText += "...";
            if (textPaint.measureText(resultText) <= maxLength)
                return resultText;
        }

        return "";
    }

    public enum TextAlignment {
        LEFT,
        CENTER,
        BOTTOM
    }

    public enum Stroke {
        DEFAULT,
        BOLD
    }
}