package com.trangiabao.giaothong.tracuu.biensoxe;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.trangiabao.giaothong.ex.ViewPagerTransformer;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.ex.ViewPagerAdapter;
import com.trangiabao.giaothong.tracuu.biensoxe.db.KiHieuDB;
import com.trangiabao.giaothong.tracuu.biensoxe.db.NhomBienSoXeDB;
import com.trangiabao.giaothong.tracuu.biensoxe.model.KiHieu;
import com.trangiabao.giaothong.tracuu.biensoxe.model.NhomBienSoXe;

import java.util.List;

public class BienSoXeActivity extends AppCompatActivity {

    private Context context = BienSoXeActivity.this;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private List<NhomBienSoXe> lstNhomBienSoXe;
    private List<KiHieu> lstKiHieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bien_so_xe);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (android.R.id.home == id) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    class LoadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            viewPager.setAdapter(pagerAdapter);
            viewPager.setPageTransformer(true, new ViewPagerTransformer());
            tabLayout.setupWithViewPager(viewPager);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            lstNhomBienSoXe = new NhomBienSoXeDB(context).getAll();
            for (NhomBienSoXe nhomBienSoXe : lstNhomBienSoXe) {
                lstKiHieu = new KiHieuDB(context).getByIdNhomBienSoXe(nhomBienSoXe.getId() + "");
                pagerAdapter.addFragment(new BienSoXeFragment(lstKiHieu, nhomBienSoXe), nhomBienSoXe.getTen());
            }
            return null;
        }
    }
}
