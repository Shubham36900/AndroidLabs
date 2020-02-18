package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Opener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAMES = "ChatDB";
    protected final static int VERSION_NUM = 2;
    public final static String TABLE_NAME = "CHAT";
    public final static String COL_NAME= "MESSAGE";
    public final static String COL_ID = "_id";
    public final static String COL_TYPE = "type";

    public Opener(Context ctx) {
        super(ctx, DATABASE_NAMES, null, VERSION_NUM);


    }
   @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME  + " text,"  + COL_TYPE + " text);");  // add or remove columns
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }

    //this function gets called if the database version on your device is higher than VERSION_NUM
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }

}
