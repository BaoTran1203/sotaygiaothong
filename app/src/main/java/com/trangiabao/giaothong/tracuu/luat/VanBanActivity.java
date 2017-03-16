package com.trangiabao.giaothong.tracuu.luat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.luat.db.ChuongDB;
import com.trangiabao.giaothong.tracuu.luat.db.VanBanDB;
import com.trangiabao.giaothong.tracuu.luat.model.Chuong;
import com.trangiabao.giaothong.tracuu.luat.model.VanBan;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.List;

public class VanBanActivity extends AppCompatActivity {

    // controls
    private Context context = VanBanActivity.this;
    private FastItemAdapter<VanBan> adapter;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_ban);

        new LoadDataTask().execute();
        addControls();
        addEvents();
    }

    private void addControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Văn bản");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
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

        adapter.withOnClickListener(new FastAdapter.OnClickListener<VanBan>() {
            @Override
            public boolean onClick(View v, IAdapter<VanBan> adapter, VanBan item, int position) {
                List<Chuong> lstChuong = new ChuongDB(context).getByIdVanBan(item.getId() + "");
                Class aClass;
                String id;
                if (lstChuong.size() == 1) {
                    aClass = NoiDungActivity.class;
                    id = lstChuong.get(0).getId() + "";
                } else {
                    aClass = ChuongActivity.class;
                    id = item.getId() + "";
                }
                Intent intent = new Intent(context, aClass);
                intent.putExtra("id", id);
                intent.putExtra("ten", item.getTenVietTat());
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (android.R.id.home == id) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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

    class LoadDataTask extends AsyncTask<Void, Void, Void> {

        private MaterialDialog dialog;
        private List<VanBan> lst;

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
            adapter.add(lst);
            dialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            lst = new VanBanDB(context).getAll();
            return null;
        }
    }
}
