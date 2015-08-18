package com.nmatte.mood.chart;

import java.util.Calendar;

public class SaveEndDateDialogEvent {
    Calendar startDate;
    Calendar endDate;
    boolean rememberDates;
    public SaveEndDateDialogEvent(Calendar startDate, Calendar endDate, boolean rememberDates){
        this.startDate = startDate;
        this.endDate = endDate;
        this.rememberDates = rememberDates;

    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public boolean isRememberDates() {
        return rememberDates;
    }

    public void setRememberDates(boolean rememberDates) {
        this.rememberDates = rememberDates;
    }
}
