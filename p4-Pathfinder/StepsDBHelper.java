package com.example.ludwi.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.util.Log;

import java.util.ArrayList;

/**
 * StepsDBHelper is database that stores the steps the user takes.
 * Created by ludwig Ninn on 21/02/2017.
 */

public class StepsDBHelper extends SQLiteOpenHelper {
    private static final String TABLE_STEPS_SUMMARY = "StepsSummary";
    private static final String ID = "id";
    private static final String STEPS_COUNT = "stepscount";

    private static final String CREATE_TABLE_STEPS_SUMMARY = "CREATE TABLE "
            + TABLE_STEPS_SUMMARY + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + STEPS_COUNT + " INTEGER" + ")";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "StepsDatabase";


    public StepsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("StepsDBHelper", "connected");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_STEPS_SUMMARY);
        Log.d("StepsDBHelper", "connected");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * Resets the database with value zero.
     * @return
     */
    public boolean resetDatabase(){
        boolean resetSuccesful = false;
        try {
            SQLiteDatabase wdb = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(STEPS_COUNT, 0);
            long row = wdb.insert(TABLE_STEPS_SUMMARY, null, values);
            resetSuccesful=true;


        } catch (Exception e) {
            e.printStackTrace();
            resetSuccesful = false;
        }
        return resetSuccesful;

    }

    /**
     * Creats a steps. Returens a boolean if the step was succefuly stored or not.
     * @return
     */
    public boolean createStepsEntry() {
        boolean createSuccessful = false;
        int currentDateStepCounts = 0;

        String selectQuery = "SELECT " + STEPS_COUNT + " FROM " + TABLE_STEPS_SUMMARY ;
        try {
            SQLiteDatabase rdb = this.getReadableDatabase();
            Cursor c = rdb.rawQuery(selectQuery, null);
            if (c.moveToFirst()) {
                do {
                    currentDateStepCounts = c.getInt((c.getColumnIndex(STEPS_COUNT)));
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            Log.d("StepsDBHelper", "SQLiteDatabase1");
            e.printStackTrace();
        }
        try {
                SQLiteDatabase wdb = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(STEPS_COUNT, ++currentDateStepCounts);
               wdb.insert(TABLE_STEPS_SUMMARY, null, values);
            createSuccessful = true;


        } catch (Exception e) {
            e.printStackTrace();
            createSuccessful = false;
        }
        return createSuccessful;

    }

    /**
     *
     * Reads a steps. Returns the current steps taken by the user. Since reset.
     * @return
     */
    public int readStepsEntries() {
        int mStepCount=0;
        String selectQuery = "SELECT * FROM " + TABLE_STEPS_SUMMARY;
        try {
            SQLiteDatabase sdb = this.getReadableDatabase();
            Cursor c = sdb.rawQuery(selectQuery, null);
            if (c.moveToFirst()) {
                do {

                     mStepCount = c.getInt((c.getColumnIndex(STEPS_COUNT)));

                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("StepsDBHelper", "SQLiteDatabase3");
        }
        return mStepCount;
    }


}
