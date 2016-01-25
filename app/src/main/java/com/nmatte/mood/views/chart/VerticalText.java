package com.nmatte.mood.views.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.nmatte.mood.moodlog.R;

public class VerticalText extends View {
    Path path;
    Paint paint;
    String text;
    TextAlignment alignment = TextAlignment.CENTER;

    public VerticalText(Context context) {
        super(context);
        init();
    }

    public VerticalText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.VerticalText,0,0);
        this.text = (String) a.getText(R.styleable.VerticalText_vertical_text);
        if (this.text == null)
            this.text = "";

        init();
    }

    private void init(){
        path = new Path();
        paint = new Paint();

    }

    public void setText(String text){
        this.text = text;
        invalidate();
    }

    public void setTextAlignment(TextAlignment alignment){
        this.alignment = alignment;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(getResources().getColor(R.color.black));
        paint.setTextSize((getWidth() * 2) / 3);
        float textLength = paint.measureText(text);
        int bottomY;
        int topY;
        int x = (getWidth() / 2) + (int) (paint.getTextSize() /2 );

        /*

        if (alignment == TextAlignment.BOTTOM){
            path.setLastPoint(getWidth(), getHeight());
            path.lineTo(getWidth(), getHeight() - (int) textLength);
        }
        if (alignment == TextAlignment.TOP){
            path.setLastPoint(getWidth(),textLength);
            path.lineTo(getWidth(),0);
        }
        if (alignment == TextAlignment.CENTER){
            int viewCenter = getHeight() / 2;
            float centerOffset = textLength / 2;
            int centerStart = viewCenter - (int) centerOffset;
            int centerEnd = viewCenter + (int) centerOffset;
            path.setLastPoint(getWidth(),centerStart);
            path.lineTo(getWidth(),centerEnd);
        }

*/
        if (alignment == TextAlignment.TOP){
            bottomY =(int) textLength;
            topY = 0;
        }
        else if (alignment == TextAlignment.BOTTOM){
            bottomY = getHeight();
            topY = getHeight() -  (int) textLength;
        } else {
            bottomY = getHeight() / 2 +((int) textLength) / 2;
            topY = 0;
        }

        path.setLastPoint(x,bottomY);
        path.lineTo(x,topY);
        paint.setAntiAlias(true);
        canvas.drawTextOnPath(text, path, 0, 0, paint);
    }

    public enum TextAlignment {
        TOP,
        BOTTOM,
        CENTER
    }
}
