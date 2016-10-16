package com.trangiabao.giaothong.sathach.cauhoi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.sathach.cauhoi.db.LoaiBangDB;
import com.trangiabao.giaothong.sathach.cauhoi.model.LoaiBang;

import java.util.List;

public class TuyChonBaiThiActivity extends AppCompatActivity {

    private Context context = TuyChonBaiThiActivity.this;

    // controls
    private Toolbar toolbar;
    private MaterialSpinner spinnerHang;
    private TextView txtQuyTac, txtMoTa;
    private Button btnStart;
    private AdView adView;

    //data
    private List<LoaiBang> lstLoaiBang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuy_chon_bai_thi);

        lstLoaiBang = new LoaiBangDB(context).getAll();
        addControls();
        addEvents();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tùy chọn bài thi");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerHang = (MaterialSpinner) findViewById(R.id.spinnerHang);
        spinnerHang.setItems(new LoaiBangDB(context).getTenBang(lstLoaiBang));

        txtQuyTac = (TextView) findViewById(R.id.txtQuyTac);
        txtMoTa = (TextView) findViewById(R.id.txtMoTa);
        btnStart = (Button) findViewById(R.id.btnStart);

        selectSpinnerItem(0);

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

        spinnerHang.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                selectSpinnerItem(position);
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idLoaiBang = spinnerHang.getSelectedIndex() + 1;
                Intent intent = new Intent(context, BaiThiActivity.class);
                intent.putExtra("IDLoaiBang", idLoaiBang + "");
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
