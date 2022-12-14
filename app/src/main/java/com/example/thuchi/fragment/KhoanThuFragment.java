package com.example.thuchi.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thuchi.R;
import com.example.thuchi.adapter.KhoanThuAdapter;
import com.example.thuchi.dao.KhoanThuChiDAO;
import com.example.thuchi.model.KhoanThuChi;
import com.example.thuchi.model.PhanLoai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class KhoanThuFragment extends Fragment {
    ListView listViewKhoanThu;
    ArrayList<KhoanThuChi> list;
    KhoanThuChiDAO khoanThuChiDAO;
    ArrayList<HashMap<String, Object>> listSpiner;
    KhoanThuAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.khoanthu_fragment,container,false);

        listViewKhoanThu = view.findViewById(R.id.listViewKhoanThu);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);

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
        list = khoanThuChiDAO.getDSKhoanThuChi("Thu");

        adapter = new KhoanThuAdapter(list,getContext(),khoanThuChiDAO,getDataSpiner());
        listViewKhoanThu.setAdapter(adapter);
    }

    private void showDialogThem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_themkhoanthu,null);
        Spinner spnLoaithu = view.findViewById(R.id.spnLoaithu);
        EditText edtTien = view.findViewById(R.id.edtTien);
        builder.setView(view);

        SimpleAdapter adapter = new SimpleAdapter(
                getContext(),
                getDataSpiner(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spnLoaithu.setAdapter(adapter);

        builder.setPositiveButton("Th??m", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tien = edtTien.getText().toString();
                HashMap<String, Object> selected = (HashMap<String, Object>) spnLoaithu.getSelectedItem();
                int maloai = (int) selected.get("maloai");
                KhoanThuChi khoanThuChi = new KhoanThuChi(Integer.parseInt(tien),maloai);
                if(khoanThuChiDAO.themMoiKhoanThuChi(khoanThuChi)){
                    Toast.makeText(getContext(), "Th??m th??nh c??ng", Toast.LENGTH_SHORT).show();
                    loadData();
                }else {
                    Toast.makeText(getContext(), "Th??m th???t b???i", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hu???", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Th??m kho???n thu");
        alertDialog.show();
    }

    private ArrayList<HashMap<String, Object>> getDataSpiner(){
        ArrayList<PhanLoai> listLoai = khoanThuChiDAO.getDSLoai("Thu");
        listSpiner = new ArrayList<>();

        for(PhanLoai phanLoai : listLoai){
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("maloai",phanLoai.getMaLoai());
            hashMap.put("tenloai",phanLoai.getTenLoai());
            listSpiner.add(hashMap);
        }
        return listSpiner;
    }
}
