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

    public MedTableHelper(Context c) {
        context = c;
        DBHelper = new DatabaseHelper(c);
    }

    public void addMedication(String name){
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

    public void deleteMedication(String name){
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String whereClause = MedicationContract.MEDICATION_NAME_COLUMN + "=?";

        try{
            db.delete(MedicationContract.MEDICATION_TABLE,whereClause,new String[]{name});
        } catch (Exception e) {
            Log.e("SQL exception", "error deleting medication");
        }

        db.close();
    }

    public Medication [] getMedications(){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        String [] columns = new String[] {
                MedicationContract.MEDICATION_ID_COLUMN,
                MedicationContract.MEDICATION_NAME_COLUMN
        };

        Cursor c = db.query(MedicationContract.MEDICATION_TABLE, columns, null, null, null, null,
                MedicationContract.MEDICATION_ID_COLUMN);
        c.moveToFirst();

        Medication[] medications;
        if(c.getCount() > 0){
            medications = new Medication[c.getCount()];
            do{
                Medication m = new Medication(c.getInt(0),c.getString(1));
                medications[c.getPosition()] = m;
            } while(c.moveToNext());
        } else{
            medications = null;
        }
        c.close();
        db.close();

        return medications;
    }


}

