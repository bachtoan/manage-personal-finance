package com.example.thuchi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context){
        // tao database
        super(context,"ASM_MOB202",null,2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE PHANLOAI(MaLoai integer primary key autoincrement,"+
                "TenLoai text, TrangThai text)";
        db.execSQL(sql);
        sql = "INSERT INTO PHANLOAI VALUES(null,'Lương','Thu')";
        db.execSQL(sql);
        sql = "INSERT INTO PHANLOAI VALUES(null,'Thu nợ','Thu')";
        db.execSQL(sql);
        sql = "INSERT INTO PHANLOAI VALUES(null,'Khác','Thu')";
        db.execSQL(sql);
        sql = "INSERT INTO PHANLOAI VALUES(null,'Du lịch','Chi')";
        db.execSQL(sql);
        sql = "INSERT INTO PHANLOAI VALUES(null,'Sinh hoạt','Chi')";
        db.execSQL(sql);

        sql = "CREATE TABLE KHOANTHUCHI(makhoan integer primary key autoincrement, tien integer, maloai integer)";
        db.execSQL(sql);
        sql = "INSERT INTO KHOANTHUCHI VALUES(1,5000,1),(2,15000,3),(3,10000,2)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
