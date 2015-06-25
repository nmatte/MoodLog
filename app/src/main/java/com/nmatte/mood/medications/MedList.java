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

import com.nmatte.mood.moodlog.R;

import java.util.ArrayList;

public class MedList extends LinearLayout {
    private ArrayList<Medication> medList;
    private Context context;
    private boolean readOnlyMode;
    MedListListener DBListener;

    public interface MedListListener {
        void delete(Medication m);
        void addNew();
        ArrayList<Medication> getMedList();
    }

    public MedList(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();

    }

    public void setToReadOnly(){
        readOnlyMode = true;
    }



    private void init(){
        this.setOrientation(VERTICAL);
        try{
            DBListener = (MedListListener) context;
        } catch (ClassCastException e){
            Log.e("MedList", "MedList instantiated without listener");
        }
    }


    public void updateList() {
        this.removeAllViews();
        medList = DBListener.getMedList();
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

            if(!readOnlyMode){
                rowView.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        DBListener.delete(m);
                        return true;
                    }
                });
            }
            this.addView(rowView);
        }
        if(!readOnlyMode) {
            View footer = inflater.inflate(R.layout.medication_list_footer, null);
            footer.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBListener.addNew();
                }
            });
            this.addView(footer);
        }

    }


    public ArrayList<Medication> checkedMedications(){
        ArrayList<Medication> result = new ArrayList<>();
        // if there's a footer we don't want to count it
        for(int i = 0; i < this.getChildCount(); i++){
            if(this.getChildAt(i) instanceof CheckedTextView) {
                CheckedTextView v = (CheckedTextView) this.getChildAt(i);
                if (v.isChecked())
                    result.add(medList.get(i));
            }
        }
        return result;
    }

    public void setChecked(ArrayList<Medication> checked){
        for (Medication checkedMed : checked){
            for (int i = 0; i < medList.size(); i++){
                if(medList.get(i).getID() == checkedMed.getID()){
                    CheckedTextView rowView = (CheckedTextView) this.getChildAt(i);
                    rowView.setChecked(true);
                }
            }
        }
    }

}
