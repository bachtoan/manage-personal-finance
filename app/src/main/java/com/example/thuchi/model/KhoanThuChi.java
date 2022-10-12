package com.example.thuchi.model;

import java.util.Date;

public class KhoanThuChi {
    private int makhoan;
    private int tien;
    private int maloai;
    private String tenLoai;

    public KhoanThuChi(int tien, int maloai) {
        this.tien = tien;
        this.maloai = maloai;
    }

    public KhoanThuChi(int maloai, String tenLoai) {
        this.maloai = maloai;
        this.tenLoai = tenLoai;
    }

    public KhoanThuChi(int makhoan, int tien, int maloai) {
        this.makhoan = makhoan;
        this.tien = tien;
        this.maloai = maloai;
    }

    public KhoanThuChi(int makhoan, int tien, int maloai, String tenLoai) {
        this.makhoan = makhoan;
        this.tien = tien;
        this.maloai = maloai;
        this.tenLoai = tenLoai;
    }

    public int getMakhoan() {
        return makhoan;
    }

    public void setMakhoan(int makhoan) {
        this.makhoan = makhoan;
    }

    public int getTien() {
        return tien;
    }

    public void setTien(int tien) {
        this.tien = tien;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
