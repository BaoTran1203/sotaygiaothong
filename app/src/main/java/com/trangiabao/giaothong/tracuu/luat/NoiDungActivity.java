package com.trangiabao.giaothong.tracuu.luat;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.luat.db.ChuongDB;
import com.trangiabao.giaothong.tracuu.luat.db.DieuDB;
import com.trangiabao.giaothong.tracuu.luat.model.Chuong;
import com.trangiabao.giaothong.tracuu.luat.model.Dieu;
import com.trangiabao.giaothong.tracuu.luat.model.NoiDung;

import java.util.List;

public class NoiDungActivity extends AppCompatActivity {

    private Context context = NoiDungActivity.this;

    // controls
    private GestureDetector gestureDetector;
    private Toolbar toolbar;
    private ViewGroup container;
    private TextView txtChuong, txtMuc, txtDieu, txtNoiDung;
    private Button btnTruoc, btnSau;
    private View line;
    private ScrollView scroll;

    // data
    private int index = 1;
    private List<Dieu> lstDieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noi_dung);

        new LoadData().execute();
        addControls();
        addEvents();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Nội dung");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        container = (ViewGroup) findViewById(R.id.container);
        gestureDetector = new GestureDetector(new SwipeDetector());
        txtChuong = (TextView) findViewById(R.id.txtChuong);
        txtMuc = (TextView) findViewById(R.id.txtMuc);
        txtDieu = (TextView) findViewById(R.id.txtDieu);
        txtNoiDung = (TextView) findViewById(R.id.txtNoiDung);
        btnTruoc = (Button) findViewById(R.id.btnTruoc);
        btnSau = (Button) findViewById(R.id.btnSau);
        line = findViewById(R.id.line);
        scroll = (ScrollView) findViewById(R.id.scroll);
    }

    private void addEvents() {
        btnTruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                truoc();
            }
        });

        btnSau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sau();
            }
        });
    }

    private void hienThiNoiDung(int flag) {
        if (flag == 1) {
            btnTruoc.setVisibility(View.GONE);
            btnSau.setVisibility(View.VISIBLE);
        } else if (flag == lstDieu.size()) {
            btnSau.setVisibility(View.GONE);
            btnTruoc.setVisibility(View.VISIBLE);
        } else {
            btnTruoc.setVisibility(View.VISIBLE);
            btnSau.setVisibility(View.VISIBLE);
        }

        Dieu dieu = lstDieu.get(flag - 1);
        String noiDung = "";
        List<NoiDung> lstNoiDung = dieu.getLstNoiDung();
        for (NoiDung temp : lstNoiDung) {
            noiDung += temp.getNoiDung();
        }
        txtDieu.setText(dieu.getTenDieu());
        txtNoiDung.setText(Html.fromHtml(noiDung));

        container.removeAllViews();
        container.addView(txtChuong);
        if (!txtMuc.getText().toString().equals(""))
            container.addView(txtMuc);
        container.addView(line);
        container.addView(txtDieu);
        container.addView(txtNoiDung);
        scroll.fullScroll(ScrollView.FOCUS_UP);
    }

    private void truoc() {
        if (index - 1 > 0) {
            hienThiNoiDung(--index);
        }
    }

    private void sau() {
        if (index + 1 <= lstDieu.size()) {
            hienThiNoiDung(++index);
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return gestureDetector != null && gestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private class SwipeDetector extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_MAX_OFF_PATH = 250;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            else if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                sau();
                return true;
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                truoc();
                return true;
            }
            return false;
        }
    }

    private class LoadData extends AsyncTask<Void, Void, Void> {

        private Chuong chuong;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            txtChuong.setText(chuong.getTenChuong());
            txtMuc.setText(chuong.getMuc());
            hienThiNoiDung(1);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String idChuong = getIntent().getExtras().getString("ID_CHUONG");
            chuong = new ChuongDB(context).getById(idChuong);
            lstDieu = new DieuDB(context).getByIdChuong(idChuong + "");
            return null;
        }
    }
}
