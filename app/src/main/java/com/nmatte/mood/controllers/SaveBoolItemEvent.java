package com.nmatte.mood.controllers;


import com.nmatte.mood.models.BoolComponent;

public class SaveBoolItemEvent {

    BoolComponent item;

    public SaveBoolItemEvent(BoolComponent item){
        this.item = item;
    }


    public BoolComponent getItem() {
        return item;
    }

}
