package com.trangiabao.giaothong.tracuu.hotline;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.ex.ViewPagerAdapter;
import com.trangiabao.giaothong.ex.ViewPagerTransformer;
import com.trangiabao.giaothong.tracuu.hotline.db.HotLineDB;
import com.trangiabao.giaothong.tracuu.hotline.db.NhomHotLineDB;
import com.trangiabao.giaothong.tracuu.hotline.model.HotLine;
import com.trangiabao.giaothong.tracuu.hotline.model.NhomHotLine;

import java.util.List;

public class HotLineActivity extends AppCompatActivity {

    private Context context = HotLineActivity.this;

    // controls
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_line);

        new LoadData().execute();
        addControls();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setPageTransformer(true, new ViewPagerTransformer());
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

    public class LoadData extends AsyncTask<Void, Void, Void> {

        private List<NhomHotLine> lstNhomHotLine;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            viewPager.setAdapter(pagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }

        @Override
        protected Void doInBackground(Void... aVoid) {
            lstNhomHotLine = new NhomHotLineDB(context).getAll();
            for (NhomHotLine nhomHotLine : lstNhomHotLine) {
                List<HotLine> lstHotLine = new HotLineDB(context).getByIdNhomHotline(nhomHotLine.getId() + "");
                pagerAdapter.addFragment(new HotLineFragment(lstHotLine, nhomHotLine), nhomHotLine.getNhom());
            }
            return null;
        }
    }
}
