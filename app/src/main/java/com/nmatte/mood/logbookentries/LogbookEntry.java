package com.nmatte.mood.logbookentries;


import com.nmatte.mood.medications.Medication;
import com.nmatte.mood.util.CalendarDatabaseUtil;

import java.util.ArrayList;
import java.util.Calendar;

public class LogbookEntry {

    ArrayList<Boolean> moods;
    int irrValue = 0,
        anxValue = 0,
        hoursSleptValue = 0;
    Calendar date;
    ArrayList<Medication> medications;

    public LogbookEntry (){
        this.moods = new ArrayList<Boolean>();
        this.medications = new ArrayList<>();
        this.date = Calendar.getInstance();
    }

    public LogbookEntry (Calendar date){
        this.moods = new ArrayList<Boolean>();
        this.medications = new ArrayList<>();
        this.date = date;
    }

    public LogbookEntry(Calendar date, String moodString, int irr, int anx, int sleep,  String medString){
        this.moods = parseMoodString(moodString);
        this.irrValue = irr;
        this.anxValue = anx;
        this.hoursSleptValue = sleep;
        this.date = date;
        this.medications = Medication.parseIDString(medString);

    }

    public LogbookEntry(int dateInt, String moodString, int irr, int anx, int sleep,  String medString){
        this.moods = parseMoodString(moodString);
        this.irrValue = irr;
        this.anxValue = anx;
        this.hoursSleptValue = sleep;
        Calendar dateCal = Calendar.getInstance();
        dateCal.set(Calendar.YEAR, dateInt / 1000);
        dateCal.set(Calendar.DAY_OF_YEAR, dateInt % 1000);
        this.date = dateCal;
        this.medications = Medication.parseIDString(medString);

    }




    public Calendar getDate() {
        return date;
    }

    public int getDateInt(){
        return CalendarDatabaseUtil.calendarToInt(date);
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getIrrValue() {
        return irrValue;
    }

    public void setIrrValue(int irrValue) {
        this.irrValue = irrValue;
    }

    public int getAnxValue() {
        return anxValue;
    }

    public void setAnxValue(int anxValue) {
        this.anxValue = anxValue;
    }

    public int getHoursSleptValue() {
        return hoursSleptValue;
    }

    public void setHoursSleptValue(int hoursSleptValue) {
        this.hoursSleptValue = hoursSleptValue;
    }

    public ArrayList<Boolean> getMoods() {
        return moods;
    }

    public void setMoods(ArrayList<Boolean> moods) {
        this.moods = moods;
    }

    public String moodString(){
        String result = "";
        for (int i = 0; i < moods.size(); i++){
            result += moods.get(i) ? "T " : "F ";
        }
        return result;
    }

    public String medicationString() {

        return Medication.IDString(medications);
    }



    private static ArrayList<Boolean> parseMoodString (String moodString){
        ArrayList<Boolean> result = new ArrayList<>();
        String [] boolStrings = moodString.split(" ");
        for (String mood: boolStrings){
            if (!mood.equals("")) {
                result.add(mood.equals("T"));
            }
        }
        return result;
    }

    public void setMedications(ArrayList medications) {
        this.medications = medications;
    }

    public ArrayList<Medication> getMedications() {
        return medications;
    }
}
