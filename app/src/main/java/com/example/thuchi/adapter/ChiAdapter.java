package com.example.thuchi.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.thuchi.fragment.KhoanChiFragment;
import com.example.thuchi.fragment.KhoanThuFragment;
import com.example.thuchi.fragment.LoaiChiFragment;
import com.example.thuchi.fragment.LoaiThuFragment;

public class ChiAdapter extends FragmentStateAdapter {
    public ChiAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0)
            return new LoaiChiFragment();
        return new KhoanChiFragment();

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
