package com.trangiabao.giaothong.tracuu.bienbao;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.melnykov.fab.FloatingActionButton;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.database.BienBaoDB;
import com.trangiabao.giaothong.database.NhomBienBaoDB;
import com.trangiabao.giaothong.model.BienBao;
import com.trangiabao.giaothong.model.NhomBienBao;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class BienBaoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerViewHeader rvHeader;
    private RecyclerView rvBienBao;
    private TextView txtMoTa;
    private MaterialSpinner spinnerNhomBienBao;
    private FloatingActionButton fab;
    private MaterialDialog dialog;
    private FrameLayout frame_container;

    private NhomBienBaoDB nhomBienBaoDB;
    private List<NhomBienBao> lstNhomBienBao;
    private BienBaoDB bienBaoDB;
    private List<FastItemAdapter<BienBao>> lstAdapter;
    private FastItemAdapter<BienBao> adapter = new FastItemAdapter<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bien_bao);

        loadData();
        addControls();
        addEvents();
    }

    private void loadData() {
        new LoadDataTask().execute();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rvBienBao = (RecyclerView) findViewById(R.id.rvBienBao);
        rvBienBao.setLayoutManager(new LinearLayoutManager(BienBaoActivity.this));
        rvHeader = (RecyclerViewHeader) findViewById(R.id.rvHeader);
        rvHeader.attachTo(rvBienBao);
        txtMoTa = (TextView) findViewById(R.id.txtMoTa);
        spinnerNhomBienBao = (MaterialSpinner) findViewById(R.id.spinnerNhomBienBao);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToRecyclerView(rvBienBao);
        frame_container = (FrameLayout) findViewById(R.id.frame_container);
    }

    private void addEvents() {
        spinnerNhomBienBao.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                hienThiDanhSachBienBao(position);
            }
        });

        rvBienBao.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (rvBienBao.computeVerticalScrollOffset() == 0 ||
                        isMaxScrollReached(rvBienBao)) {
                    fab.hide(true);
                } else
                    fab.show(true);
            }

            private boolean isMaxScrollReached(RecyclerView recyclerView) {
                int maxScroll = recyclerView.computeVerticalScrollRange();
                int currentScroll = recyclerView.computeVerticalScrollOffset() + recyclerView.computeVerticalScrollExtent();
                return currentScroll >= maxScroll;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvBienBao.smoothScrollToPosition(0);
            }
        });
    }

    private void hienThiDanhSachBienBao(int flag) {
        frame_container.setVisibility(View.VISIBLE);
        txtMoTa.setText(Html.fromHtml(lstNhomBienBao.get(flag).getMoTa()));

        FastItemAdapter<BienBao> adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);
        adapter.add(lstAdapter.get(flag).getAdapterItems());

        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(adapter);
        animationAdapter.setDuration(1500);
        animationAdapter.setInterpolator(new OvershootInterpolator());
        animationAdapter.setFirstOnly(false);

        rvBienBao.setAdapter(animationAdapter);
    }

    public class LoadDataTask extends AsyncTask<Void, List<BienBao>, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MaterialDialog.Builder(BienBaoActivity.this)
                    .title("Đang tải dữ liệu...")
                    .customView(R.layout.custom_dialog_loading, true)
                    .show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            spinnerNhomBienBao.setItems(nhomBienBaoDB.getTenNhomBienBao(lstNhomBienBao));
            hienThiDanhSachBienBao(0);
        }

        @SafeVarargs
        @Override
        protected final void onProgressUpdate(List<BienBao>... values) {
            super.onProgressUpdate(values);
            adapter = new FastItemAdapter<>();
            adapter.add(values[0]);
            lstAdapter.add(adapter);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            nhomBienBaoDB = new NhomBienBaoDB(BienBaoActivity.this);
            lstNhomBienBao = nhomBienBaoDB.getAll();

            bienBaoDB = new BienBaoDB(BienBaoActivity.this);
            lstAdapter = new ArrayList<>();
            for (NhomBienBao nhomBienBao : lstNhomBienBao) {
                List<BienBao> lstBienBao = bienBaoDB.getByNhom(nhomBienBao.getId());
                publishProgress(lstBienBao);
            }
            return null;
        }
    }
}
