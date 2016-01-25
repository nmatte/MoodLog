package com.nmatte.mood.controllers;


import com.nmatte.mood.models.NumItem;

public class SaveNumItemEvent {
    NumItem item;

    public SaveNumItemEvent(NumItem item) {
        this.item = item;
    }

    public NumItem getItem(){
        return item;
    }
}
