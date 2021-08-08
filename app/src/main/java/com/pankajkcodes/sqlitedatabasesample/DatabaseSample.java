package com.pankajkcodes.sqlitedatabasesample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import androidx.annotation.Nullable;

import java.util.List;

public class DatabaseSample extends SQLiteOpenHelper {
    // Create Database
    private static final String dbname = "signUp.db";
    // Create Constructor
    public DatabaseSample(@Nullable Context context) {
        super(context, dbname, null ,1);
    }

    // Implements methods
    @Override
    public void onCreate(SQLiteDatabase db) {
        //code for database connectivity
        String q = "create table users(_id Integer primary key autoincrement,name text,username text,password text)";
        db.execSQL(q);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop  table if exists users");
        onCreate(db);

    }
    public boolean insert_data(String name,String username,String password){
        SQLiteDatabase sqlDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put("username",username);
        cv.put(" password", password);
        long insert = sqlDb.insert("users",null,cv);
        if (insert==-1) return false;
        else
            return true;
    }

    public boolean delete_data(String username){
        SQLiteDatabase sqlDb = this.getWritableDatabase();
        Cursor cursor = sqlDb.rawQuery("select * from users where username=?",new String[]{username});
        if (cursor.getCount()>0){
           long delete = sqlDb.delete("users","username=?",new String[]{username});
           if (delete==-1){
               return false;
           }else
               return true;
        }else
            return false;


    }


    public boolean update_data(String name,String username,String password){
        SQLiteDatabase sqlDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put(" password", password);
        Cursor cursor = sqlDb.rawQuery("select * from users where username=?",new String[]{username});
        if (cursor.getCount()>0){
            long update = sqlDb.update("users",cv,"username=?",new String[]{username});
            if (update==-1){
                return false;
            }else return true;
        }
        else return false;
    }

    public Cursor getInfo() {
        SQLiteDatabase sqlDb = this.getReadableDatabase();
        // generate Query to read data from database
        String select = "SELECT * FROM users";
        Cursor cursor = sqlDb.rawQuery(select,null);
        return cursor;
    }
}
