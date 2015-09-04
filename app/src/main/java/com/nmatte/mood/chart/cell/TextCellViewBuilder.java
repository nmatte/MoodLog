package com.nmatte.mood.chart.cell;

import android.content.Context;

public class TextCellViewBuilder {
        Context context;
        String text;
        int backgroundColor = -1;
        TextCellView.TextAlignment alignment;

        public TextCellViewBuilder(Context context) {
            this.context = context;
        }

        public TextCellViewBuilder setText(String text){
            this.text = text;
            return this;
        }

        public TextCellViewBuilder setAlignment(TextCellView.TextAlignment alignment){
            this.alignment = alignment;
            return this;
        }

        public TextCellViewBuilder setBackgroundColor(int color){
            this.backgroundColor = color;
            return this;
        }

        public TextCellView build(){
            if (backgroundColor == -1)
                backgroundColor = CellView.WHITE;
            return new TextCellView(context,text,backgroundColor,alignment);
        }
}
