package com.example.thuchi.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thuchi.R;
import com.example.thuchi.adapter.LoaithuAdapter;
import com.example.thuchi.adapter.ThuAdapter;
import com.example.thuchi.dao.KhoanThuChiDAO;
import com.example.thuchi.model.PhanLoai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LoaiThuFragment extends Fragment {
    ListView listViewLoaiThu;
    FloatingActionButton floatAdd;
    LoaithuAdapter adapter;
    ArrayList<PhanLoai> list;
    KhoanThuChiDAO khoanThuChiDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loaithu_fragment,container,false);
        listViewLoaiThu = view.findViewById(R.id.listViewLoaiThu);
        floatAdd = view.findViewById(R.id.floatAdd);

        khoanThuChiDAO = new KhoanThuChiDAO(getContext());
        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogThem();
            }
        });
        return view;
    }

    private void loadData(){
        list = khoanThuChiDAO.getDSLoai("Thu");
        adapter = new LoaithuAdapter(list,getContext(),khoanThuChiDAO);
        listViewLoaiThu.setAdapter(adapter);
    }

    private void showDialogThem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themloaithu,null);
        EditText edtInput = view.findViewById(R.id.edtInput);
        builder.setView(view);
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenloai = edtInput.getText().toString();
                PhanLoai phanLoai = new PhanLoai(tenloai,"Thu");
                if(khoanThuChiDAO.themMoiLoaiThuChi(phanLoai)){
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else{
                    Toast.makeText(getContext(), "Không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
