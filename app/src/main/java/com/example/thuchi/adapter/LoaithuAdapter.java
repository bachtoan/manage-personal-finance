package com.example.thuchi.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuchi.R;
import com.example.thuchi.dao.KhoanThuChiDAO;
import com.example.thuchi.model.PhanLoai;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Base64;

public class LoaithuAdapter extends BaseAdapter {
    private ArrayList<PhanLoai> list;
    private Context context;
    private KhoanThuChiDAO khoanThuChiDAO;
    public LoaithuAdapter(ArrayList<PhanLoai> list, Context context, KhoanThuChiDAO khoanThuChiDAO) {
        this.list = list;
        this.context = context;
        this.khoanThuChiDAO = khoanThuChiDAO;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewOfItem{
        TextView txtTen;
        ImageView ivSua,ivXoa;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        ViewOfItem viewOfItem;

        if(convertView == null){
            viewOfItem = new ViewOfItem();
            convertView = inflater.inflate(R.layout.item_loaithu,parent,false);
            viewOfItem.txtTen = convertView.findViewById(R.id.txtTen);
            viewOfItem.ivSua = convertView.findViewById(R.id.icEdit);
            viewOfItem.ivXoa = convertView.findViewById(R.id.icDelete);
            convertView.setTag(viewOfItem);
        }else{
            viewOfItem = (ViewOfItem) convertView.getTag();
        }
        viewOfItem.txtTen.setText(list.get(position).getTenLoai());

        viewOfItem.ivSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSuaLoaiThu(list.get(position));
            }
        });

        viewOfItem.ivXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idCanXoa = list.get(position).getMaLoai();
                if(khoanThuChiDAO.xoaLoaiThuChi(idCanXoa)){
                    Toast.makeText(context, "Xo?? th??nh c??ng", Toast.LENGTH_SHORT).show();
                    reloadData();
                }else{
                    Toast.makeText(context, "Xo?? th???t b???i", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }

    private void reloadData(){
        list.clear();
        list = khoanThuChiDAO.getDSLoai("Thu");
        notifyDataSetChanged();
    }

    private void showDialogSuaLoaiThu(PhanLoai phanLoai){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sualoaithu,null);
        EditText edtInput = view.findViewById(R.id.edtInput);
        builder.setView(view);

        edtInput.setText(phanLoai.getTenLoai());

        builder.setPositiveButton("S???a", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenloai = edtInput.getText().toString();
                phanLoai.setTenLoai(tenloai);
                if(khoanThuChiDAO.capNhatLoaiThuChi(phanLoai)){
                    Toast.makeText(context, "C???p nh???t th??nh c??ng", Toast.LENGTH_SHORT).show();
                    reloadData();
                }else{
                    Toast.makeText(context, "C???p nh???t th???t b???i", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hu???", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
