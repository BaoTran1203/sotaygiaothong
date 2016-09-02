package com.trangiabao.giaothong.tracuu.luat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.luat.db.VanBanDB;
import com.trangiabao.giaothong.tracuu.luat.model.VanBan;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class VanBanActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FastItemAdapter<VanBan> adapter;
    private RecyclerView rvVanBan;

    private List<VanBan> lstVanBan;
    private VanBan vanBan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_ban);

        loadData();
        addControls(savedInstanceState);
        addEvents();
    }

    private void loadData() {
        lstVanBan = new VanBanDB(VanBanActivity.this).getAll();
    }

    private void addControls(Bundle savedInstanceState) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Văn bản");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvVanBan = (RecyclerView) findViewById(R.id.rvVanBan);
        rvVanBan.setLayoutManager(new LinearLayoutManager(VanBanActivity.this));
        rvVanBan.setHasFixedSize(true);

        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);

        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
        animationAdapter.setDuration(1000);
        animationAdapter.setInterpolator(new OvershootInterpolator());
        animationAdapter.setFirstOnly(false);

        rvVanBan.setAdapter(animationAdapter);
        adapter.add(lstVanBan);
        adapter.withSavedInstanceState(savedInstanceState);
    }

    private void addEvents() {
        adapter.withOnClickListener(new FastAdapter.OnClickListener<VanBan>() {
            @Override
            public boolean onClick(View v, IAdapter<VanBan> adapter, VanBan item, int position) {
                vanBan = lstVanBan.get(position);
                Intent intent = new Intent(VanBanActivity.this, ChuongActivity.class);
                intent.putExtra("ID_VAN_BAN", vanBan.getId() + "");
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState = adapter.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}
