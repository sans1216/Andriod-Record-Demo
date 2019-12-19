package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_DB = "CREATE TABLE IF NOT EXISTS user_info3"+"("
            + "bw VARCHAR, "
            + "address VARCHAR, "
            + "date VARCHAR,"
            + "cost DOUBLE,"
            + "AllType VARCHAR,"
            + "type VARCHAR"
            +")";
    private Context mContext;
    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
        ContentValues values = new ContentValues();
        values.put("bw",0);
        values.put("address",0);
        values.put("date",0);
        values.put("cost",0);
        values.put("AllType","收入");
        values.put("type",0);
        db.insert("user_info3",null,values);
        ContentValues values2 = new ContentValues();
        values2.put("bw",0);
        values2.put("address",0);
        values2.put("date",0);
        values2.put("cost",0);
        values2.put("AllType","支出");
        values2.put("type",0);
        db.insert("user_info3",null,values);
        Toast.makeText(mContext,"数据库建立成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user_info3");
        db.execSQL(CREATE_DB );
        onCreate(db);
    }






}
