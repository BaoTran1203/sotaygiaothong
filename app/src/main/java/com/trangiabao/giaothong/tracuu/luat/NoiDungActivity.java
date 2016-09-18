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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.luat.db.ChuongDB;
import com.trangiabao.giaothong.tracuu.luat.db.DieuDB;
import com.trangiabao.giaothong.tracuu.luat.model.Chuong;
import com.trangiabao.giaothong.tracuu.luat.model.Dieu;
import com.trangiabao.giaothong.tracuu.luat.model.NoiDung;

import java.util.List;

public class NoiDungActivity extends AppCompatActivity {

    private Context context = NoiDungActivity.this;

    private GestureDetector gestureDetector;
    private Toolbar toolbar;
    private TextSwitcher txtDieu, txtNoiDung;
    private TextView txtChuong, txtMuc;
    private Button btnTruoc, btnSau;
    private Animation slideInLeft, slideOutLeft, slideOutRight, slideInRight;

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
        gestureDetector = new GestureDetector(new SwipeDetector());

        txtDieu = (TextSwitcher) findViewById(R.id.txtDieu);
        txtNoiDung = (TextSwitcher) findViewById(R.id.txtNoiDung);
        txtChuong = (TextView) findViewById(R.id.txtChuong);
        txtMuc = (TextView) findViewById(R.id.txtMuc);
        btnTruoc = (Button) findViewById(R.id.btnTruoc);
        btnSau = (Button) findViewById(R.id.btnSau);

        slideInLeft = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
        slideOutLeft = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
        slideInRight = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
        slideOutRight = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);

        txtDieu.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new TextView(NoiDungActivity.this);
            }
        });
        txtNoiDung.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new TextView(NoiDungActivity.this);
            }
        });
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
    }

    private boolean truoc() {
        if (index - 1 > 0) {
            txtDieu.setInAnimation(slideInLeft);
            txtDieu.setOutAnimation(slideOutRight);
            txtNoiDung.setInAnimation(slideInLeft);
            txtNoiDung.setOutAnimation(slideOutRight);
            hienThiNoiDung(--index);
            return true;
        }
        return false;
    }

    private boolean sau() {
        if (index + 1 <= lstDieu.size()) {
            txtDieu.setInAnimation(slideInRight);
            txtDieu.setOutAnimation(slideOutLeft);
            txtNoiDung.setInAnimation(slideInRight);
            txtNoiDung.setOutAnimation(slideOutLeft);
            hienThiNoiDung(++index);
            return true;
        }
        return false;
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
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                return sau();
            }
            return e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY && truoc();
        }
    }

    class LoadData extends AsyncTask<Void, Void, Void> {

        private Chuong chuong;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            txtChuong.setText(chuong.getTenChuong());
            if (chuong.getMuc().equals("")) {
                txtMuc.setVisibility(View.GONE);
            } else {
                txtMuc.setVisibility(View.VISIBLE);
                txtMuc.setText(chuong.getMuc());
            }
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
