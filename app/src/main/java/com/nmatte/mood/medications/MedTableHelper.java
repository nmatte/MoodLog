package com.nmatte.mood.medications;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nmatte.mood.moodlog.DatabaseHelper;

import java.util.ArrayList;

public class MedTableHelper {
    DatabaseHelper DBHelper;
    Context context;

    public MedTableHelper(Context context) {
        this.context = context;
        this.DBHelper = new DatabaseHelper(context);
    }



    public static void addMedication(Context context, String name){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MedicationContract.MEDICATION_NAME_COLUMN, name);

        try{
            db.insert(MedicationContract.MEDICATION_TABLE,null,values);
        } catch (Exception e){
            Log.e("SQL exception", "error adding medication");
        }

        db.close();
    }

    public static void deleteMedication(Context context, String name){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String whereClause = MedicationContract.MEDICATION_NAME_COLUMN + "=?";

        try{
            db.delete(MedicationContract.MEDICATION_TABLE, whereClause, new String[]{name});
        } catch (Exception e) {
            Log.e("SQL exception", "error deleting medication");
        }

        db.close();
    }

    public static ArrayList<Medication> getMedicationList(Context context){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        String [] columns = new String[] {
                MedicationContract.MEDICATION_ID_COLUMN,
                MedicationContract.MEDICATION_NAME_COLUMN
        };

        Cursor c = db.query(MedicationContract.MEDICATION_TABLE, columns, null, null, null, null,
                MedicationContract.MEDICATION_ID_COLUMN);
        c.moveToFirst();

        ArrayList<Medication> medications = new ArrayList<>();
        if(c.getCount() > 0){
            do{
                Medication m = new Medication(c.getInt(0),c.getString(1));
                medications.add(m);
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        return medications;

    }

    public static ArrayList<String>  mapIDsToNames(ArrayList<Long> ids, Context context) {
        ArrayList<String> result = new ArrayList<>();

        for (Medication m : getMedicationList(context)){
            if (ids.contains(m.getID())){
                result.add(m.getName());
            }
        }

        return result;
    }

}

