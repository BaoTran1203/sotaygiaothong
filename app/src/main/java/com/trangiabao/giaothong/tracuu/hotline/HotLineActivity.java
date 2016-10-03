package com.trangiabao.giaothong.tracuu.hotline;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.hotline.db.HotLineDB;
import com.trangiabao.giaothong.tracuu.hotline.db.NhomHotLineDB;
import com.trangiabao.giaothong.tracuu.hotline.model.HotLine;
import com.trangiabao.giaothong.tracuu.hotline.model.NhomHotLine;

import java.util.ArrayList;
import java.util.List;

public class HotLineActivity extends AppCompatActivity {

    private Context context = HotLineActivity.this;

    // controls
    private Toolbar toolbar;
    private FastItemAdapter<HotLine> adapter;
    private RecyclerView rvHotLine;
    private MaterialSpinner spinner;
    private TextView txtMoTa;

    // datas
    private List<NhomHotLine> lstNhomHotLine;
    private List<String> lstItemSpinner;
    private List<List<HotLine>> lstHotLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_line);

        new LoadData().execute();
        addControls();
        addEvents();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);

        rvHotLine = (RecyclerView) findViewById(R.id.rvHotLine);
        RecyclerViewHeader rvHeader = (RecyclerViewHeader) findViewById(R.id.rvHeader);
        rvHotLine.setLayoutManager(new LinearLayoutManager(context));
        rvHeader.attachTo(rvHotLine);
        rvHotLine.setAdapter(adapter);

        spinner = (MaterialSpinner) findViewById(R.id.spinner);
        txtMoTa = (TextView) findViewById(R.id.txtMoTa);
    }

    private void addEvents() {
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                selectSpinnerItem(position);
            }
        });
    }

    private void selectSpinnerItem(int i) {
        adapter.clear();
        adapter.add(lstHotLine.get(i));
        adapter.notifyAdapterDataSetChanged();
        txtMoTa.setText(Html.fromHtml(lstNhomHotLine.get(i).getMoTa()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (android.R.id.home == id) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public class LoadData extends AsyncTask<Void, Void, Void> {

        List<NhomHotLine> lstNhomHotLine;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            spinner.setItems(lstItemSpinner);
            selectSpinnerItem(0);
        }

        @Override
        protected Void doInBackground(Void... aVoid) {
            lstNhomHotLine = new NhomHotLineDB(context).getAll();
            lstItemSpinner = new NhomHotLineDB(context).getAllString(lstNhomHotLine);
            lstHotLine = new ArrayList<>();
            for (NhomHotLine nhomHotLine : lstNhomHotLine) {
                List<HotLine> temp = new HotLineDB(context).getByIdNhomHotline(nhomHotLine.getId() + "");
                lstHotLine.add(temp);
            }
            return null;
        }
    }
}
