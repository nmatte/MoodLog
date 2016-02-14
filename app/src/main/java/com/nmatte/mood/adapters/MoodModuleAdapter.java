package com.nmatte.mood.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nmatte.mood.models.MoodModule;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.views.chart.CellView;
import com.nmatte.mood.views.chart.ImageCellView;
import com.nmatte.mood.views.chart.TextCellView;
import com.nmatte.mood.views.chart.TextCellViewBuilder;
import com.nmatte.mood.views.chart.VerticalText;

import java.util.ArrayList;

public class MoodModuleAdapter extends ModuleAdapter {
    boolean isMini = false;
    MoodModule module;

    public MoodModuleAdapter(Context context, MoodModule module, boolean isMini) {
        super(context, module);
        this.module = module;
        this.isMini = isMini;
    }

    private ArrayList<ImageCellView> getCheckboxes(){
        ArrayList<ImageCellView> result = new ArrayList<>();
        Resources res = context.getResources();
        int colorID = (isMini) ? R.array.mood_colors_mini : R.array.mood_colors;
        int [] colors = res.getIntArray(colorID);
        int factor = (isMini) ? 2 : 1;
        int i = 0;
        for (int color :
                colors) {
            ImageCellView newRow = new ImageCellView(context, color);
            newRow.setBackground(CellView.Background.NONE);
            newRow.setChecked(module.get(i));
            result.add(newRow);
            i+= factor;
        }
        return result;
    }

    @Override
    public ArrayList<View> getLabelViews() {
        RelativeLayout mainView = (RelativeLayout) View.inflate(this.context, R.layout.view_moodmodule_large_label, null);

        LinearLayout labelLayout = (LinearLayout) mainView.findViewById(R.id.moodLabelLayout);

        if (this.isMini){
            VerticalText depressed = (VerticalText) mainView.findViewById(R.id.depressedText);
            depressed.setTextAlignment(VerticalText.TextAlignment.BOTTOM);
            depressed.setText("DEP");
            VerticalText normal = (VerticalText) mainView.findViewById(R.id.normalText);
            normal.setText("NORM");
            VerticalText elevated = (VerticalText) mainView.findViewById(R.id.elevatedText);
            elevated.setTextAlignment(VerticalText.TextAlignment.TOP);
            elevated.setText("ELEV");
        }

        for (TextCellView cellView : getLabelViewss()){
            labelLayout.addView(cellView);
        }

        ArrayList<View> result = new ArrayList<>();
        result.add(mainView);
        return result;
    }



    public ArrayList<TextCellView> getLabelViewss(){
        ArrayList<TextCellView> result = new ArrayList<>();
        int labelID = (isMini ) ? R.array.mood_labels_mini : R.array.mood_labels;
        String [] moodLabels = context.getResources().getStringArray(labelID);
        int colorID = (isMini ) ? R.array.mood_colors_mini : R.array.mood_colors;
        int[] moodColors = context.getResources().getIntArray(colorID);

        int i = 0;
        for (String label : moodLabels){
            TextCellViewBuilder b = new TextCellViewBuilder(context);
            if (i < moodColors.length){
                result.add(b
                        .setText(label)
                        .setBackgroundColor(moodColors[i])
                        .setVerticalAlignment(TextCellView.TextAlignment.CENTER)
                        .build());
                i++;
            } else {
                result.add(b.setText(label).build());
            }
        }
        return result;
    }

    @Override
    public ArrayList<View> getReadViews() {
        ArrayList<View> views = new ArrayList<>();

        for (ImageCellView view : this.getCheckboxes()) {
            view.setEnabled(false);
            views.add(view);
        }

        return views;
    }



    @Override
    public ArrayList<View> getEditViews() {
        ArrayList<View> views = new ArrayList<>();
        int i = 0;
        for (ImageCellView cellView : getCheckboxes()){
            final int index = i;
            cellView.setEnabled(true);
            cellView.setOnChangeListener(new ImageCellView.OnChangeListener() {
                @Override
                public void onChange(boolean value) {
                    module.set(index, value);
                }
            });
            views.add(cellView);
            if (this.isMini)
                i += 2;
            else
                i++;
        }
        return views;
    }
}
