package com.trangiabao.giaothong.tracuu.luat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.luat.db.ChuongDB;
import com.trangiabao.giaothong.tracuu.luat.db.NoiDungDB;
import com.trangiabao.giaothong.tracuu.luat.model.Chuong;
import com.trangiabao.giaothong.tracuu.luat.model.NoiDung;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.List;

public class NoiDungActivity extends AppCompatActivity {

    // controls
    private Context context = NoiDungActivity.this;
    private GestureDetector gestureDetector;
    private Toolbar toolbar;
    private ViewGroup container;
    private TextView txtNoiDung;
    private Button btnTruoc, btnSau;
    private ScrollView scroll;
    private AdView adView;

    // data
    private int index = 1;
    private List<NoiDung> lstNoiDung;

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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        container = (ViewGroup) findViewById(R.id.container);
        gestureDetector = new GestureDetector(new SwipeDetector());
        txtNoiDung = (TextView) findViewById(R.id.txtNoiDung);
        btnTruoc = (Button) findViewById(R.id.btnTruoc);
        btnSau = (Button) findViewById(R.id.btnSau);
        scroll = (ScrollView) findViewById(R.id.scroll);

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
        } else if (flag == lstNoiDung.size()) {
            btnSau.setVisibility(View.GONE);
            btnTruoc.setVisibility(View.VISIBLE);
        } else {
            btnTruoc.setVisibility(View.VISIBLE);
            btnSau.setVisibility(View.VISIBLE);
        }

        String noiDung = lstNoiDung.get(flag - 1).getNoiDung();
        txtNoiDung.setText(Html.fromHtml(noiDung));

        container.removeAllViews();
        container.addView(txtNoiDung);
        scroll.fullScroll(ScrollView.FOCUS_UP);
    }

    private void truoc() {
        if (index - 1 > 0) {
            hienThiNoiDung(--index);
        }
    }

    private void sau() {
        if (index + 1 <= lstNoiDung.size()) {
            hienThiNoiDung(++index);
        }
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
        private MaterialDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Drawable icon = MaterialDrawableBuilder.with(context)
                    .setIcon(MaterialDrawableBuilder.IconValue.DOWNLOAD)
                    .setColor(Color.parseColor("#1976D2"))
                    .build();

            dialog = new MaterialDialog.Builder(context)
                    .title("Đang tải dữ liệu...")
                    .progress(true, 0)
                    .icon(icon)
                    .autoDismiss(false).cancelable(false).canceledOnTouchOutside(false)
                    .show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            if (chuong.getTenChuong().equals(""))
                toolbar.setTitle(getIntent().getExtras().getString("ten"));
            else
                toolbar.setTitle(chuong.getTenChuong());
            hienThiNoiDung(1);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String idChuong = getIntent().getExtras().getString("id");
            chuong = new ChuongDB(context).getById(idChuong);
            lstNoiDung = new NoiDungDB(context).getByIdChuong(idChuong + "");
            return null;
        }
    }
}
