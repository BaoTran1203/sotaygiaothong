package com.trangiabao.giaothong.sathach.meo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.sathach.meo.db.MeoDB;
import com.trangiabao.giaothong.sathach.meo.model.Meo;

import java.util.List;

public class MeoActivity extends AppCompatActivity {

    private Context context = MeoActivity.this;

    // controls
    private Toolbar toolbar;
    private MaterialSpinner spinner;
    private WebView webView;

    // datas
    private List<Meo> lstMeo;
    private List<String> lstStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meo);

        lstMeo = new MeoDB(context).getAll();
        lstStr = new MeoDB(context).getListString(lstMeo);
        addControls();
        addEvents();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Mẹo ghi nhớ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (WebView) findViewById(R.id.webView);
        spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems(lstStr);
        selectSpinnerItem(0);
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
        Meo meo = lstMeo.get(i);
        webView.loadUrl("file:///android_asset/" + meo.getHtml());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
