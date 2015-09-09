package com.nmatte.mood.chart.cell;

import android.content.Context;

public class TextCellViewBuilder {
    Context context;
    String text;
    int backgroundColor = -1;
    TextCellView.TextAlignment horizontalAlignment;
    TextCellView.TextAlignment verticalAlignment;

    public TextCellViewBuilder(Context context) {
        this.context = context;
    }

    public TextCellViewBuilder setText(String text){
        this.text = text;
        return this;
    }

    public TextCellViewBuilder setHorizontalAlignment(TextCellView.TextAlignment alignment){
        this.horizontalAlignment = alignment;
        return this;
    }


    public TextCellViewBuilder setVerticalAlignment(TextCellView.TextAlignment alignment){
        this.verticalAlignment = alignment;
        return this;
    }

    public TextCellViewBuilder setBackgroundColor(int color){
        this.backgroundColor = color;
        return this;
    }

    public TextCellView build(){
        if (backgroundColor == -1)
            backgroundColor = CellView.WHITE;
        return new TextCellView(context,text,backgroundColor, horizontalAlignment,verticalAlignment);
    }
}
