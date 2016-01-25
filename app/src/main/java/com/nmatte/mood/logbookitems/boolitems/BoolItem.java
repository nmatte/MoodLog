package com.nmatte.mood.logbookitems.boolitems;

import com.nmatte.mood.logbookitems.LogbookItem;

public class BoolItem extends LogbookItem {

    public BoolItem(Long id, String name){
        super(id,name);
    }

    public BoolItem(String name){
        super(name);
    }

    public BoolItem(){
        super();
    }

    @Override
    public String toString(){
        return "B" + id.toString();
    }

    @Override
    public int hashCode() {
        long prime = 331;
        long hash = 1;
        hash = hash * ((id == null? 0 : id) + prime);
        return (int) hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!(o instanceof  BoolItem))
            return false;
        else {
            BoolItem rhs = (BoolItem) o;
            if (rhs.getID() == null || getID() == null)
                return false;
            return this.getID().equals(rhs.getID());
        }
    }
}
