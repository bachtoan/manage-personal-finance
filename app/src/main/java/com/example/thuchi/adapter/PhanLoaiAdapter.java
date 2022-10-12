package com.example.thuchi.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuchi.PhanLoaiActivity;
import com.example.thuchi.R;
import com.example.thuchi.dao.PhanLoaiDAO;
import com.example.thuchi.model.PhanLoai;

import java.util.ArrayList;

public class PhanLoaiAdapter extends RecyclerView.Adapter<PhanLoaiAdapter.PhanLoaiViewHolder> {
    Context context;
    ArrayList<PhanLoai> list;
    PhanLoaiDAO plDao;
    public PhanLoaiAdapter(Context context, ArrayList<PhanLoai> list){
        this.context = context;
        this.list = list;
        plDao = new PhanLoaiDAO(context);
    }
    @NonNull
    @Override
    public PhanLoaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.phanloai_item,parent,false);
        PhanLoaiViewHolder viewHolder = new PhanLoaiViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhanLoaiViewHolder holder, int position) {
        PhanLoai pl = list.get(position);
        holder.tvTenLoai.setText(pl.getTenLoai());
        holder.tvTrangThai.setText(pl.getTrangThai());
        holder.ivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogUpdate(pl);
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(plDao.delete(pl.getMaLoai())){
                    Toast.makeText(context, "Đã xoá", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(plDao.getAll());
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PhanLoaiViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenLoai,tvTrangThai;
        ImageView ivUpdate,ivDelete;
        CardView cvPhanLoai;
        public PhanLoaiViewHolder(View view){
            super(view);
            tvTenLoai = view.findViewById(R.id.txtTenLoai);
            tvTrangThai = view.findViewById(R.id.txtTrangThai);
            ivUpdate = view.findViewById(R.id.ivUpdate);
            ivDelete = view.findViewById(R.id.ivDelete);
            cvPhanLoai = view.findViewById(R.id.cvPhanLoai);
        }
    }
    private void openDialogUpdate(PhanLoai pl){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.phanloai_update,null);
        builder.setView(view);
        Spinner spinner = view.findViewById(R.id.spnTrangThaiUpdate);
        EditText etTen = view.findViewById(R.id.edtTenLoaiUpdate);
        Button btnSua = view.findViewById(R.id.btnSuaLoai);
        Button btnHuy = view.findViewById(R.id.btnHuyLoaiUpdate);

        Dialog dialog = builder.create();
        dialog.show();

        //Tao du lieu cho spiner
        String[] arrTT = {"Thu","Chi"};
        ArrayAdapter<String> spnAdapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,arrTT);
        spinner.setAdapter(spnAdapter);

        // Gán giá trị lên view

        etTen.setText(pl.getTenLoai());
        for(int i=0; i< arrTT.length; i++){
            if(pl.getTrangThai().equalsIgnoreCase(arrTT[i])){
                spinner.setSelection(i);
            }
        }


        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pl.setTenLoai(etTen.getText().toString());
                pl.setTrangThai((String)spinner.getSelectedItem());

                if(plDao.update(pl)){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    list.clear();
                    list.addAll(plDao.getAll());
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
