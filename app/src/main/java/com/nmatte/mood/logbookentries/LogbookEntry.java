package com.nmatte.mood.logbookentries;


import com.nmatte.mood.medications.Medication;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LogbookEntry {

    ArrayList<Boolean> moods;
    int irrValue = 0,
        anxValue = 0,
        hoursSleptValue = 0;
    long date = 0;
    ArrayList<Medication> medications;

    public LogbookEntry (){
        moods = new ArrayList<Boolean>();
        medications = new ArrayList<>();
        setDateCurrent();
    }

    public LogbookEntry(long date, String moodString, int irr, int anx, int sleep,  String medString){
        this.moods = parseMoodString(moodString);
        this.irrValue = irr;
        this.anxValue = anx;
        this.hoursSleptValue = sleep;
        this.date = date;
        this.medications = parseMedicationString(medString);

    }

    public void setDateCurrent(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        this.date = c.getTimeInMillis();
    }

    public String getSummaryString(){
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
        String dateString = format.format(date);

        String summary = moodString() + "\n" +
                         dateString + "\n" +
                         irrValue + "\n" +
                         anxValue + "\n" +
                         hoursSleptValue + "\n" +
                         medicationString();

        return summary;

    }

    public long getDate() {
        return date;
    }

    public void setDate(int date) {
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
            if(moods.get(i))
                result += String.valueOf(i) + " ";
        }
        return result;
    }

    public String medicationString() {
        String result = "";
        for (Medication med : medications){
            String idString = String.valueOf(med.getID());
            result += idString + " ";
        }
        return result;
    }

    private ArrayList<Medication> parseMedicationString(String medicationString){
        ArrayList<Medication> result = new ArrayList<>();
        for (String med : medicationString.split(" ")){
            long id = Integer.valueOf(med);
            result.add(new Medication(id,null));
        }
        return result;
    }

    private static ArrayList<Boolean> parseMoodString (String moodString){
        ArrayList<Boolean> result = new ArrayList<>();
        ArrayList<Integer> moodInts = new ArrayList<>();
        for (String mood: moodString.split(" ")){
            moodInts.add(Integer.parseInt(mood));
        }

        for (int i = 0; i < 13; i++){
            result.add((moodInts.contains(i)));
        }
        return result;
    }

    public void setMedications(ArrayList medications) {
        this.medications = medications;
    }
}
