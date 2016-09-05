package com.trangiabao.giaothong.tracuu.xuphat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.ViewPagerAdapter;
import com.trangiabao.giaothong.tracuu.xuphat.db.LoaiViPhamDB;
import com.trangiabao.giaothong.tracuu.xuphat.db.MucXuPhatDB;
import com.trangiabao.giaothong.tracuu.xuphat.db.PhuongTienDB;
import com.trangiabao.giaothong.tracuu.xuphat.model.LoaiViPham;
import com.trangiabao.giaothong.tracuu.xuphat.model.MucXuPhat;
import com.trangiabao.giaothong.tracuu.xuphat.model.PhuongTien;

import java.util.ArrayList;
import java.util.List;

public class XuPhatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;

    private List<PhuongTien> lstPhuongTien;
    private List<List<MucXuPhat>> lstMucXuPhat;
    private List<LoaiViPham> lstLoaiViPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xu_phat);

        new LoadData().execute();
        addControls();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tra cứu biển số xe");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    }

    private void setupViewPager(ViewPager viewPager) {
        for (int i = 0; i < lstPhuongTien.size(); i++) {
            PhuongTien phuongTien = lstPhuongTien.get(i);
            pagerAdapter.addFragment(new XuPhatFragment(lstMucXuPhat), phuongTien.getVietTat());
        }
        viewPager.setAdapter(pagerAdapter);
    }

    private void setupIcon() {
        for (int i = 0; i < lstPhuongTien.size(); i++) {
            tabLayout.getTabAt(i).setIcon(lstPhuongTien.get(i).getIcon());
        }
    }

    class LoadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
            setupIcon();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            lstPhuongTien = new PhuongTienDB(XuPhatActivity.this).getAll();
            lstLoaiViPham = new LoaiViPhamDB(XuPhatActivity.this).getAll();
            lstMucXuPhat = new ArrayList<>();
            for (int i = 0; i < lstPhuongTien.size(); i++) {
                for (int j = 0; j < lstLoaiViPham.size(); j++) {
                    List<MucXuPhat> lstTemp = new MucXuPhatDB(XuPhatActivity.this).getList(i + 1, j + 1);
                    lstMucXuPhat.add(lstTemp);
                }
            }
            return null;
        }
    }
}