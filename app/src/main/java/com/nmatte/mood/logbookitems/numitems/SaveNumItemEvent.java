package com.nmatte.mood.logbookitems.numitems;


public class SaveNumItemEvent {
    NumItem item;

    public SaveNumItemEvent(NumItem item) {
        this.item = item;
    }

    public NumItem getItem(){
        return item;
    }
}
