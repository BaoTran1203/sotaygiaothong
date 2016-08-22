package com.trangiabao.giaothong.sathach.cauhoi;

import android.graphics.BitmapFactory;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.database.CauHoiDB;
import com.trangiabao.giaothong.database.CauTraLoiDB;
import com.trangiabao.giaothong.database.DanhMucHinhDB;
import com.trangiabao.giaothong.database.HinhCauHoiDB;
import com.trangiabao.giaothong.model.CauHoi;
import com.trangiabao.giaothong.model.CauTraLoi;
import com.trangiabao.giaothong.model.DanhMucHinh;
import com.trangiabao.giaothong.model.HinhCauHoi;

import java.util.ArrayList;

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
    private Animation slideInLeft, slideOutLeft, downFromTop, upFromBottom;
    private ImageButton imgTruoc, imgSau;
    private MaterialDialog dialog;

    private int flag = 1;
    private ArrayList<CauHoi> lstCauHoi;
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

        slideInLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        slideOutLeft = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        downFromTop = AnimationUtils.loadAnimation(this, R.anim.down_from_top);
        upFromBottom = AnimationUtils.loadAnimation(this, R.anim.up_from_bottom);

        gestureDetector = new GestureDetector(new SwipeDetector());
    }

    private void addEvents() {
        btnDapAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtDapAn.setText(kiemTraKetQua());
                txtGiaiThich.setText(Html.fromHtml(cauHoi.getGiaiThich()));
                layoutDapAn.setVisibility(View.VISIBLE);
                layoutDapAn.startAnimation(upFromBottom);
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

    private String kiemTraKetQua() {
        int size = lstCheckBoxCauTraLoi.size();
        boolean flag = false;
        for (int i = 0; i < size; i++) {
            if (lstCheckBoxCauTraLoi.get(i).isChecked())
                flag = true;
        }
        String dapAn;
        if (!flag) {
            dapAn = "Bạn chưa chọn đáp án!";
        } else {
            dapAn = "Bạn chọn ";
            for (int i = 0; i < size; i++) {
                if (lstCheckBoxCauTraLoi.get(i).isChecked())
                    dapAn += (i + 1) + ",";
            }
            dapAn = dapAn.substring(0, dapAn.length() - 1);
        }
        String ketQua = "Kết quả ";
        ArrayList<CauTraLoi> lstCauTraLoi = cauHoi.getLstCauTraLoi();
        for (int i = 0; i < size; i++) {
            if (lstCauTraLoi.get(i).isDapAn())
                ketQua += (i + 1) + ",";
        }
        ketQua = ketQua.substring(0, ketQua.length() - 1);
        return dapAn + "\n\n" + ketQua;
    }

    private void hienThiCauHoi(int flag, Animation animation) {
        if (flag == 1)
            imgTruoc.setVisibility(View.GONE);
        else if (flag == lstCauHoi.size())
            imgSau.setVisibility(View.GONE);
        else {
            imgTruoc.setVisibility(View.VISIBLE);
            imgSau.setVisibility(View.VISIBLE);
        }
        layoutDapAn.startAnimation(animation);
        layoutDapAn.setVisibility(View.GONE);
        int soCauHoi = lstCauHoi.size();
        cauHoi = lstCauHoi.get(flag - 1);
        toolbar.setTitle("Câu " + flag + "/" + soCauHoi);
        txtCauHoi.startAnimation(animation);
        txtCauHoi.setText(Html.fromHtml("Câu " + flag + ": " + cauHoi.getCauHoi()));
        setLayoutImage(cauHoi.getLstHinh(), animation);
        setLayoutCauTraLoi(cauHoi.getLstCauTraLoi(), animation);
        btnDapAn.startAnimation(upFromBottom);
    }

    private void setLayoutCauTraLoi(ArrayList<CauTraLoi> lstCauTraLoi, Animation animation) {
        layoutCauTraLoi.removeAllViews();
        lstCheckBoxCauTraLoi = new ArrayList<>();
        for (CauTraLoi cauTraLoi : lstCauTraLoi) {
            AppCompatCheckBox chk = new AppCompatCheckBox(CauHoiActivity.this);
            chk.setText(Html.fromHtml(cauTraLoi.getCauTraLoi()));
            chk.setGravity(Gravity.TOP);
            int padding = getDip(5);
            chk.setPadding(padding, padding, padding, padding);
            chk.startAnimation(animation);
            lstCheckBoxCauTraLoi.add(chk);
            layoutCauTraLoi.addView(chk);
        }
    }

    private void setLayoutImage(ArrayList<DanhMucHinh> lstImage, Animation animation) {
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
            ImageView img = createImageView(lstImage.get(i).getHinh(), width, height, margin);
            img.startAnimation(animation);
            layoutImage.addView(img);
        }
    }

    private int getDip(int pixel) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, getResources().getDisplayMetrics());
    }

    private ImageView createImageView(byte[] image, int width, int height, int margin) {
        ImageView img = new ImageView(this);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(width, height, 1f);
        imageParams.gravity = Gravity.CENTER;
        imageParams.setMargins(margin, margin, margin, margin);
        img.setLayoutParams(imageParams);
        img.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
        img.requestLayout();
        return img;
    }

    private boolean previous() {
        if (flag - 1 > 0) {
            hienThiCauHoi(--flag, slideInLeft);
            return true;
        }
        return false;
    }

    private boolean next() {
        if (flag + 1 <= lstCauHoi.size()) {
            hienThiCauHoi(++flag, slideOutLeft);
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
            hienThiCauHoi(flag, downFromTop);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String query = getIntent().getExtras().getString("QUERY");
            lstCauHoi = new ArrayList<>();
            ArrayList<CauHoi> temp = new CauHoiDB(CauHoiActivity.this).getCauHoi(query);
            for (CauHoi cauHoi : temp) {
                int idCauHoi = cauHoi.getId();
                ArrayList<HinhCauHoi> lstHinhCauHoi = new HinhCauHoiDB(CauHoiActivity.this).getByIdCauHoi(idCauHoi);
                ArrayList<DanhMucHinh> lstDanhMucHinh = new ArrayList<>();
                if (lstHinhCauHoi.size() > 0) {
                    for (HinhCauHoi hinhCauHoi : lstHinhCauHoi) {
                        lstDanhMucHinh.add(new DanhMucHinhDB(CauHoiActivity.this).getById(hinhCauHoi.getIdHinh()));
                    }
                }
                cauHoi.setLstHinh(lstDanhMucHinh);
                ArrayList<CauTraLoi> lstCauTraLoi = new CauTraLoiDB(CauHoiActivity.this).getCauTraLoiByIdCauHoi(idCauHoi);
                cauHoi.setLstCauTraLoi(lstCauTraLoi);
                lstCauHoi.add(cauHoi);
            }
            return null;
        }
    }
}
