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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuchi.R;
import com.example.thuchi.dao.KhoanThuChiDAO;
import com.example.thuchi.model.KhoanThuChi;

import java.util.ArrayList;
import java.util.HashMap;

public class KhoanChiAdapter extends BaseAdapter {
    private ArrayList<KhoanThuChi> list;
    private Context context;
    private KhoanThuChiDAO khoanThuChiDAO;
    private ArrayList<HashMap<String, Object>> listSpiner;

    public KhoanChiAdapter(ArrayList<KhoanThuChi> list, Context context, KhoanThuChiDAO khoanThuChiDAO, ArrayList<HashMap<String, Object>> listSpiner ) {
        this.list = list;
        this.context = context;
        this.khoanThuChiDAO = khoanThuChiDAO;
        this.listSpiner = listSpiner;
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
        TextView txtTen,txtTien;
        ImageView ivSua, ivXoa;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        ViewOfItem viewOfItem;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_khoanthu,parent,false);
            viewOfItem = new ViewOfItem();
            viewOfItem.txtTen = convertView.findViewById(R.id.txtTen);
            viewOfItem.txtTien = convertView.findViewById(R.id.txtTien);
            viewOfItem.ivSua = convertView.findViewById(R.id.icEdit);
            viewOfItem.ivXoa = convertView.findViewById(R.id.icDelete);
            convertView.setTag(viewOfItem);
        }else{
            viewOfItem = (ViewOfItem) convertView.getTag();
        }

        viewOfItem.txtTen.setText(list.get(position).getTenLoai());
        viewOfItem.txtTien.setText(list.get(position).getTien()+"");

        viewOfItem.ivSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSua(list.get(position));
            }
        });

        viewOfItem.ivXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int makhoan = list.get(position).getMakhoan();
                if(khoanThuChiDAO.xoaLoaiThuChi(makhoan)){
                    Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                    reloadData();
                }else{
                    Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }
    private void showDialogSua(KhoanThuChi khoanThuChi){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_suakhoanthu,null);
        Spinner spnLoaithu = view.findViewById(R.id.spnLoaithu);
        EditText edtTien = view.findViewById(R.id.edtTien);
        builder.setView(view);

        SimpleAdapter adapter = new SimpleAdapter(
                context,
                listSpiner,
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1 }
        );
        spnLoaithu.setAdapter(adapter);
        edtTien.setText(String.valueOf(khoanThuChi.getTien()));

        int index = 0;
        int vitri = -1;
        for(HashMap<String, Object> item : listSpiner){
            if((int)item.get("maloai") == khoanThuChi.getMaloai())
                vitri = index;
            index++;
        }
        spnLoaithu.setSelection(vitri);

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tien = edtTien.getText().toString();
                HashMap<String, Object> selected = (HashMap<String, Object>) spnLoaithu.getSelectedItem();
                int maloai = (int) selected.get("maloai");
                khoanThuChi.setTien(Integer.parseInt(tien));
                khoanThuChi.setMaloai(maloai);
                if(khoanThuChiDAO.capNhatKhoanThuChi(khoanThuChi)){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    reloadData();
                }else{
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
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
        alertDialog.setTitle("Sửa khoản chi");
        alertDialog.show();
    }
    private void reloadData(){
        list.clear();
        list = khoanThuChiDAO.getDSKhoanThuChi("Chi");
        notifyDataSetChanged();
    }
}
