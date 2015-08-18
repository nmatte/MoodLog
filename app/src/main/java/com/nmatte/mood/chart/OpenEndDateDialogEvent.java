package com.nmatte.mood.chart;

import java.util.Calendar;

public class OpenEndDateDialogEvent {
    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }


    Calendar date;
    public OpenEndDateDialogEvent (Calendar date){
        this.date = date;
    }
}
