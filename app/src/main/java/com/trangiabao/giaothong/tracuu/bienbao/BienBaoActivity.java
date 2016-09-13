package com.trangiabao.giaothong.tracuu.bienbao;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.trangiabao.giaothong.ViewPagerTransformer;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.ViewPagerAdapter;
import com.trangiabao.giaothong.tracuu.bienbao.db.BienBaoDB;
import com.trangiabao.giaothong.tracuu.bienbao.db.NhomBienBaoDB;
import com.trangiabao.giaothong.tracuu.bienbao.model.BienBao;
import com.trangiabao.giaothong.tracuu.bienbao.model.NhomBienBao;

import java.util.List;

public class BienBaoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bien_bao);

        new LoadData().execute();
        addControls();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tra cứu biển báo");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    }

    public class LoadData extends AsyncTask<Void, Void, Void> {

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
            List<NhomBienBao> lstNhomBienBao = new NhomBienBaoDB(BienBaoActivity.this).getAll();
            for (NhomBienBao nhomBienBao : lstNhomBienBao) {
                List<BienBao> lstBienBao = new BienBaoDB(BienBaoActivity.this).getByNhom(nhomBienBao.getId());
                pagerAdapter.addFragment(new BienBaoFragment(lstBienBao, nhomBienBao), nhomBienBao.getTenNhomBienBao());
            }
            return null;
        }
    }
}
