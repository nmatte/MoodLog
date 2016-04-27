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
    String text = "";
    TextAlignment horizontalAlignment;
    TextAlignment verticalAlignment;
    Stroke stroke;
    float leftAlignX = 0;


    protected TextCellView(Context context, String text, int backgroundColor, TextAlignment hAlign, TextAlignment vAlign, Stroke stroke, float xOffset){
        super(context, backgroundColor);
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
        this.text                = textAttr;
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

        float textX = 0;
        float textY = 0;

        switch (horizontalAlignment) {
            case CENTER:
                textX = getWidth() / 2 - textPaint.measureText( text) / 2;
                break;
            case LEFT:
                textX = (float) super.leftTransparentBound;
                if (leftAlignX > textX)
                    textX = leftAlignX;
                break;
            default:
                break;
        }

        switch (verticalAlignment) {
            case CENTER:
                textY = getHeight() / 2 + textPaint.getTextSize()/2;
                break;
            case BOTTOM:
                textY = (getHeight() * 5 )/ 6;
        }

        if (stroke == Stroke.BOLD){
            textPaint.setTypeface(Typeface.create(textPaint.getTypeface(),Typeface.BOLD));
        }

        canvas.drawText(truncate(getText()), textX, textY, textPaint);
    }

    public String getText (){
        if (this.text == null) {
            text = "";
        }
        return text;
    }

    private String truncate(final String text){
        int maxLength = super.rightTransparentBound - super.leftTransparentBound;

        if(textPaint.measureText(text) <= maxLength)
            return text;

        String newText = text;

        while(newText.length() > 0 && textPaint.measureText(newText + "...") > maxLength) {
            newText = newText.substring(0, newText.length() - 1);
        }

        return newText + "...";
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