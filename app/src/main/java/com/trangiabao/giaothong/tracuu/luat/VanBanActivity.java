package com.trangiabao.giaothong.tracuu.luat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

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

import java.util.List;

public class VanBanActivity extends AppCompatActivity {

    // controls
    private Context context = VanBanActivity.this;
    private Toolbar toolbar;
    private FastItemAdapter<VanBan> adapter;
    private RecyclerView rvVanBan;
    private AdView adView;

    // datas
    private List<VanBan> lstVanBan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_ban);

        lstVanBan = new VanBanDB(context).getAll();
        addControls();
        addEvents();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Văn bản");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);
        adapter.add(lstVanBan);

        rvVanBan = (RecyclerView) findViewById(R.id.rvVanBan);
        rvVanBan.setLayoutManager(new LinearLayoutManager(context));
        rvVanBan.setHasFixedSize(true);
        rvVanBan.setAdapter(adapter);

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
                VanBan vanBan = lstVanBan.get(position);
                List<Chuong> lstChuong = new ChuongDB(context).getByIdVanBan(vanBan.getId() + "");
                Intent intent;
                if (lstChuong.size() == 1) {
                    intent = new Intent(context, NoiDungActivity.class);
                    intent.putExtra("ID_CHUONG", lstChuong.get(0).getId() + "");
                } else {
                    intent = new Intent(context, ChuongActivity.class);
                    intent.putExtra("ID_VAN_BAN", vanBan.getId() + "");
                }
                intent.putExtra("VANBAN", vanBan.getTenVietTat());
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
}
