package com.example.thuchi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thuchi.database.DbHelper;
import com.example.thuchi.model.KhoanThuChi;
import com.example.thuchi.model.PhanLoai;

import java.util.ArrayList;

public class KhoanThuChiDAO {
    DbHelper dbHelper;
    public KhoanThuChiDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    public ArrayList<PhanLoai> getDSLoai(String loai){
        ArrayList<PhanLoai> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from PHANLOAI",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                String trangthai = cursor.getString(2);
                if(trangthai.equals(loai)){
                    list.add(new PhanLoai(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
                }
            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean themMoiLoaiThuChi(PhanLoai phanLoai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenLoai",phanLoai.getTenLoai());
        contentValues.put("TrangThai",phanLoai.getTrangThai());
        long check = sqLiteDatabase.insert("PHANLOAI",null,contentValues);
        if(check == -1)
            return false;
        return true;
    }
    public boolean capNhatLoaiThuChi(PhanLoai phanLoai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenLoai",phanLoai.getTenLoai());
        contentValues.put("TrangThai",phanLoai.getTrangThai());
        long check = sqLiteDatabase.update("PHANLOAI",contentValues,"MaLoai = ?", new String[]{phanLoai.getMaLoai()+""});
        if(check == -1)
            return false;
        return true;
    }
    public boolean xoaLoaiThuChi(int maLoai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("PHANLOAI","MaLoai = ?",new String[]{String.valueOf(maLoai)});
        if(check == -1)
            return false;
        return true;
    }

    public ArrayList<KhoanThuChi> getDSKhoanThuChi(String loai){
        ArrayList<KhoanThuChi> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String query = "SELECT k.makhoan, k.tien, k.maloai, l.tenloai from khoanthuchi k, phanloai l where k.maloai = l.maloai and l.trangthai = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{loai});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                list.add(new KhoanThuChi(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themMoiKhoanThuChi(KhoanThuChi khoanThuChi){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tien", khoanThuChi.getTien());
        contentValues.put("maloai",khoanThuChi.getMaloai());
        long check = sqLiteDatabase.insert("khoanthuchi", null,contentValues);
        if (check == -1){
            return false;
        }return true;
    }

    public boolean capNhatKhoanThuChi(KhoanThuChi khoanThuChi){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tien", khoanThuChi.getTien());
        contentValues.put("maloai",khoanThuChi.getMaloai());
        long check = sqLiteDatabase.update("khoanthuchi",contentValues,"makhoan = ?",new String[]{String.valueOf(khoanThuChi.getMakhoan())});
        if (check == -1){
            return false;
        }return true;
    }

    public boolean xoaKhoanThuChi(int makhoan){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("KHOANTHUCHI","makhoan = ?",new String[]{String.valueOf(makhoan)});
        if (check == -1){
            return false;
        }return true;
    }

    public float[] getThongTinThuChi() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        int thu = 0, chi = 0;
        //select sum(tien)
        //from giaodich
        //where maloai in (select maloai from phanloai where thangthai = 'thu')
        Cursor cursorThu = sqLiteDatabase.rawQuery("select sum(tien) from KHOANTHUCHI where maloai in (select maloai from Phanloai where trangthai = 'thu') ", null);
        if (cursorThu.getCount() != 0) {
            cursorThu.moveToFirst();
            thu = cursorThu.getInt(0);
        }
        //select sum(tien)
        //from giaodich
        //where maloai in (select maloai from phanloai where thangthai = 'chi')
        Cursor cursorChi = sqLiteDatabase.rawQuery("select sum(tien) from KHOANTHUCHI where maloai in (select maloai from Phanloai where trangthai = 'chi') ", null);
        if (cursorChi.getCount() != 0) {
            cursorChi.moveToFirst();
            chi = cursorChi.getInt(0);
        }
        float[] ketQua = new float[]{thu, chi};
        return ketQua;
    }
}
