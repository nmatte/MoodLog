package com.nmatte.mood.medications;

public class Medication {
    private long ID;
    private String name = "";

    public Medication(int ID, String name){
        this.ID = ID;
        this.name = name;
    }

    public Medication(String name){
        this.name = name;
    }

    public long getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
