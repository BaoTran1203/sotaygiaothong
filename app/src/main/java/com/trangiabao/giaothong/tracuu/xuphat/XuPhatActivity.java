package com.trangiabao.giaothong.tracuu.xuphat;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.trangiabao.giaothong.R;

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
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
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
            PhuongTien phuongTien = lstPhuongTien.get(i);
            int resID = getResources().getIdentifier(phuongTien.getIconName(), "drawable", getPackageName());
            tabLayout.getTabAt(i).setIcon(resID);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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