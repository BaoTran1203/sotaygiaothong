package com.trangiabao.giaothong.sathach.lambaithi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.sathach.db.LoaiBangDB;
import com.trangiabao.giaothong.sathach.model.LoaiBang;

import java.util.ArrayList;

public class TuyChonBaiThiActivity extends AppCompatActivity {

    // controls
    private Toolbar toolbar;
    private MaterialSpinner spinnerHang;
    private TextView txtQuyTac, txtMoTa;
    private Button btnStart;

    //data
    private LoaiBangDB loaiBangDB;
    private ArrayList<LoaiBang> lstLoaiBang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuy_chon_bai_thi);

        loadDatas();
        addControls();
        addEvents();
    }

    private void loadDatas() {
        loaiBangDB = new LoaiBangDB(this);
        lstLoaiBang = loaiBangDB.getAll();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tùy chọn bài thi");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinnerHang = (MaterialSpinner) findViewById(R.id.spinnerHang);
        spinnerHang.setItems(loaiBangDB.getTenBang(lstLoaiBang));
        txtQuyTac = (TextView) findViewById(R.id.txtQuyTac);
        txtMoTa = (TextView) findViewById(R.id.txtMoTa);
        btnStart = (Button) findViewById(R.id.btnStart);

        selectSpinnerItem(0);
    }

    private void addEvents() {
        spinnerHang.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                selectSpinnerItem(position);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TuyChonBaiThiActivity.this, BaiThiActivity.class);
                intent.putExtra("IDLoaiBang", spinnerHang.getSelectedIndex() + 1 + "");
                startActivity(intent);
            }
        });
    }

    private void selectSpinnerItem(int i) {
        LoaiBang loaiBang = lstLoaiBang.get(i);
        txtMoTa.setText(Html.fromHtml(loaiBang.getMoTa()));
        txtQuyTac.setText(Html.fromHtml(loaiBang.getQuyTac()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
