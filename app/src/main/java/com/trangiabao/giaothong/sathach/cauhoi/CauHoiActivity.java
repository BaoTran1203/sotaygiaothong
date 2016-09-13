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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;

    private Toolbar toolbar;
    private Button btnDapAn;
    private LinearLayout layoutImage, layoutCauTraLoi, layoutDapAn;
    private TextView txtCauHoi, txtDapAn, txtGiaiThich;
    private ArrayList<CheckBox> lstCheckBoxCauTraLoi;
    private ImageButton imgTruoc, imgSau;
    private MaterialDialog dialog;

    private int flag = 1;
    private List<CauHoi> lstCauHoi = new ArrayList<>();
    private CauHoi cauHoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cau_hoi);

        new LoadDataTask().execute();
        addControls();
        addEvents();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtCauHoi = (TextView) findViewById(R.id.txtCauHoi);
        layoutImage = (LinearLayout) findViewById(R.id.layoutImage);
        layoutCauTraLoi = (LinearLayout) findViewById(R.id.layoutCauTraLoi);
        layoutDapAn = (LinearLayout) findViewById(R.id.layoutDapAn);
        btnDapAn = (Button) findViewById(R.id.btnDapAn);
        txtDapAn = (TextView) findViewById(R.id.txtDapAn);
        txtGiaiThich = (TextView) findViewById(R.id.txtGiaiThich);
        imgTruoc = (ImageButton) findViewById(R.id.imgTruoc);
        imgSau = (ImageButton) findViewById(R.id.imgSau);

        gestureDetector = new GestureDetector(new SwipeDetector());
    }

    private void addEvents() {
        btnDapAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kiemTraKetQua();
                txtGiaiThich.setText(Html.fromHtml(cauHoi.getGiaiThich()));
                layoutDapAn.setVisibility(View.VISIBLE);
            }
        });

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

    private void kiemTraKetQua() {
        List<CauTraLoi> lstCauTraLoi = cauHoi.getLstCauTraLoi();
        for (int i = 0; i < lstCauTraLoi.size(); i++) {
            if (lstCauTraLoi.get(i).isDapAn()) {
                lstCheckBoxCauTraLoi.get(i).setTextColor(Color.BLUE);
                lstCheckBoxCauTraLoi.get(i).setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                lstCheckBoxCauTraLoi.get(i).setPaintFlags(lstCheckBoxCauTraLoi.get(i).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            lstCheckBoxCauTraLoi.get(i).setEnabled(false);
        }
        txtGiaiThich.setText(Html.fromHtml(cauHoi.getGiaiThich()));
        btnDapAn.setVisibility(View.GONE);
    }

    private void hienThiCauHoi(int flag) {
        if (flag == 1)
            imgTruoc.setVisibility(View.GONE);
        else if (flag == lstCauHoi.size())
            imgSau.setVisibility(View.GONE);
        else {
            imgTruoc.setVisibility(View.VISIBLE);
            imgSau.setVisibility(View.VISIBLE);
        }
        layoutDapAn.setVisibility(View.GONE);
        int soCauHoi = lstCauHoi.size();
        cauHoi = lstCauHoi.get(flag - 1);
        toolbar.setTitle("Câu " + flag + "/" + soCauHoi);
        txtCauHoi.setText(Html.fromHtml("Câu " + flag + ": " + cauHoi.getCauHoi()));
        setLayoutImage(cauHoi.getLstHinhCauHoi());
        setLayoutCauTraLoi(cauHoi.getLstCauTraLoi());
    }

    private void setLayoutCauTraLoi(List<CauTraLoi> lstCauTraLoi) {
        layoutCauTraLoi.removeAllViews();
        lstCheckBoxCauTraLoi = new ArrayList<>();
        for (CauTraLoi cauTraLoi : lstCauTraLoi) {
            AppCompatCheckBox chk = new AppCompatCheckBox(CauHoiActivity.this);
            chk.setText(Html.fromHtml(cauTraLoi.getCauTraLoi()));
            chk.setGravity(Gravity.TOP);
            int padding = getDip(5);
            chk.setPadding(padding, padding, padding, padding);
            lstCheckBoxCauTraLoi.add(chk);
            layoutCauTraLoi.addView(chk);
        }
    }

    private void setLayoutImage(List<HinhCauHoi> lstImage) {
        int size = lstImage.size();
        layoutImage.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutImage.setLayoutParams(params);
        layoutImage.setOrientation(LinearLayout.HORIZONTAL);
        layoutImage.setWeightSum(size);
        int width = size == 1 ? LinearLayout.LayoutParams.MATCH_PARENT : getDip(100);
        int height = size == 1 ? getDip(200) : getDip(100);
        int margin = getDip(5);
        for (int i = 0; i < size; i++) {
            Drawable drawable = null;
            try {
                InputStream is = getAssets().open(lstImage.get(i).getHinh());
                drawable = Drawable.createFromStream(is, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageView img = createImageView(drawable, width, height, margin);
            layoutImage.addView(img);
        }
    }

    private int getDip(int pixel) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, getResources().getDisplayMetrics());
    }

    private ImageView createImageView(Drawable image, int width, int height, int margin) {
        ImageView img = new ImageView(this);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(width, height, 1f);
        imageParams.gravity = Gravity.CENTER;
        imageParams.setMargins(margin, margin, margin, margin);
        img.setLayoutParams(imageParams);
        img.setImageDrawable(image);
        img.requestLayout();
        return img;
    }

    private boolean previous() {
        if (flag - 1 > 0) {
            hienThiCauHoi(--flag);
            return true;
        }
        return false;
    }

    private boolean next() {
        if (flag + 1 <= lstCauHoi.size()) {
            hienThiCauHoi(++flag);
            return true;
        }
        return false;
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
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            else if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                return next();
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                return previous();
            }
            return false;
        }
    }

    public class LoadDataTask extends AsyncTask<Void, Void, Void> {
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
            btnDapAn.setVisibility(View.VISIBLE);
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