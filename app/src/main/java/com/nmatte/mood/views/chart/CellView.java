package com.nmatte.mood.views.chart;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.View;

import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.settings.PreferencesContract;


/* base class to draw background and borders */
public class CellView extends View {

    static final int
            DEFAULT_BG_ID = R.drawable.drop_shadow3,
            VERTICAL_SHADOW_BG_ID = R.drawable.drop_shadow_vertical,
            HORIZONTAL_SHADOW_BG_ID=  R.drawable.drop_shadow_horizontal;
    static final int WHITE = 0xFFFFFFFF;
    static final int BLACK = 0xFF000000;
    protected int
            leftTransparentBound = -1,
            rightTransparentBound = -1,
            topTransparentBound = -1,
            bottomTransparentBound = -1,
            shadowID = VERTICAL_SHADOW_BG_ID;
    Paint blackPaint;
    int backgroundColor = -1;
    Context context;
    Background background = Background.NONE;
    Size size = Size.MEDIUM;
    public CellView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CellView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CellView(Context context, int backgroundColor){
        super(context);
        this.context = context;
        init();
        this.backgroundColor = backgroundColor;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    private void init(){
        int sizeCode = PreferenceManager
                .getDefaultSharedPreferences(context)
                .getInt(PreferencesContract.CELL_SIZE,Size.MEDIUM.sizeCode);

        this.size = Size.getSize(sizeCode);
        blackPaint = new Paint();
        blackPaint.setColor(BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Resources res = getResources();


        int desiredHeight;
        int desiredWidth;
        switch (size){
            case MEDIUM:
                desiredHeight = (int) res.getDimension(R.dimen.chart_cell_height_m);
                desiredWidth = (int) res.getDimension(R.dimen.chart_cell_width_m);
                break;
            case LARGE:
                desiredHeight = (int) res.getDimension(R.dimen.chart_cell_height_l);
                desiredWidth = (int) res.getDimension(R.dimen.chart_cell_width_l);
                break;
            default:
                desiredHeight = (int) res.getDimension(R.dimen.chart_cell_height_m);
                desiredWidth = (int) res.getDimension(R.dimen.chart_cell_width_m);
        }



        if (res.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            desiredHeight = desiredHeight/2;
            desiredWidth = (desiredWidth * 2)/3;
            this.setPadding(1,1,1,1);
        }

        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == View.MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == View.MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }
        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == View.MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw default color (white)
        if (backgroundColor == -1)
            backgroundColor = WHITE;
        canvas.drawColor(backgroundColor);


        leftTransparentBound = 0;
        rightTransparentBound = getWidth();
        topTransparentBound = 0;
        bottomTransparentBound = getHeight();
        NinePatchDrawable bg = (NinePatchDrawable) getResources().getDrawable(shadowID);

        if (background != Background.NONE){
            if (bg != null) {
                bg.setBounds(0, 0, getWidth(), getHeight());
                bg.draw(canvas);
                Rect bounds = bg.getTransparentRegion().getBounds();
                leftTransparentBound = bounds.left;
                rightTransparentBound = bounds.right;
                topTransparentBound = bounds.top;

            }
        }



    }

    public void setBg(int id) {
        this.shadowID = id;
        invalidate();
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    protected float getAdjustedTextSize (String text){
        blackPaint.setTextSize(getHeight());
        while(blackPaint.measureText(text) > getWidth() * 0.8){
            blackPaint.setTextSize(blackPaint.getTextSize() * (float) 0.7);
        }
        return blackPaint.getTextSize();
    }



    public enum Background {
        NONE,
        VERTICAL
    }


    public enum Size {
        MEDIUM(1),
        LARGE(2);
        private final int sizeCode;
        Size (int sizeCode){
            this.sizeCode = sizeCode;
        }

        public static Size getSize (int sizeCode){
            if (sizeCode == MEDIUM.sizeCode)
                return MEDIUM;
            if (sizeCode == LARGE.sizeCode)
                return LARGE;
            return MEDIUM;
        }

        public int sizeCode(){
            return sizeCode;
        }


    }
}
