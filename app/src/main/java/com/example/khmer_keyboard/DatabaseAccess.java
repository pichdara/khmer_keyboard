package com.example.khmer_keyboard;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;

    //private constructor so that object creation from outside the class is avoided
    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);

    }

    //to return the single instance of the database

    public static DatabaseAccess getInstance(Context context){
        if (instance==null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    //open database
    public void open(){
        this.db = openHelper.getReadableDatabase();

    }

    //close database
    public void close(){
        if (db!= null)
        {
            this.db.close();
        }

    }

    //query and return from database

    public String getFullWord(String word){
        c=db.rawQuery("select Definition From Test Where Word = '"+word+"'", new String[]{});
//        List<String> data = new ArrayList<>();
//        while (c.moveToNext()){
//            String def = c.getString(0);
//            data.add(def);
//        }

        StringBuffer buffer = new StringBuffer();
        while (c.moveToNext()){
            String def = c.getString(0);
            buffer.append(""+def);
        }
        return buffer.toString();
    }

}
