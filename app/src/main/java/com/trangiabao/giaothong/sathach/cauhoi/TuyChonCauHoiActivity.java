package com.trangiabao.giaothong.sathach.cauhoi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.sathach.cauhoi.db.LoaiBangDB;
import com.trangiabao.giaothong.sathach.cauhoi.db.NhomCauHoiDB;
import com.trangiabao.giaothong.sathach.cauhoi.db.QuyTacRaDeDB;
import com.trangiabao.giaothong.sathach.cauhoi.model.LoaiBang;
import com.trangiabao.giaothong.sathach.cauhoi.model.NhomCauHoi;
import com.trangiabao.giaothong.sathach.cauhoi.model.QuyTacRaDe;

import java.util.ArrayList;
import java.util.List;

public class TuyChonCauHoiActivity extends AppCompatActivity {

    private Context context = TuyChonCauHoiActivity.this;

    private MaterialSpinner spinnerHang;
    private TextView txtMoTa;
    private Button btnStart;
    private LinearLayout layoutNhomCauHoi;
    private CheckBox chkTatCa;
    private List<CheckBox> lstCheckBoxNhomCauHoi;
    private RadioButton radNgauNhien;
    private AdView adView;

    //data
    private List<LoaiBang> lstLoaiBang;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuy_chon_cau_hoi);

        lstLoaiBang = new LoaiBangDB(context).getAll();
        addControls();
        addEvents();
    }

    private void addControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tùy chọn câu hỏi");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerHang = (MaterialSpinner) findViewById(R.id.spinnerHang);
        spinnerHang.setItems(new LoaiBangDB(context).getTenBang(lstLoaiBang));

        layoutNhomCauHoi = (LinearLayout) findViewById(R.id.layoutNhomCauHoi);
        txtMoTa = (TextView) findViewById(R.id.txtMoTa);
        chkTatCa = (CheckBox) findViewById(R.id.chkTatCa);
        btnStart = (Button) findViewById(R.id.btnStart);
        radNgauNhien = (RadioButton) findViewById(R.id.radNgauNhien);

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

        chkTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (CheckBox chk : lstCheckBoxNhomCauHoi) {
                    chk.setChecked(chkTatCa.isChecked());
                }
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = createStringQuery();
                if (query != null) {
                    Intent intent = new Intent(context, CauHoiActivity.class);
                    intent.putExtra("QUERY", query);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "Vui lòng chọn nhóm câu hỏi ít nhât một mục", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String createStringQuery() {
        String query = null;
        if (count != 0) {
            query = "select * from CauHoi ";
            boolean full = false;
            int loaiBang = spinnerHang.getSelectedIndex();
            if (loaiBang == 0)
                query += "where A1 = 1 ";
            else if (loaiBang == 1)
                query += "where A2 = 1 ";
            else if (loaiBang == 2 || loaiBang == 3)
                query += "where A3A4 = 1 ";
            else if (loaiBang == 4)
                query += "where B1 = 1 ";
            else {
                query = "select * from CauHoi ";
                full = true;
            }
            if (count > 0 && count < lstCheckBoxNhomCauHoi.size()) {
                if (full) {
                    query += "where (";
                } else
                    query += "and (";

                for (int i = 0; i < lstCheckBoxNhomCauHoi.size(); i++) {
                    if (lstCheckBoxNhomCauHoi.get(i).isChecked()) {
                        query += "IdNhomCauHoi = " + lstCheckBoxNhomCauHoi.get(i).getHint() + " or ";
                    }
                }
                query = query.substring(0, query.length() - 4) + ")";
            }
            if (radNgauNhien.isChecked())
                query += " order by random()";
        }
        return query;
    }

    private void selectSpinnerItem(int i) {
        chkTatCa.setChecked(false);
        LoaiBang loaiBang = lstLoaiBang.get(i);
        txtMoTa.setText(Html.fromHtml(loaiBang.getMoTa()));
        List<QuyTacRaDe> lstQuyTacRaDe = new QuyTacRaDeDB(context).getByIdLoaiBang(loaiBang.getId() + "");

        // create List Nhom Cau Hoi
        List<NhomCauHoi> lstNhomCauHoi = new ArrayList<>();
        for (QuyTacRaDe quyTacRaDe : lstQuyTacRaDe) {
            NhomCauHoi nhomCauHoi = new NhomCauHoiDB(context).getItemById(quyTacRaDe.getIdNhomCauHoi() + "");
            lstNhomCauHoi.add(nhomCauHoi);
        }
        // create Layout CheckBox
        layoutNhomCauHoi.removeAllViews();
        lstCheckBoxNhomCauHoi = new ArrayList<>();
        for (NhomCauHoi nhomCauHoi : lstNhomCauHoi) {
            AppCompatCheckBox chk = new AppCompatCheckBox(context);
            chk.setText(nhomCauHoi.getTenNhom());
            chk.setHint(nhomCauHoi.getId() + "");
            chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked) {
                        chkTatCa.setChecked(false);
                        count--;
                    } else count++;
                    if (count == lstCheckBoxNhomCauHoi.size())
                        chkTatCa.setChecked(true);
                }
            });
            lstCheckBoxNhomCauHoi.add(chk);
            layoutNhomCauHoi.addView(chk);
        }
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
