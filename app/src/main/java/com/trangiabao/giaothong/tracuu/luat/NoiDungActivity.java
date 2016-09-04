package com.trangiabao.giaothong.tracuu.luat;

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
import android.widget.ImageButton;
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

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;

    private Toolbar toolbar;
    private TextSwitcher txtDieu, txtNoiDung;
    private TextView txtChuong, txtMuc;
    private ImageButton imgTruoc, imgSau;
    private Animation slideInLeft, slideOutLeft, slideOutRight, slideInRight;

    private int flag = 1;
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

        txtDieu = (TextSwitcher) findViewById(R.id.txtDieu);
        txtDieu.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new TextView(NoiDungActivity.this);
            }
        });
        txtNoiDung = (TextSwitcher) findViewById(R.id.txtNoiDung);
        txtNoiDung.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new TextView(NoiDungActivity.this);
            }
        });
        txtChuong = (TextView) findViewById(R.id.txtChuong);
        txtMuc = (TextView) findViewById(R.id.txtMuc);
        imgTruoc = (ImageButton) findViewById(R.id.imgTruoc);
        imgSau = (ImageButton) findViewById(R.id.imgSau);

        gestureDetector = new GestureDetector(new SwipeDetector());
        slideInLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        slideOutLeft = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        slideInRight = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        slideOutRight = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
    }

    private void addEvents() {
        imgTruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previous();
            }
        });

        imgSau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });
    }

    private void hienThiNoiDung(int flag) {
        if (flag == 1)
            imgTruoc.setVisibility(View.GONE);
        else if (flag == lstDieu.size())
            imgSau.setVisibility(View.GONE);
        else {
            imgTruoc.setVisibility(View.VISIBLE);
            imgSau.setVisibility(View.VISIBLE);
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

    private boolean previous() {
        if (flag - 1 > 0) {
            txtDieu.setInAnimation(slideInLeft);
            txtDieu.setOutAnimation(slideOutRight);
            txtNoiDung.setInAnimation(slideInLeft);
            txtNoiDung.setOutAnimation(slideOutRight);
            hienThiNoiDung(--flag);
            return true;
        }
        return false;
    }

    private boolean next() {
        if (flag + 1 <= lstDieu.size()) {
            txtDieu.setInAnimation(slideInRight);
            txtDieu.setOutAnimation(slideOutLeft);
            txtNoiDung.setInAnimation(slideInRight);
            txtNoiDung.setOutAnimation(slideOutLeft);
            hienThiNoiDung(++flag);
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
        if (gestureDetector != null) {
            if (gestureDetector.onTouchEvent(ev))
                return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private class SwipeDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                return next();
            }
            if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                return previous();
            }
            return false;
        }
    }

    class LoadData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            hienThiNoiDung(1);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String idChuong = getIntent().getExtras().getString("ID_CHUONG");
            Chuong chuong = new ChuongDB(NoiDungActivity.this).getById(idChuong);
            txtChuong.setText(chuong.getTenChuong());
            if (chuong.getMuc().equals("")) {
                txtMuc.setVisibility(View.GONE);
            } else {
                txtMuc.setVisibility(View.VISIBLE);
                txtMuc.setText(chuong.getMuc());
            }
            lstDieu = new DieuDB(NoiDungActivity.this).getByIdChuong(idChuong + "");
            return null;
        }
    }
}
