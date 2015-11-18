package com.nmatte.mood.logbookentries.editentry;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.nmatte.mood.moodlog.R;

public class NoteView extends RelativeLayout{
    Context context;

    public NoteView(Context context) {
        super(context);
        this.context = context;
        init();
    }


    public NoteView(Context context, AttributeSet attrs){
        super(context,attrs);
        this.context = context;
        init();
    }

    private void init(){
        inflate(context, R.layout.note_view,this);
    }

}
