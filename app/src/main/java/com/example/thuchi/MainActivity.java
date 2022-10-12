package com.example.thuchi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.thuchi.dao.PhanLoaiDAO;
import com.example.thuchi.fragment.ChiFragment;
import com.example.thuchi.fragment.KhoanThuFragment;
import com.example.thuchi.fragment.LoaiThuFragment;
import com.example.thuchi.fragment.PhanLoaiFragment;
import com.example.thuchi.fragment.ThongKeFragment;
import com.example.thuchi.fragment.ThuFragment;
import com.example.thuchi.model.PhanLoai;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    PhanLoaiDAO phanLoaiDAO;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //========
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment;

                switch (item.getItemId()){
                    case R.id.nav_loai:
                        fragment = new PhanLoaiFragment();
                        break;
                    case R.id.nav_thu:
                        fragment = new ThuFragment();
                        break;
                    case R.id.nav_chi:
                        fragment = new ChiFragment();
                        break;
                    case R.id.nav_thongke:
                        fragment = new ThongKeFragment();
                        break;
                    default:
                        fragment = new PhanLoaiFragment();
                        break;
                }

                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
                drawer.closeDrawer(GravityCompat.START);
                setTitle(item.getTitle());
                return false;
            }
        });
    }
}