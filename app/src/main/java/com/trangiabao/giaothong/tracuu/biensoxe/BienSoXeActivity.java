package com.trangiabao.giaothong.tracuu.biensoxe;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.gioithieu.GioiThieuFragment;
import com.trangiabao.giaothong.tracuu.ViewPagerAdapter;
import com.trangiabao.giaothong.tracuu.xuphat.XuPhatFragment;
import com.trangiabao.giaothong.tracuu.xuphat.db.LoaiViPhamDB;
import com.trangiabao.giaothong.tracuu.xuphat.db.MucXuPhatDB;
import com.trangiabao.giaothong.tracuu.xuphat.db.PhuongTienDB;
import com.trangiabao.giaothong.tracuu.xuphat.model.MucXuPhat;
import com.trangiabao.giaothong.tracuu.xuphat.model.PhuongTien;

public class BienSoXeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bien_so_xe);

        new LoadData().execute();
        addControls();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    }

    private void setupViewPager(ViewPager viewPager) {
        pagerAdapter.addFragment(new GioiThieuFragment(), "Dân sự");
        pagerAdapter.addFragment(new GioiThieuFragment(), "Quân đội");
        pagerAdapter.addFragment(new GioiThieuFragment(), "Biển 80");
        pagerAdapter.addFragment(new GioiThieuFragment(), "Nước ngoài");
        pagerAdapter.addFragment(new GioiThieuFragment(), "Đặc biệt");
        viewPager.setAdapter(pagerAdapter);
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
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
