package com.nmatte.mood.chart.cell;

import android.content.Context;

public class TextCellViewBuilder {
    Context context;
    String text;
    int backgroundColor = -1;
    boolean backgroundEnabled = true;
    TextCellView.TextAlignment horizontalAlignment;
    TextCellView.TextAlignment verticalAlignment;
    float xOffset = 0;
    TextCellView.Stroke stroke;

    public TextCellViewBuilder setBackground(CellView.Background background) {
        this.background = background;
        return this;
    }

    CellView.Background background = CellView.Background.NONE;

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

    public TextCellViewBuilder setIsBackgroundEnabled(boolean enabled){
        this.backgroundEnabled = enabled;
        return this;
    }

    public TextCellViewBuilder setXoffset(float offset){
        this.xOffset = offset;
        return this;
    }

    public TextCellViewBuilder setStroke(TextCellView.Stroke stroke){
        this.stroke = stroke;
        return this;
    }

    public TextCellView build(){
        if (backgroundColor == -1)
            backgroundColor = CellView.WHITE;
        TextCellView newCell = new TextCellView(
                context,
                text,
                backgroundColor,
                horizontalAlignment,
                verticalAlignment);
        newCell.setBackground(background);
        if (newCell.horizontalAlignment == TextCellView.TextAlignment.LEFT){
            newCell.setLeftAlignX(xOffset);
        }

        if (stroke == TextCellView.Stroke.BOLD){
            newCell.setStroke(TextCellView.Stroke.BOLD);
        }

        return newCell;
    }
}
