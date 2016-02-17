package com.nmatte.mood.models;

/**
 * These are one of the building blocks of the chart.
 * They represent checkboxes. They are also used in the MoodModule, for instance.
 */
public class BoolComponent extends LogbookComponent {

    public BoolComponent(Long id, String name, int color) {
        this(id, name);
    }

    public BoolComponent(Long id, String name){
        super(id, name);
    }

    public BoolComponent(String name){
        super(name);
    }

    public BoolComponent(){
        super();
    }

    @Override
    protected String prefix() {
        return "B";
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
        if (!(o instanceof BoolComponent))
            return false;
        else {
            BoolComponent rhs = (BoolComponent) o;
            if (rhs.getID() == null || getID() == null)
                return this.getName().equals(rhs.getName());
            return this.getID().equals(rhs.getID());
        }
    }
}
