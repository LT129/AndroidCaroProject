package com.example.caroproject.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CaroProject.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //create database
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table PlayerInfo(UserName TEXT primary key, password TEXT)");
    }

    @Override
    //upgrade database
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "PlayerInfo");
    }

    public Boolean UpgradePlayerInfo(String UserName,String Pasword){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("UserName",UserName);
        cv.put("password",Pasword);


        long result = db.insert("PlayerInfo",null, cv);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean checkUsername(String Username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from PlayerInfo where UserName = ?",new String[]{Username});


        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkUsernamePassword(String Username, String Password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from PlayerInfo where username = ? and password = ?",new String[]{Username,Password});


        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }

}

