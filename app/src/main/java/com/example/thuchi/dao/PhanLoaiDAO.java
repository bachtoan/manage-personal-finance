package com.example.thuchi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.example.thuchi.database.DbHelper;
import com.example.thuchi.model.PhanLoai;

import java.util.ArrayList;

public class PhanLoaiDAO {
    DbHelper dbHelper;

    public PhanLoaiDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    public ArrayList<PhanLoai> getAll(){
        ArrayList<PhanLoai> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM PHANLOAI";
        Cursor cs = db.rawQuery(sql,null);
        cs.moveToFirst();
        while(!cs.isAfterLast()){
            int ma = cs.getInt(0);
            String ten = cs.getString(1);
            String trangthai = cs.getString(2);
            list.add(new PhanLoai(ma,ten,trangthai));
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return list;
    }
    public boolean insert(PhanLoai phanLoai){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenLoai",phanLoai.getTenLoai());
        values.put("TrangThai",phanLoai.getTrangThai());
        long row = db.insert("PHANLOAI",null,values);
        return (row>0);
    }
    public boolean update(PhanLoai phanLoai){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenLoai",phanLoai.getTenLoai());
        values.put("TrangThai",phanLoai.getTrangThai());
        int row = db.update("PHANLOAI",values,"MaLoai=?",new String[]{phanLoai.getMaLoai()+""});
        return (row>0);
    }
    public boolean delete(int maLoai){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int row = db.delete("PHANLOAI","MaLoai=?",new String[]{maLoai+""});
        return (row>0);
    }
}
