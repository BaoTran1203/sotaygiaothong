package com.trangiabao.giaothong.sathach.cauhoi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.database.LoaiBangDB;
import com.trangiabao.giaothong.database.NhomCauHoiDB;
import com.trangiabao.giaothong.database.QuyTacRaDeDB;
import com.trangiabao.giaothong.model.LoaiBang;
import com.trangiabao.giaothong.model.NhomCauHoi;
import com.trangiabao.giaothong.model.QuyTacRaDe;

import java.util.ArrayList;
import java.util.List;

public class TuyChonCauHoiActivity extends AppCompatActivity {

    // controls
    private Toolbar toolbar;
    private MaterialSpinner spinnerHang;
    private TextView txtMoTa;
    private Button btnStart;
    private LinearLayout layoutNhomCauHoi;
    private CheckBox chkTatCa;
    private List<CheckBox> lstCheckBoxNhomCauHoi;
    private RadioButton radNgauNhien;

    //data
    private LoaiBangDB loaiBangDB;
    private ArrayList<LoaiBang> lstLoaiBang;
    private NhomCauHoiDB nhomCauHoiDB;
    private ArrayList<NhomCauHoi> lstNhomCauHoi;
    private QuyTacRaDeDB quyTacRaDeDB;
    private ArrayList<QuyTacRaDe> lstQuyTacRaDe;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuy_chon_cau_hoi);

        loadDatas();
        addControls();
        addEvents();
    }

    private void loadDatas() {
        loaiBangDB = new LoaiBangDB(this);
        lstLoaiBang = loaiBangDB.getAll();
        nhomCauHoiDB = new NhomCauHoiDB(this);
        quyTacRaDeDB = new QuyTacRaDeDB(this);
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinnerHang = (MaterialSpinner) findViewById(R.id.spinnerHang);
        spinnerHang.setItems(loaiBangDB.getTenBang(lstLoaiBang));
        layoutNhomCauHoi = (LinearLayout) findViewById(R.id.layoutNhomCauHoi);
        txtMoTa = (TextView) findViewById(R.id.txtMoTa);
        chkTatCa = (CheckBox) findViewById(R.id.chkTatCa);
        btnStart = (Button) findViewById(R.id.btnStart);
        radNgauNhien = (RadioButton) findViewById(R.id.radNgauNhien);

        selectSpinnerItem(0);
    }

    private void addEvents() {
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
                    Intent intent = new Intent(TuyChonCauHoiActivity.this, CauHoiActivity.class);
                    intent.putExtra("QUERY", query);
                    startActivity(intent);
                } else {
                    Toast.makeText(TuyChonCauHoiActivity.this, "Vui lòng chọn nhóm câu hỏi ít nhât một mục", Toast.LENGTH_SHORT).show();
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
                query = query.substring(0,query.length()-4) + ")";
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
        lstQuyTacRaDe = quyTacRaDeDB.getByIdLoaiBang(loaiBang.getId());
        // create List Nhom Cau Hoi
        lstNhomCauHoi = new ArrayList<>();
        for (QuyTacRaDe quyTacRaDe : lstQuyTacRaDe) {
            NhomCauHoi nhomCauHoi = nhomCauHoiDB.getItemById(quyTacRaDe.getIdNhomCauHoi());
            lstNhomCauHoi.add(nhomCauHoi);
        }
        // create Layout CheckBox
        layoutNhomCauHoi.removeAllViews();
        lstCheckBoxNhomCauHoi = new ArrayList<>();
        for (NhomCauHoi nhomCauHoi : lstNhomCauHoi) {
            AppCompatCheckBox chk = new AppCompatCheckBox(TuyChonCauHoiActivity.this);
            chk.setText(nhomCauHoi.getTenNhom());
            chk.setHint(nhomCauHoi.getId() + "");
            lstCheckBoxNhomCauHoi.add(chk);
            layoutNhomCauHoi.addView(chk);
        }
        // check List CheckBox
        new Thread(new Runnable() {
            public void run() {
                for (final CheckBox chk : lstCheckBoxNhomCauHoi) {
                    chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                            if (!isChecked) {
                                chkTatCa.setChecked(false);
                                count--;
                            } else count++;
                            if (count == lstCheckBoxNhomCauHoi.size())
                                chkTatCa.setChecked(true);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
