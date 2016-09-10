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
import com.trangiabao.giaothong.tracuu.biensoxe.db.NuocNgoaiDB;
import com.trangiabao.giaothong.tracuu.biensoxe.db.TinhDB;
import com.trangiabao.giaothong.tracuu.biensoxe.fragment.Bien80Fragment;
import com.trangiabao.giaothong.tracuu.biensoxe.fragment.BoQuocPhongFragment;
import com.trangiabao.giaothong.tracuu.biensoxe.fragment.DanSuFragment;
import com.trangiabao.giaothong.tracuu.biensoxe.fragment.NuocNgoaiFragment;
import com.trangiabao.giaothong.tracuu.biensoxe.model.NuocNgoai;
import com.trangiabao.giaothong.tracuu.biensoxe.model.Tinh;
import com.trangiabao.giaothong.tracuu.xuphat.XuPhatFragment;
import com.trangiabao.giaothong.tracuu.xuphat.db.LoaiViPhamDB;
import com.trangiabao.giaothong.tracuu.xuphat.db.MucXuPhatDB;
import com.trangiabao.giaothong.tracuu.xuphat.db.PhuongTienDB;
import com.trangiabao.giaothong.tracuu.xuphat.model.MucXuPhat;
import com.trangiabao.giaothong.tracuu.xuphat.model.PhuongTien;

import java.util.List;

public class BienSoXeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private List<Tinh> lstTinh;
    private List<NuocNgoai> lstNuocNgoai;

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

    class LoadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pagerAdapter.addFragment(new DanSuFragment(lstTinh), "Dân sự");
            pagerAdapter.addFragment(new BoQuocPhongFragment(), "Quân đội");
            pagerAdapter.addFragment(new Bien80Fragment(), "Biển 80");
            pagerAdapter.addFragment(new NuocNgoaiFragment(lstNuocNgoai), "Nước ngoài");
            viewPager.setAdapter(pagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            lstTinh = new TinhDB(BienSoXeActivity.this).getAll();
            lstNuocNgoai = new NuocNgoaiDB(BienSoXeActivity.this).getAll();
            return null;
        }
    }
}
