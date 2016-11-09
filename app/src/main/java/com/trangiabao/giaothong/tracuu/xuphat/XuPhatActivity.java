package com.trangiabao.giaothong.tracuu.xuphat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.ex.ViewPagerTransformer;
import com.trangiabao.giaothong.ex.ViewPagerAdapter;
import com.trangiabao.giaothong.tracuu.xuphat.db.LoaiViPhamDB;
import com.trangiabao.giaothong.tracuu.xuphat.db.MucXuPhatDB;
import com.trangiabao.giaothong.tracuu.xuphat.db.PhuongTienDB;
import com.trangiabao.giaothong.tracuu.xuphat.model.LoaiViPham;
import com.trangiabao.giaothong.tracuu.xuphat.model.MucXuPhat;
import com.trangiabao.giaothong.tracuu.xuphat.model.PhuongTien;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.ArrayList;
import java.util.List;

public class XuPhatActivity extends AppCompatActivity {

    private Context context = XuPhatActivity.this;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private MaterialSearchView searchView;
    private LinearLayout layout_viewPager;
    private RecyclerView recyclerView;
    private FastItemAdapter<MucXuPhat> adapter;
    private AdView adView;
    private SearchTask searchTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xu_phat);

        new LoadData().execute();
        addControls();
        addEvents();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tra cứu các mức phạt");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setPageTransformer(true, new ViewPagerTransformer());
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        searchView = (MaterialSearchView) findViewById(R.id.searchView);
        layout_viewPager = (LinearLayout) findViewById(R.id.layout_viewPager);

        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(context.getString(R.string.test_device_id))
                .build();
        adView.loadAd(adRequest);
    }

    private void addEvents() {
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                adView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int error) {
                adView.setVisibility(View.GONE);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() >= 3) {
                    searchTask = new SearchTask();
                    searchTask.execute(newText);
                }
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                layout_viewPager.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
                layout_viewPager.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (adView != null) {
            adView.pause();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        searchView.setHint("Tìm kiếm có dấu hoặc không dấu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    class SearchTask extends AsyncTask<String, Void, List<MucXuPhat>> {

        @Override
        protected void onPostExecute(List<MucXuPhat> lstMucXuPhat) {
            super.onPostExecute(lstMucXuPhat);
            adapter.clear();
            adapter.add(lstMucXuPhat);
            adapter.notifyAdapterDataSetChanged();
        }

        @Override
        protected List<MucXuPhat> doInBackground(String... params) {
            return new MucXuPhatDB(context).filter("", params[0]);
        }
    }

    class LoadData extends AsyncTask<Void, Void, Void> {

        private List<PhuongTien> lstPhuongTien = new ArrayList<>();
        private List<LoaiViPham> lstLoaiViPham;
        private List<List<MucXuPhat>> lstMucXuPhat;
        private MaterialDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Drawable icon = MaterialDrawableBuilder.with(context)
                    .setIcon(MaterialDrawableBuilder.IconValue.DOWNLOAD)
                    .setColor(Color.parseColor("#1976D2"))
                    .build();

            dialog = new MaterialDialog.Builder(context)
                    .title("Đang tải dữ liệu...")
                    .progress(true, 0)
                    .icon(icon)
                    .autoDismiss(false).cancelable(false).canceledOnTouchOutside(false)
                    .show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            viewPager.setAdapter(pagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
            for (int i = 0; i < lstPhuongTien.size(); i++) {
                tabLayout.getTabAt(i).setIcon(lstPhuongTien.get(i).getIcon());
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.lstPhuongTien = new PhuongTienDB(context).getAll();
            List<LoaiViPham> lstLoaiViPham = new LoaiViPhamDB(context).getAll();
            for (int i = 0; i < lstPhuongTien.size(); i++) {
                PhuongTien phuongTien = lstPhuongTien.get(i);
                this.lstLoaiViPham = new ArrayList<>();
                this.lstMucXuPhat = new ArrayList<>();
                for (int j = 0; j < lstLoaiViPham.size(); j++) {
                    List<MucXuPhat> lstTemp = new MucXuPhatDB(context).getList(i + 1, j + 1);
                    if (lstTemp.size() != 0) {
                        this.lstLoaiViPham.add(lstLoaiViPham.get(j));
                        this.lstMucXuPhat.add(lstTemp);
                    }
                }
                pagerAdapter.addFragment(new XuPhatFragment(this.lstLoaiViPham, this.lstMucXuPhat), phuongTien.getPhuongTien());
            }
            return null;
        }
    }
}