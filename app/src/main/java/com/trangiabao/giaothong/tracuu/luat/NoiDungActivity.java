package com.trangiabao.giaothong.tracuu.luat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.database.ChuongDB;
import com.trangiabao.giaothong.database.DieuDB;
import com.trangiabao.giaothong.database.NoiDungDB;
import com.trangiabao.giaothong.database.VanBanDB;
import com.trangiabao.giaothong.model.Chuong;
import com.trangiabao.giaothong.model.Dieu;
import com.trangiabao.giaothong.model.NoiDung;

import java.util.ArrayList;

public class NoiDungActivity extends AppCompatActivity {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;

    private Toolbar toolbar;
    private TextView txtChuong, txtDieu, txtNoiDung;
    private Animation slideInLeft, slideOutLeft, downFromTop;
    private ImageButton imgTruoc, imgSau;
    private MaterialSearchView searchView;

    private int flag = 0;
    private int idVanBan = 1;
    private ChuongDB chuongDB;
    private Chuong chuong;
    private DieuDB dieuDB;
    private Dieu dieu;
    private NoiDungDB noiDungDB;
    private ArrayList<ArrayList<NoiDung>> lstnoiDung = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noi_dung);

        getItents();
        loadDatas();
        addControls();
        addEvents();
    }

    private void getItents() {
        idVanBan = getIntent().getExtras().getInt("ID_VAN_BAN");
    }

    private void loadDatas() {
        chuongDB = new ChuongDB(this);
        dieuDB = new DieuDB(this);
        noiDungDB = new NoiDungDB(this);

        ArrayList<NoiDung> lstTemp = noiDungDB.getByIdVanBan(idVanBan);
        int first = lstTemp.get(0).getIdDieu();
        int last = lstTemp.get(lstTemp.size() - 1).getIdDieu();
        for (int i = first; i <= last; i++) {
            ArrayList<NoiDung> temp = noiDungDB.getByIdDieu(i);
            lstnoiDung.add(temp);
        }
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(new VanBanDB(this).getByID(idVanBan).getTenVietTat());

        txtChuong = (TextView) findViewById(R.id.txtChuong);
        txtDieu = (TextView) findViewById(R.id.txtDieu);
        txtNoiDung = (TextView) findViewById(R.id.txtNoiDung);
        imgTruoc = (ImageButton) findViewById(R.id.imgTruoc);
        imgSau = (ImageButton) findViewById(R.id.imgSau);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setHint(getString(R.string.tim_kiem));
        searchView.setEllipsize(true);
        searchView.setVoiceSearch(false);

        slideInLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        slideOutLeft = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        downFromTop = AnimationUtils.loadAnimation(this, R.anim.down_from_top);

        gestureDetector = new GestureDetector(new SwipeDetector());
    }

    private void addEvents() {
        hienThiNoiDung(flag, downFromTop);

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

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void hienThiNoiDung(int flag, Animation animation) {
        if (flag == 1)
            imgTruoc.setVisibility(View.GONE);
        else if (flag == lstnoiDung.size())
            imgSau.setVisibility(View.GONE);
        else {
            imgTruoc.setVisibility(View.VISIBLE);
            imgSau.setVisibility(View.VISIBLE);
        }

        ArrayList<NoiDung> data = lstnoiDung.get(flag);

        int idChuong = data.get(0).getIdChuong();
        chuong = chuongDB.getById(idChuong);
        txtChuong.startAnimation(animation);
        txtChuong.setText(Html.fromHtml(chuong.getTenChuong()));

        int idDieu = data.get(0).getIdDieu();
        dieu = dieuDB.getById(idDieu);
        txtDieu.startAnimation(animation);
        txtDieu.setText(Html.fromHtml(dieu.getTenDieu()));

        String noiDung = "";
        for (NoiDung temp : data) {
            noiDung += temp.getNoiDung();
        }
        txtNoiDung.startAnimation(animation);
        txtNoiDung.setText(Html.fromHtml(noiDung));
    }

    private boolean previous() {
        if (flag - 1 >= 0) {
            hienThiNoiDung(--flag, slideInLeft);
            return true;
        }
        return false;
    }

    private boolean next() {
        if (flag + 1 < lstnoiDung.size()) {
            hienThiNoiDung(++flag, slideOutLeft);
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
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
}
