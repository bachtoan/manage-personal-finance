package com.example.thuchi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thuchi.adapter.PhanLoaiAdapter;
import com.example.thuchi.dao.PhanLoaiDAO;
import com.example.thuchi.model.PhanLoai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PhanLoaiActivity extends AppCompatActivity {
    RecyclerView rvLoai;
    PhanLoaiDAO plDao;
    ArrayList<PhanLoai> list = new ArrayList<>();
    PhanLoaiAdapter adapter;
    FloatingActionButton fabAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phan_loai);

        fabAdd = findViewById(R.id.fabThemLoai);
        rvLoai = findViewById(R.id.rcvPhanLoai);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PhanLoaiActivity.this);
        rvLoai.setLayoutManager(layoutManager);

        plDao = new PhanLoaiDAO(PhanLoaiActivity.this);
        list = plDao.getAll();

        adapter = new PhanLoaiAdapter(PhanLoaiActivity.this,list);
        rvLoai.setAdapter(adapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAdd();
            }
        });
    }
    private void openDialogAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PhanLoaiActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.phanloai_add,null);
        builder.setView(view);
        Spinner spinner = view.findViewById(R.id.spnTrangThai);
        EditText etTen = view.findViewById(R.id.edtTenLoai);
        Button btnThem = view.findViewById(R.id.btnThemLoai);
        Button btnHuy = view.findViewById(R.id.btnHuyLoai);

        Dialog dialog = builder.create();
        dialog.show();

        //Tao du lieu cho spiner
        String[] arrTT = {"Thu","Chi"};
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<>(PhanLoaiActivity.this,android.R.layout.simple_spinner_dropdown_item,arrTT);
        spinner.setAdapter(spnAdapter);

        //Them du lieu
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = etTen.getText().toString();
                String tt = (String) spinner.getSelectedItem();
                
                PhanLoai phanLoai = new PhanLoai(ten,tt);
                
                if(plDao.insert(phanLoai)){
                    Toast.makeText(PhanLoaiActivity.this, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    list.clear();
                    list.addAll(plDao.getAll());
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(PhanLoaiActivity.this, "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}