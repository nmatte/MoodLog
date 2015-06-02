package com.nmatte.mood.medications;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MedList extends LinearLayout {
    private ArrayList<Medication> medList;
    private Context context;
    private View footer;

    MedListLongClickListener listener;

    public interface MedListLongClickListener {
        public void deleteMedication(Medication m);
    }

    public MedList(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        medList = new ArrayList<>();
        this.setOrientation(VERTICAL);
        try{
        listener = (MedListLongClickListener) context;
        } catch (ClassCastException e){
            Log.e("MedList", "MedList instantiated without listener");
        }
    }

    public void setMedicationList (ArrayList<Medication> meds){
        medList = meds;
        updateList();
    }

    private void updateList() {
        this.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(context);
        for (final Medication m : medList){
            final CheckedTextView rowView = (CheckedTextView) inflater.inflate(android.R.layout.simple_list_item_multiple_choice,null);
            TextView label = (TextView) rowView.findViewById(android.R.id.text1);
            float height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, context.getResources().getDisplayMetrics());
            label.setHeight((int) height);
            label.setText(m.getName());
            rowView.setClickable(true);
            rowView.setOnClickListener(new OnClickListener() { // smh android I shouldn't have to add this
                @Override
                public void onClick(View v) {
                    rowView.setChecked(!rowView.isChecked());
                }
            });
            rowView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.deleteMedication(m);
                    return true;
                }
            });
            this.addView(rowView);

        }
        if (footer != null) this.addView(footer);
    }



    public void setFooter(View footer){
        this.footer = footer;
    }

    public ArrayList<Medication> checkedMedications(){
        ArrayList<Medication> result = new ArrayList<>();

        // if there's a footer we don't want to count it
        int count = (footer == null) ? this.getChildCount() : this.getChildCount() - 1;

        for(int i = 0; i < count; i++){
            CheckedTextView v = (CheckedTextView) this.getChildAt(i);
            if (v.isChecked())
                result.add(medList.get(i));
        }
        return result;
    }
}
