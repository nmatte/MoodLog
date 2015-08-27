package com.nmatte.mood.logbookitems.boolitems;


public class SaveBoolItemEvent {

    BoolItem item;

    SaveBoolItemEvent(BoolItem item){
        this.item = item;
    }


    public BoolItem getItem() {
        return item;
    }

}
