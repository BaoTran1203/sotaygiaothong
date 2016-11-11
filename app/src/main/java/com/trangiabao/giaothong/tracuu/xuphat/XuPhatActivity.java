package com.trangiabao.giaothong.tracuu.xuphat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.fastadapter.IItemAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.ex.MyMethod;
import com.trangiabao.giaothong.tracuu.xuphat.db.LoaiViPhamDB;
import com.trangiabao.giaothong.tracuu.xuphat.db.MucXuPhatDB;
import com.trangiabao.giaothong.tracuu.xuphat.model.LoaiViPham;
import com.trangiabao.giaothong.tracuu.xuphat.model.MucXuPhat;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.ArrayList;
import java.util.List;

public class XuPhatActivity extends AppCompatActivity {

    private Context context = XuPhatActivity.this;
    private Toolbar toolbar;
    private MaterialSearchView searchView;
    private RecyclerView recyclerView;
    private FastItemAdapter<MucXuPhat> adapter;
    private AdView adView;
    private MaterialSpinner spinner;

    private String id;
    private List<List<MucXuPhat>> lst;

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
        toolbar.setTitle("Các mức phạt: " + getIntent().getExtras().getString("ten"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchView = (MaterialSearchView) findViewById(R.id.searchView);
        spinner = (MaterialSpinner) findViewById(R.id.spinner);

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

        adapter.withFilterPredicate(new IItemAdapter.Predicate<MucXuPhat>() {
            @Override
            public boolean filter(MucXuPhat item, CharSequence value) {
                String filter = MyMethod.unAccent(String.valueOf(value)).toLowerCase();

                String doiTuong = MyMethod.unAccent(item.getDoiTuong()).toLowerCase();
                String hanhVi = MyMethod.unAccent(item.getHanhVi()).toLowerCase();
                String mucPhat = MyMethod.unAccent(item.getMucPhat()).toLowerCase();
                String boSung = MyMethod.unAccent(item.getBoSung()).toLowerCase();
                String khacPhuc = MyMethod.unAccent(item.getKhacPhuc()).toLowerCase();

                return !(hanhVi.contains(filter) ||
                        mucPhat.contains(filter) ||
                        boSung.contains(filter) ||
                        doiTuong.contains(filter) ||
                        khacPhuc.contains(filter));
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

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                adapter.clear();
                adapter.add(lst.get(position));
                adapter.notifyAdapterDataSetChanged();
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

    class LoadData extends AsyncTask<Void, Void, Void> {

        private MaterialDialog dialog;
        private List<String> lstSpinner = new ArrayList<>();

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
            spinner.setItems(lstSpinner);
            adapter.add(lst.get(0));
            dialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            lst = new ArrayList<>();
            List<LoaiViPham> lstLoaiViPham = new LoaiViPhamDB(context).getAll();
            for (int i = 0; i < lstLoaiViPham.size(); i++) {
                LoaiViPham loaiViPham = lstLoaiViPham.get(i);
                List<MucXuPhat> lstMucXuPhat = new MucXuPhatDB(context).getList(id, loaiViPham.getId() + "");
                if (lstMucXuPhat.size() > 0) {
                    lst.add(lstMucXuPhat);
                    lstSpinner.add(loaiViPham.getLoai());
                }
            }
            return null;
        }
    }
}