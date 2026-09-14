package com.example.project2option1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RegisterActivity extends SQLiteOpenHelper {
    private static final String database_name = "Login.db";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "USERNAME";
    private static final String COL_3 = "PASSWORD";

    public RegisterActivity(Context context) {
        super(context, database_name, null, 1);
    }

    //makes the table for accounts
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Users(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        onCreate(db);
    }

    //puts new entries into database
    //TODO: add data validation
    public boolean insert(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, password);

        long result = db.insert("Users", null, contentValues);
        //verifies that it was successful
        return result != -1;
    }
    //TODO: add data validation


    public String verifyLogin(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {COL_2};
        String selection = "USERNAME=? AND PASSWORD=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query("Users", columns, selection, selectionArgs, null, null, null);
        String result = null;

        if (cursor != null && cursor.moveToFirst()) {

            result = cursor.getString(cursor.getColumnIndexOrThrow(COL_2));
            cursor.close();
        }
        return result;
    }
}