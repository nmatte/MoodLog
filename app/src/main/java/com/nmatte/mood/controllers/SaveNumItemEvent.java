package com.nmatte.mood.controllers;


import com.nmatte.mood.models.components.NumComponent;

public class SaveNumItemEvent {
    NumComponent item;

    public SaveNumItemEvent(NumComponent item) {
        this.item = item;
    }

    public NumComponent getItem(){
        return item;
    }
}
