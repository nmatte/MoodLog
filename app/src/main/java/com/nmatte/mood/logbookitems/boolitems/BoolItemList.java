package com.nmatte.mood.logbookitems.boolitems;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nmatte.mood.chart.cell.CheckableCellView;
import com.nmatte.mood.moodlog.R;

import java.util.ArrayList;

public class BoolItemList extends LinearLayout {
    private ArrayList<BoolItem> medList;
    final private boolean chartStyle;
    final private boolean isEnabled;

    public BoolItemList(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.BoolItemList,0,0);
        this.chartStyle = a.getBoolean(R.styleable.BoolItemList_style_chart,false);
        this.isEnabled = a.getBoolean(R.styleable.BoolItemList_is_enabled,true);
        init(context);
    }

    public BoolItemList(Context context, boolean chartStyle){
        super(context);
        this.chartStyle = chartStyle;
        this.isEnabled = true;
        init(context);
    }

    public BoolItemList(Context context, boolean chartStyle, boolean isEnabled){
        super(context);
        this.chartStyle = chartStyle;
        this.isEnabled = isEnabled;
        init(context);
    }



    private void init(Context context){
        this.setOrientation(VERTICAL);
        this.medList = BoolItemTableHelper.getMedicationList(context);
        this.setClickable(isEnabled);
        this.setLongClickable(isEnabled);
    }


    public void updateList(Context context) {
        this.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(context);
        for (final BoolItem m : medList){
            final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, context.getResources().getDisplayMetrics());
            if(chartStyle){
                final CheckableCellView cellView = new CheckableCellView(context,isEnabled);
                this.addView(cellView);
            } else {
                final CheckedTextView rowView = (CheckedTextView) inflater.inflate(android.R.layout.simple_list_item_multiple_choice, null);
                TextView label = (TextView) rowView.findViewById(android.R.id.text1);
                label.setHeight (height);
                label.setText(m.getName());
                rowView.setClickable(true);
                rowView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isEnabled)
                            rowView.setChecked(!rowView.isChecked());
                    }
                });
                this.addView(rowView);
            }
        }
    }


    public ArrayList<BoolItem> getCheckedMeds(){
        ArrayList<BoolItem> result = new ArrayList<>();
        // if there's a footer we don't want to count it
        for(int i = 0; i < this.getChildCount(); i++){
            if(this.getChildAt(i) instanceof CheckedTextView) {
                CheckedTextView v = (CheckedTextView) this.getChildAt(i);
                if (v.isChecked())
                    result.add(medList.get(i));
            } else if (this.getChildAt(i) instanceof CheckableCellView){
                CheckableCellView v = (CheckableCellView) this.getChildAt(i);
                if(v.isChecked())
                    result.add(medList.get(i));
            }
        }
        return result;
    }

    public void setCheckedMeds(ArrayList<BoolItem> checked){
        for (BoolItem checkedMed : checked){
            for (int i = 0; i < medList.size(); i++){
                if(medList.get(i).getID() == checkedMed.getID()){
                    if(this.getChildAt(i) instanceof  CheckedTextView){
                        CheckedTextView rowView = (CheckedTextView) this.getChildAt(i);
                        rowView.setChecked(true);
                    } else if (this.getChildAt(i) instanceof  CheckableCellView){
                        CheckableCellView rowView = (CheckableCellView) this.getChildAt(i);
                        rowView.setChecked(true);
                    }
                }
            }
        }
    }

}
