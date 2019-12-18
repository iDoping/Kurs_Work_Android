package com.example.kursrpmos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "mydatabase.db";

    public static final String TABLE_COSTS = "costs";
    public static final String COL_ID = "ID";
    public static final String COL_SUM = "COST_SUM";
    public static final String COL_DATE = "COST_DATE";
    public static final String COL_CAT = "COST_CATEGORY";


    public static final String TABLE_TYPECOSTS = "typecosts";
    public static final String COL_COST_TYPE_ID = "_id";
    public static final String COL_NAME_COST = "NAME_OF_COST";

    public static final String TABLE_INCOMES = "incomes";
    public static final String COL_INC_ID = "_id";
    public static final String COL_INC_SUM = "INC_SUM";
    public static final String COL_INC_DATE = "INC_DATE";
    public static final String COL_INC_CAT = "INC_CATEGORY";


    public static final String TABLE_TYPEINCOMES = "typeincomes";
    public static final String COL_INC_TYPE_ID = "ID_INCOME";
    public static final String COL_NAME_INC = "NAME_OF_INC";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_COSTS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, COST_DATE TEXT, COST_CATEGORY INTEGER, COST_SUM REAL,CONSTRAINT COST_CATEGORY FOREIGN KEY(COST_CATEGORY) REFERENCES typecosts(_id) ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE " + TABLE_TYPECOSTS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME_OF_COST TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_INCOMES + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, INC_DATE TEXT, INC_CATEGORY INTEGER, INC_SUM REAL, CONSTRAINT INC_CATEGORY FOREIGN KEY(INC_CATEGORY) REFERENCES typeincomes(_id) ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE " + TABLE_TYPEINCOMES + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME_OF_INC TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COSTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPECOSTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPEINCOMES);
        onCreate(db);
    }

    public void insertLabel(String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO typecosts(NAME_OF_COST) VALUES ('" + label + "')");
        db.close();
    }

    public void insertLabel2(String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO typeincomes(NAME_OF_INC) VALUES ('" + label + "')");
        db.close();
    }

    public void InsertCost(String label1, String label2, String label3) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO costs(COST_SUM, COST_DATE, COST_CATEGORY) VALUES (" + label1 + ",'" + label2 + "',(SELECT _id FROM typecosts WHERE NAME_OF_COST = '" + label3 + "'))");
        db.close();
    }

    public void InsertIncome(String label1, String label2, String label3) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO incomes(INC_SUM, INC_DATE, INC_CATEGORY) VALUES (" + label1 + ",'" + label2 + "',(SELECT _id FROM typeincomes WHERE NAME_OF_INC = '" + label3 + "'))");
        db.close();
    }

    public void DeleteCost(String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM typecosts where NAME_OF_COST = '" + label + "'");
        db.close();
    }

    public void DeleteIncome(String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM typeincomes where NAME_OF_INC = '" + label + "'");
        db.close();
    }

    public List<String> getAllLabels() {
        List<String> labels = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_TYPECOSTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;
    }

    public List<String> getAllLabels2() {
        List<String> labels = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_TYPEINCOMES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return labels;
    }

    public ArrayList<BarEntry> getBarEntriesCosts(String label1, String label2) {
        String cost_sum_column = "COST_SUM";
        String cost_sum_column2 = "COST_CATEGORY";
        String date = "COSTS.COST_DATE >= '" + label1 + "' AND COSTS.COST_DATE <= '" + label2 + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{"SUM(COST_SUM) AS " + cost_sum_column, "COST_CATEGORY AS " + cost_sum_column2};
        Cursor csr = db.query("costs JOIN TYPECOSTS ON COSTS.COST_CATEGORY = TYPECOSTS._id", columns, date, null, "COST_CATEGORY", null, null);
        int barEntry = 1;
        ArrayList<BarEntry> rv = new ArrayList<>();
        while (csr.moveToNext()) {
            rv.add(new BarEntry(
                    barEntry,
                    csr.getFloat(csr.getColumnIndex(cost_sum_column))
            ));
            barEntry++;
        }
        csr.close();
        return rv;
    }

    public ArrayList<BarEntry> getBarEntriesIncomes(String label1, String label2) {
        String income_sum_column = "INC_SUM";
        String income_sum_column2 = "INC_CATEGORY";
        String date = "INCOMES.INC_DATE >= '" + label1 + "' AND INCOMES.INC_DATE <= '" + label2 + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{"SUM(INC_SUM) AS " + income_sum_column, "INC_CATEGORY AS " + income_sum_column2};
        Cursor csr = db.query("incomes JOIN typeincomes ON incomes.INC_CATEGORY = TYPEINCOMES._id", columns, date, null, "INC_CATEGORY", null, null);
        int barEntry = 1;
        ArrayList<BarEntry> rv = new ArrayList<>();
        while (csr.moveToNext()) {
            rv.add(new BarEntry(
                    barEntry,
                    csr.getFloat(csr.getColumnIndex(income_sum_column))
            ));
            barEntry++;
        }
        csr.close();
        return rv;
    }

    public List<String> LabelsOfCosts() {

        List<String> labels = new ArrayList<String>();
        labels.add(" ");
        String selectQuery = "SELECT SUM(COST_SUM),NAME_OF_COST FROM COSTS JOIN TYPECOSTS ON COSTS.COST_CATEGORY = TYPECOSTS._id group by COST_CATEGORY ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;
    }

    public List<String> LabelsOfIncomes() {

        List<String> labels = new ArrayList<String>();
        labels.add(" ");
        String selectQuery = "SELECT SUM(INC_SUM),NAME_OF_INC FROM INCOMES JOIN TYPEINCOMES ON INCOMES.INC_CATEGORY = TYPEINCOMES._id group by INC_CATEGORY ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;
    }

    public List<String> test() {

        List<String> labels = new ArrayList<String>();
        labels.add(" ");

        // Select All Query

        String selectQuery = "SELECT NAME_OF_COST FROM COSTS JOIN TYPECOSTS ON COSTS.COST_CATEGORY = TYPECOSTS._id group by COST_CATEGORY ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        return labels;
    }


    public List<String> test2() {

        List<String> labels = new ArrayList<String>();
        labels.add(" ");

        // Select All Query

        String selectQuery = "SELECT NAME_OF_INC FROM INCOMES JOIN TYPEINCOMES ON INCOMES.INC_CATEGORY = TYPEINCOMES._id group by INC_CATEGORY ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        return labels;
    }

    public Cursor SelectCostsToList() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TYPECOSTS;
        return db.rawQuery(query, null);
    }

    public Cursor SelectIncomesToList() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TYPEINCOMES;
        return db.rawQuery(query, null);
    }
}