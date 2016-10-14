package com.trangiabao.giaothong.tracuu.biensoxe;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
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
    private MaterialSearchView searchView;
    private LinearLayout layout_viewPager;
    private RecyclerView recyclerView;
    private FastItemAdapter<KiHieu> adapter;

    private SearchTask searchTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bien_so_xe);

        new LoadDataTask().execute();
        addControls();
        addEvents();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tra cứu biển số xe");
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
    }

    private void addEvents() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.clear();
                if (newText.length() >= 2) {
                    searchTask = new SearchTask();
                    searchTask.execute(newText);
                }
                adapter.notifyAdapterDataSetChanged();
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

    class SearchTask extends AsyncTask<String, Void, List<KiHieu>> {

        @Override
        protected void onPostExecute(List<KiHieu> lstKiHieu) {
            super.onPostExecute(lstKiHieu);
            adapter.add(lstKiHieu);
        }

        @Override
        protected List<KiHieu> doInBackground(String... params) {
            return new KiHieuDB(context).filter(params[0]);
        }
    }

    class LoadDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            viewPager.setAdapter(pagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<NhomBienSoXe> lstNhomBienSoXe = new NhomBienSoXeDB(context).getAll();
            for (NhomBienSoXe nhomBienSoXe : lstNhomBienSoXe) {
                List<KiHieu> lstKiHieu = new KiHieuDB(context).getByIdNhomBienSoXe(nhomBienSoXe.getId() + "");
                pagerAdapter.addFragment(new BienSoXeFragment(lstKiHieu, nhomBienSoXe), nhomBienSoXe.getTen());
            }
            return null;
        }
    }
}
