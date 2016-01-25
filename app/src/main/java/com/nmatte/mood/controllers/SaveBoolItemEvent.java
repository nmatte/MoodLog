package com.nmatte.mood.controllers;


import com.nmatte.mood.models.BoolItem;

public class SaveBoolItemEvent {

    BoolItem item;

    public SaveBoolItemEvent(BoolItem item){
        this.item = item;
    }


    public BoolItem getItem() {
        return item;
    }

}
