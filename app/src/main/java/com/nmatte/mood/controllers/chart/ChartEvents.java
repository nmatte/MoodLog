package com.nmatte.mood.controllers.chart;


import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.models.components.NumComponent;
import com.nmatte.mood.models.modules.NoteModule;

import org.joda.time.DateTime;

public class ChartEvents {
    public static class StartEndDatesLoaded {
        DateTime start;
        DateTime end;
        public StartEndDatesLoaded(DateTime start, DateTime end){
            this.start = start;
            this.end = end;
        }
    }

    public static class CloseEditEntryEvent {
    }

    public static class OpenEditEntryEvent {
    }

    public static class OpenStartDateDialogEvent {
    }

    public static class OpenEndDateDialogEvent {
        DateTime date;

        public OpenEndDateDialogEvent(DateTime date) {
            this.date = date;
        }

        public DateTime getDate() {
            return date;
        }
    }

    public static class SaveEndDateDialogEvent {
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

    public static class SaveBoolItemEvent {
        BoolComponent item;

        public SaveBoolItemEvent(BoolComponent item){
            this.item = item;
        }

        public BoolComponent getItem() {
            return item;
        }
    }

    public static class SaveNumItemEvent {
        NumComponent item;

        public SaveNumItemEvent(NumComponent item) {
            this.item = item;
        }

        public NumComponent getItem(){
            return item;
        }
    }

    public static class CloseNoteEvent {
        ChartEntry entry;

        public CloseNoteEvent (ChartEntry entry){
            this.entry = entry;
        }

        public ChartEntry getEntry() {
            return entry;
        }


    }

    public static class OpenNoteEvent {
        NoteModule module;

        public OpenNoteEvent(NoteModule module){
            this.module = module;
        }

        public NoteModule getModule() {
            return module;
        }
    }
}
