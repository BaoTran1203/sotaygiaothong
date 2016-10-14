package com.trangiabao.giaothong.tracuu.hotline;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
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
    private MaterialSearchView searchView;
    private LinearLayout layout_viewPager;
    private RecyclerView recyclerView;
    private FastItemAdapter<HotLine> adapter;

    private SearchTask searchTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_line);

        new LoadData().execute();
        addControls();
        addEvents();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Đường dây nóng");
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
                if (newText.length() > 0) {
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

    class SearchTask extends AsyncTask<String, Void, List<HotLine>> {

        @Override
        protected void onPostExecute(List<HotLine> lstHotLine) {
            super.onPostExecute(lstHotLine);
            adapter.add(lstHotLine);
        }

        @Override
        protected List<HotLine> doInBackground(String... params) {
            return new HotLineDB(context).filter(params[0]);
        }
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
