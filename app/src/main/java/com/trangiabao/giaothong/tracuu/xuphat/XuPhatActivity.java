package com.trangiabao.giaothong.tracuu.xuphat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.ViewPagerTransformer;
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

    private void setupIcon() {

    }

    class LoadData extends AsyncTask<Void, Void, List<PhuongTien>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<PhuongTien> lstPhuongTien) {
            super.onPostExecute(lstPhuongTien);
            viewPager.setAdapter(pagerAdapter);
            viewPager.setPageTransformer(true, new ViewPagerTransformer());
            tabLayout.setupWithViewPager(viewPager);
            for (int i = 0; i < lstPhuongTien.size(); i++) {
                tabLayout.getTabAt(i).setIcon(lstPhuongTien.get(i).getIcon());
            }
        }

        @Override
        protected List<PhuongTien> doInBackground(Void... voids) {
            List<PhuongTien> lstPhuongTien = new PhuongTienDB(XuPhatActivity.this).getAll();
            List<LoaiViPham> lstLoaiViPham = new LoaiViPhamDB(XuPhatActivity.this).getAll();
            for (int i = 0; i < lstPhuongTien.size(); i++) {
                PhuongTien phuongTien = lstPhuongTien.get(i);
                List<List<MucXuPhat>> lstMucXuPhat = new ArrayList<>();
                for (int j = 0; j < lstLoaiViPham.size(); j++) {
                    List<MucXuPhat> lstTemp = new MucXuPhatDB(XuPhatActivity.this).getList(i + 1, j + 1);
                    lstMucXuPhat.add(lstTemp);
                }
                pagerAdapter.addFragment(new XuPhatFragment(lstMucXuPhat), phuongTien.getVietTat());
            }
            return lstPhuongTien;
        }
    }
}