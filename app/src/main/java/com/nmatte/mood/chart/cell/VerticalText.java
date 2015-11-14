package com.nmatte.mood.chart.cell;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.nmatte.mood.moodlog.R;

public class VerticalText extends View {
    Path path;
    Paint paint;

    public VerticalText(Context context) {
        super(context);
        init();
    }

    public VerticalText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        path = new Path();
        paint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(getResources().getColor(R.color.gray_cell_bg));
        paint.setColor(getResources().getColor(R.color.white));
        path.setLastPoint(getWidth(), getHeight());
        path.lineTo(getWidth(), 0);
        paint.setTextSize(getWidth()/10);
        canvas.drawTextOnPath("Foobar", path, 0, 0, paint);
        paint.setTextSize(getHeight()/10);
        canvas.drawText("Foobar",0,getHeight(),paint);
    }
}
