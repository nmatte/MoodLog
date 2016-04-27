package com.nmatte.mood.views.chart.cells;

import android.content.Context;

import com.nmatte.mood.moodlog.R;

public class ImageCellViewBuilder extends CellViewBuilder {
    boolean value = false;
    ImageCellView.OnChangeListener listener = null;
    int imageResource = R.drawable.black_square;

    public ImageCellViewBuilder(Context context) {
        super(context);
    }

    @Override
    public ImageCellViewBuilder setBackgroundColor(int color) {
        this.backgroundColor = color;
        return this;
    }

    public ImageCellViewBuilder setValue(boolean value) {
        this.value = value;
        return this;
    }

    public ImageCellViewBuilder setListener(ImageCellView.OnChangeListener listener) {
        this.listener = listener;
        return this;
    }

    public ImageCellViewBuilder setImageResource(int imageResource) {
        this.imageResource = imageResource;
        return this;
    }

    @Override
    public ImageCellView build() {
        return new ImageCellView(
                context,
                backgroundColor,
                value,
                listener,
                imageResource
        );
    }
}
