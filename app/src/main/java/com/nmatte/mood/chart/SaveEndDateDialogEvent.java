package com.nmatte.mood.chart;

import org.joda.time.DateTime;


public class SaveEndDateDialogEvent {
    DateTime startDate;
    DateTime endDate;
    boolean rememberDates;
    public SaveEndDateDialogEvent(DateTime startDate, DateTime endDate, boolean rememberDates){
        this.startDate = startDate;
        this.endDate = endDate;
        this.rememberDates = rememberDates;

    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isRememberDates() {
        return rememberDates;
    }

    public void setRememberDates(boolean rememberDates) {
        this.rememberDates = rememberDates;
    }
}
