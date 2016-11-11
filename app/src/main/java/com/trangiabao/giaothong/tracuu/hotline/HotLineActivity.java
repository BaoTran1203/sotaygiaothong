package com.trangiabao.giaothong.tracuu.hotline;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.fastadapter.IItemAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.ex.MyMethod;
import com.trangiabao.giaothong.tracuu.hotline.db.HotLineDB;
import com.trangiabao.giaothong.tracuu.hotline.model.HotLine;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.List;

public class HotLineActivity extends AppCompatActivity {

    private Context context = HotLineActivity.this;
    private Toolbar toolbar;
    private MaterialSearchView searchView;
    private RecyclerView recyclerView;
    private FastItemAdapter<HotLine> adapter;
    private AdView adView;

    private String id;
    private List<HotLine> lst;

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
        toolbar.setTitle(getIntent().getExtras().getString("ten"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchView = (MaterialSearchView) findViewById(R.id.searchView);

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

        adapter.withFilterPredicate(new IItemAdapter.Predicate<HotLine>() {
            @Override
            public boolean filter(HotLine item, CharSequence value) {
                String filter = MyMethod.unAccent(String.valueOf(value)).toLowerCase();
                String ten = MyMethod.unAccent(item.getTen()).toLowerCase();
                String phone = MyMethod.unAccent(item.getPhone()).toLowerCase().replace(".", "").replace(" ", "");
                return !(ten.contains(filter) || phone.contains(filter));
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                adView.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
                adView.setVisibility(View.VISIBLE);
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

    public class LoadData extends AsyncTask<Void, Void, Void> {

        private MaterialDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            id = getIntent().getExtras().getString("id");

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
            adapter.add(lst);
            dialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... aVoid) {
            lst = new HotLineDB(context).getByIdNhomHotline(id);
            return null;
        }
    }
}
