package com.trangiabao.giaothong.tracuu.luat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.luat.db.ChuongDB;
import com.trangiabao.giaothong.tracuu.luat.model.Chuong;

import java.util.List;

public class ChuongActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FastItemAdapter<Chuong> adapter;
    private RecyclerView rvChuong;

    private List<Chuong> lstChuong;
    private Chuong chuong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuong);

        loadData();
        addControls(savedInstanceState);
        addEvents();
    }

    private void loadData() {
        lstChuong = new ChuongDB(ChuongActivity.this).getByIdVanBan(getIntent().getExtras().getString("ID_VAN_BAN"));
    }

    private void addControls(Bundle savedInstanceState) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Chương");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvChuong = (RecyclerView) findViewById(R.id.rvChuong);
        rvChuong.setLayoutManager(new LinearLayoutManager(ChuongActivity.this));
        rvChuong.setHasFixedSize(true);

        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);

        rvChuong.setAdapter(adapter);
        adapter.add(lstChuong);
        adapter.withSavedInstanceState(savedInstanceState);
    }

    private void addEvents() {
        adapter.withOnClickListener(new FastAdapter.OnClickListener<Chuong>() {
            @Override
            public boolean onClick(View v, IAdapter<Chuong> adapter, Chuong item, int position) {
                chuong = lstChuong.get(position);
                Intent intent = new Intent(ChuongActivity.this, NoiDungActivity.class);
                intent.putExtra("ID_CHUONG", chuong.getId() + "");
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
