package com.example.project2option1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemDB extends SQLiteOpenHelper {
    private static final String database_name = "Item.db";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "ITEM";
    private static final String COL_3 = "QUANTITY";

    public ItemDB(Context context) {
        super(context, database_name, null, 1);
    }

    //makes the table for accounts
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Items(ID INTEGER PRIMARY KEY AUTOINCREMENT, ITEM TEXT, QUANTITY INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);
    }

    //puts new entries into database
    //TODO: add data validation
    public boolean insert(String item, Number quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, item);
        contentValues.put(COL_3, quantity.toString());

        long result = db.insert("Items", null, contentValues);
        //verifies that it was successful
        return result != -1;
    }
    //TODO: add data validation


    public String verifyLogin(String item, Number quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COL_2};
        String selection = "ITEM=? AND QUANTITY=?";
        String[] selectionArgs = {item, quantity.toString()};
        Cursor cursor = db.query("Items", columns, selection, selectionArgs, null, null, null);
        String result = null;

        if (cursor != null && cursor.moveToFirst()) {

            result = cursor.getString(cursor.getColumnIndexOrThrow(COL_2));
            cursor.close();
        }
        return result;
    }
}