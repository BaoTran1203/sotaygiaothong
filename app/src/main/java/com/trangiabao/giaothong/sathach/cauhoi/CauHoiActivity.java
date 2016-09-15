package com.trangiabao.giaothong.sathach.cauhoi;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.afollestad.materialdialogs.MaterialDialog;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.sathach.db.CauHoiDB;
import com.trangiabao.giaothong.sathach.model.CauHoi;
import com.trangiabao.giaothong.sathach.model.CauTraLoi;
import com.trangiabao.giaothong.sathach.model.HinhCauHoi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CauHoiActivity extends AppCompatActivity {

    private GestureDetector gestureDetector;
    private Toolbar toolbar;
    private ViewGroup container;
    private TextView txtCauHoi;
    private TextSwitcher txtGiaiThich;
    private Button btnDapAn, btnTruoc, btnSau;

    private int flag = 1;
    private List<CauHoi> lstCauHoi = new ArrayList<>();
    private CauHoi cauHoi;
    private List<CauTraLoi> lstCauTraLoi;
    private List<HinhCauHoi> lstHinhCauHoi;
    private ArrayList<AppCompatCheckBox> lstCheckBoxCauTraLoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cau_hoi);

        new LoadData().execute();
        addControls();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gestureDetector = new GestureDetector(new SwipeDetector());
        container = (ViewGroup) findViewById(R.id.container);
        btnDapAn = (Button) findViewById(R.id.btnDapAn);
        btnTruoc = (Button) findViewById(R.id.btnTruoc);
        btnSau = (Button) findViewById(R.id.btnSau);
    }

    private void hienThiCauHoi(final int flag) {
        int soCauHoi = lstCauHoi.size();
        btnTruoc.setEnabled(flag != 1);
        btnSau.setEnabled(flag != soCauHoi);
        btnDapAn.setVisibility(View.VISIBLE);
        cauHoi = lstCauHoi.get(flag - 1);
        toolbar.setTitle("Câu " + flag + "/" + soCauHoi);

        txtCauHoi = new TextView(CauHoiActivity.this);
        txtCauHoi.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        txtCauHoi.setTypeface(txtCauHoi.getTypeface(), Typeface.BOLD);
        txtCauHoi.setText("Câu " + flag + ": " + cauHoi.getCauHoi());

        lstHinhCauHoi = cauHoi.getLstHinhCauHoi();
        int soHinh = lstHinhCauHoi.size();
        LinearLayout layoutImage = new LinearLayout(CauHoiActivity.this);
        layoutImage.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutImage.setOrientation(LinearLayout.HORIZONTAL);
        layoutImage.setWeightSum(soHinh);
        int width = soHinh == 1 ? LinearLayout.LayoutParams.MATCH_PARENT : getDip(100);
        int height = soHinh == 1 ? getDip(200) : getDip(100);
        int margin = getDip(5);
        for (int i = 0; i < soHinh; i++) {
            Drawable drawable = null;
            try {
                InputStream is = getAssets().open(lstHinhCauHoi.get(i).getHinh());
                drawable = Drawable.createFromStream(is, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageView img = new ImageView(this);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(width, height, 1f);
            imageParams.gravity = Gravity.CENTER;
            imageParams.setMargins(margin, margin, margin, margin);
            img.setLayoutParams(imageParams);
            img.setImageDrawable(drawable);
            img.requestLayout();
            layoutImage.addView(img);
        }

        lstCauTraLoi = cauHoi.getLstCauTraLoi();
        LinearLayout layoutCauTraLoi = new LinearLayout(CauHoiActivity.this);
        layoutCauTraLoi.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutCauTraLoi.setOrientation(LinearLayout.VERTICAL);
        lstCheckBoxCauTraLoi = new ArrayList<>();
        for (CauTraLoi cauTraLoi : lstCauTraLoi) {
            AppCompatCheckBox chk = new AppCompatCheckBox(CauHoiActivity.this);
            chk.setText(cauTraLoi.getCauTraLoi());
            chk.setGravity(Gravity.TOP);
            int padding = getDip(5);
            chk.setPadding(padding, padding, padding, padding);
            lstCheckBoxCauTraLoi.add(chk);
            layoutCauTraLoi.addView(chk);
        }

        txtGiaiThich = new TextSwitcher(CauHoiActivity.this);
        txtGiaiThich.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        txtGiaiThich.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new TextView(CauHoiActivity.this);
            }
        });

        container.removeAllViews();
        container.addView(txtCauHoi);
        container.addView(layoutImage);
        container.addView(layoutCauTraLoi);
        container.removeView(btnDapAn);
        container.addView(btnDapAn);
        container.addView(txtGiaiThich);

        addEvents();
    }

    private void addEvents() {
        btnDapAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < lstCauTraLoi.size(); i++) {
                    if (lstCauTraLoi.get(i).isDapAn()) {
                        lstCheckBoxCauTraLoi.get(i).setTextColor(Color.BLUE);
                        lstCheckBoxCauTraLoi.get(i).setTypeface(Typeface.DEFAULT_BOLD);
                    } else {
                        lstCheckBoxCauTraLoi.get(i).setPaintFlags(lstCheckBoxCauTraLoi.get(i).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    lstCheckBoxCauTraLoi.get(i).setEnabled(false);
                }
                txtGiaiThich.setText(Html.fromHtml("<h3>Giải thích</h3>" + cauHoi.getGiaiThich()));
                btnDapAn.setVisibility(View.GONE);
            }
        });

        btnTruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                truoc();
            }
        });

        btnSau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sau();
            }
        });
    }

    private int getDip(int pixel) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, getResources().getDisplayMetrics());
    }

    private void luuTrangThai() {
        for (int i = 0; i < lstCauTraLoi.size(); i++) {
            boolean isChecked = lstCheckBoxCauTraLoi.get(i).isChecked();
            lstCauHoi.get(flag - 1).getLstCauTraLoi().get(i).setChecked(isChecked);
        }
    }

    private void truoc() {
        if (flag - 1 > 0) {
            luuTrangThai();
            hienThiCauHoi(--flag);
        }
    }

    private void sau() {
        if (flag + 1 <= lstCauHoi.size()) {
            luuTrangThai();
            hienThiCauHoi(++flag);
        }
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

    class SwipeDetector extends GestureDetector.SimpleOnGestureListener {

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

    public class LoadData extends AsyncTask<Void, Void, Void> {

        private MaterialDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new MaterialDialog.Builder(CauHoiActivity.this)
                    .title("Đang tải dữ liệu...")
                    .customView(R.layout.custom_dialog_loading, true)
                    .show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            hienThiCauHoi(flag);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String query = getIntent().getExtras().getString("QUERY");
            lstCauHoi = new CauHoiDB(CauHoiActivity.this).getCauHoi(query);
            return null;
        }
    }
}