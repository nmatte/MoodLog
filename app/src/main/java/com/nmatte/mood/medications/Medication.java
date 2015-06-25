package com.nmatte.mood.medications;

import java.util.ArrayList;

public class Medication {
    private long ID;
    private String name;

    public Medication(long ID, String name){
        this.ID = ID;
        this.name = name;
    }

    public Medication(String name){
        this.name = name;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String IDString (ArrayList<Medication> medications){

        String result = "";
        for (Medication med : medications){
            String idString = String.valueOf(med.getID());
            result += idString + " ";
        }
        return result;
    }

    public static ArrayList<Medication> parseIDString(String medicationString){
        ArrayList<Medication> result = new ArrayList<>();
        for (String med : medicationString.split(" ")){
            if (!med.equals("")) {
                long id = Integer.valueOf(med);
                result.add(new Medication(id, null));
            }
        }
        return result;
    }
}
