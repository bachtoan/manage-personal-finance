package com.example.thuchi.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuchi.PhanLoaiActivity;
import com.example.thuchi.R;
import com.example.thuchi.adapter.LoaithuAdapter;
import com.example.thuchi.adapter.PhanLoaiAdapter;
import com.example.thuchi.dao.PhanLoaiDAO;
import com.example.thuchi.model.PhanLoai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PhanLoaiFragment extends Fragment {
    RecyclerView rvLoai;
    PhanLoaiDAO plDao;
    ArrayList<PhanLoai> list = new ArrayList<>();
    PhanLoaiAdapter adapter;
    FloatingActionButton fabAdd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_phan_loai,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabAdd = view.findViewById(R.id.fabThemLoai);
        rvLoai = view.findViewById(R.id.rcvPhanLoai);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvLoai.setLayoutManager(layoutManager);

        plDao = new PhanLoaiDAO(getContext());
        list = plDao.getAll();

        adapter = new PhanLoaiAdapter(getContext(),list);
        rvLoai.setAdapter(adapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogThem();
            }
        });
    }

    private void showDialogThem(){
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
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
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,arrTT);
        spinner.setAdapter(spnAdapter);

        //Them du lieu
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = etTen.getText().toString();
                String tt = (String) spinner.getSelectedItem();

                PhanLoai phanLoai = new PhanLoai(ten,tt);

                if(plDao.insert(phanLoai)){
                    Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    list.clear();
                    list.addAll(plDao.getAll());
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(), "Thêm mới thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
