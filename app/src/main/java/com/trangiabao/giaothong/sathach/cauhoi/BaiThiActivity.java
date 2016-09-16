package com.trangiabao.giaothong.sathach.cauhoi;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.sathach.cauhoi.db.CauHoiDB;
import com.trangiabao.giaothong.sathach.cauhoi.db.LoaiBangDB;
import com.trangiabao.giaothong.sathach.cauhoi.db.NhomCauHoiDB;
import com.trangiabao.giaothong.sathach.cauhoi.db.QuyTacRaDeDB;
import com.trangiabao.giaothong.sathach.cauhoi.model.CauHoi;
import com.trangiabao.giaothong.sathach.cauhoi.model.CauTraLoi;
import com.trangiabao.giaothong.sathach.cauhoi.model.HinhCauHoi;
import com.trangiabao.giaothong.sathach.cauhoi.model.LoaiBang;
import com.trangiabao.giaothong.sathach.cauhoi.model.NhomCauHoi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BaiThiActivity extends AppCompatActivity {

    private GestureDetector gestureDetector;
    private Toolbar toolbar;
    private ViewGroup container;
    private TextView txtCauHoi, txtThoiGian;
    private TextSwitcher txtGiaiThich;
    private Button btnTruoc, btnSau;
    private ImageButton imgMucLuc;
    private ImageView imgTamDung;
    private CountDown countDown;

    private int flag = 1;
    private List<CauHoi> lstCauHoi;
    private CauHoi cauHoi;
    private List<CauTraLoi> lstCauTraLoi;
    private List<HinhCauHoi> lstHinhCauHoi;
    private ArrayList<AppCompatCheckBox> lstCheckBoxCauTraLoi;
    private LoaiBang loaiBang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_thi);

        new LoadDataTask().execute();
        addControls();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gestureDetector = new GestureDetector(new SwipeDetector());
        container = (ViewGroup) findViewById(R.id.container);

        btnTruoc = (Button) findViewById(R.id.btnTruoc);
        btnSau = (Button) findViewById(R.id.btnSau);
        imgMucLuc = (ImageButton) findViewById(R.id.imgMucLuc);
        imgTamDung = (ImageView) findViewById(R.id.imgTamDung);
        txtThoiGian = (TextView) findViewById(R.id.txtThoiGian);
    }

    private void addEvents() {
        container.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                luuTrangThai();
            }
        });

        btnTruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previous();
            }
        });

        btnSau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        imgMucLuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < lstCauTraLoi.size(); i++) {
                    boolean isChecked = lstCheckBoxCauTraLoi.get(i).isChecked();
                    lstCauHoi.get(flag - 1).getLstCauTraLoi().get(i).setChecked(isChecked);
                }
                List<String> lst = createStaticData();
                new MaterialDialog.Builder(BaiThiActivity.this)
                        .title("Danh sách câu hỏi")
                        .items(lst)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                flag = position + 1;
                                hienThiCauHoi(flag);
                                dialog.dismiss();
                                countDown.resume();
                            }
                        })
                        .positiveText("Kết thúc bài thi")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                countDown.stop();
                                dialog.dismiss();
                                tinhDiem();
                            }
                        })
                        .show();
            }
        });

        imgTamDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDown.pause();
                new MaterialDialog.Builder(BaiThiActivity.this)
                        .customView(R.layout.custom_dialog_pause, true)
                        .autoDismiss(false)
                        .cancelable(false)
                        .canceledOnTouchOutside(false)
                        .positiveText("Tiếp tục")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                countDown.resume();
                            }
                        })
                        .show();
            }
        });
    }

    private void luuTrangThai() {
        for (int i = 0; i < lstCauTraLoi.size(); i++) {
            boolean isChecked = lstCheckBoxCauTraLoi.get(i).isChecked();
            lstCauHoi.get(flag - 1).getLstCauTraLoi().get(i).setChecked(isChecked);
        }
    }

    private void tinhDiem() {
        new MaterialDialog.Builder(BaiThiActivity.this)
                .title("Bạn có chắc muốn kết thúc ?")
                .content("Sau khi kết thúc bạn không thể quay lại trang làm bài.")
                .positiveText("Đồng ý")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        int tongSoCau = lstCauHoi.size();
                        int soCauDung = 0;
                        int soCauChuaTraLoi = 0;
                        for (int i = 0; i < tongSoCau; i++) {
                            CauHoi cauHoi = lstCauHoi.get(i);
                            List<CauTraLoi> lstCauTraLoi = cauHoi.getLstCauTraLoi();
                            if (!isTraLoi(lstCauTraLoi)) {
                                soCauChuaTraLoi++;
                            } else {
                                if (kiemTraCauTraLoi(lstCauTraLoi))
                                    soCauDung++;
                            }
                        }
                        String content = "Số câu đúng: " + soCauDung;
                        content += "\nChưa trả lời: " + soCauChuaTraLoi;
                        content += "\nSố câu sai: " + (tongSoCau - soCauDung - soCauChuaTraLoi);
                        content += "\nKết quả: ";
                        if (soCauDung >= loaiBang.getSoCauDatYeuCau())
                            content += "Đạt";
                        else content += "Chưa đạt";
                        new MaterialDialog.Builder(BaiThiActivity.this)
                                .title("Kết quả")
                                .content(content)
                                .negativeText("Quay về trang trước")
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                })
                                .show();
                    }
                })
                .negativeText("Hủy")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private List<String> createStaticData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < lstCauHoi.size(); i++) {
            CauHoi cauHoi = lstCauHoi.get(i);
            List<CauTraLoi> lstCauTraLoi = cauHoi.getLstCauTraLoi();
            if (isTraLoi(lstCauTraLoi)) {
                data.add("Câu số " + (i + 1) + " - Đã trả lời");
            } else {
                data.add("Câu số " + (i + 1) + " - Chưa trả lời");
            }
        }
        return data;
    }

    private boolean isTraLoi(List<CauTraLoi> lstCauTraLoi) {
        for (int j = 0; j < lstCauTraLoi.size(); j++) {
            CauTraLoi cauTraLoi = lstCauTraLoi.get(j);
            if (cauTraLoi.isChecked())
                return true;
        }
        return false;
    }

    private boolean kiemTraCauTraLoi(List<CauTraLoi> lstCauTraLoi) {
        for (int j = 0; j < lstCauTraLoi.size(); j++) {
            CauTraLoi cauTraLoi = lstCauTraLoi.get(j);
            if (cauTraLoi.isChecked() != cauTraLoi.isDapAn())
                return false;
        }
        return true;
    }

    private void hienThiCauHoi(int flag) {
        int soCauHoi = lstCauHoi.size();

        if (flag == 1) {
            btnTruoc.setVisibility(View.GONE);
        } else if (flag == soCauHoi) {
            btnSau.setVisibility(View.GONE);
        } else {
            btnTruoc.setVisibility(View.VISIBLE);
            btnSau.setVisibility(View.VISIBLE);
        }

        cauHoi = lstCauHoi.get(flag - 1);
        toolbar.setTitle("Câu " + flag + "/" + soCauHoi);

        txtCauHoi = new TextView(BaiThiActivity.this);
        txtCauHoi.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        txtCauHoi.setTypeface(txtCauHoi.getTypeface(), Typeface.BOLD);
        txtCauHoi.setText("Câu " + flag + ": " + cauHoi.getCauHoi());

        lstHinhCauHoi = cauHoi.getLstHinhCauHoi();
        int soHinh = lstHinhCauHoi.size();
        LinearLayout layoutImage = new LinearLayout(BaiThiActivity.this);
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
        LinearLayout layoutCauTraLoi = new LinearLayout(BaiThiActivity.this);
        layoutCauTraLoi.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutCauTraLoi.setOrientation(LinearLayout.VERTICAL);
        lstCheckBoxCauTraLoi = new ArrayList<>();
        for (CauTraLoi cauTraLoi : lstCauTraLoi) {
            AppCompatCheckBox chk = new AppCompatCheckBox(BaiThiActivity.this);
            chk.setText(cauTraLoi.getCauTraLoi());
            chk.setGravity(Gravity.TOP);
            int padding = getDip(5);
            chk.setPadding(padding, padding, padding, padding);
            chk.setChecked(cauTraLoi.isChecked());
            lstCheckBoxCauTraLoi.add(chk);
            layoutCauTraLoi.addView(chk);
        }

        container.removeAllViews();
        container.addView(txtCauHoi);
        container.addView(layoutImage);
        container.addView(layoutCauTraLoi);

        addEvents();
    }

    private int getDip(int pixel) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixel, getResources().getDisplayMetrics());
    }

    private void previous() {
        if (flag - 1 > 0) {
            luuTrangThai();
            hienThiCauHoi(--flag);
        }
    }

    private void next() {
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
                next();
                return true;
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                previous();
                return true;
            }
            return false;
        }
    }

    class CountDown {

        private final static int COUNT_DOWN_INTERVAL = 1000;
        private long millisInFuture = 0;
        private long timeRemaining = 0;
        private CountDownTimer timer;

        public CountDown(int min) {
            this.millisInFuture = min * 60 * 1000 + 1000;
            txtThoiGian.setText(min + ":00");
            runTimer();
        }

        private void runTimer() {
            timer = new CountDownTimer(millisInFuture, COUNT_DOWN_INTERVAL) {
                public void onTick(long millisUntilFinished) {
                    @SuppressLint("DefaultLocale") String min = String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                    @SuppressLint("DefaultLocale") String sec = String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                    txtThoiGian.setText(min + ":" + sec);
                    timeRemaining = millisUntilFinished;
                }

                public void onFinish() {
                    tinhDiem();
                    txtThoiGian.setText("00:00");
                }
            }.start();
        }

        public void pause() {
            timer.cancel();
        }

        public void stop() {
            timer.cancel();
        }

        public void resume() {
            this.millisInFuture = this.timeRemaining;
            runTimer();
        }
    }

    class LoadDataTask extends AsyncTask<Void, Void, Void> {

        private MaterialDialog progessDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progessDialog = new MaterialDialog.Builder(BaiThiActivity.this)
                    .title("Đang tải dữ liệu...")
                    .customView(R.layout.custom_dialog_loading, true)
                    .show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progessDialog.dismiss();
            hienThiCauHoi(flag);
            txtThoiGian.setText(loaiBang.getThoiGian() + ":00");
            new MaterialDialog.Builder(BaiThiActivity.this)
                    .title("Hướng dẫn")
                    .items(R.array.huongdan)
                    .content(Html.fromHtml("Bấm <b>Bắt đầu</b> để làm bài và bấm <b>Hủy</b> sẽ quay về màn hình trước"))
                    .positiveText("Bắt đầu")
                    .neutralText("Hủy")
                    .autoDismiss(false)
                    .canceledOnTouchOutside(false)
                    .cancelable(false)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                            countDown = new CountDown(loaiBang.getThoiGian());
                        }
                    })
                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String idLoaiBang = getIntent().getExtras().getString("IDLoaiBang");
            loaiBang = new LoaiBangDB(BaiThiActivity.this).getById(idLoaiBang);
            String query = "select * from CauHoi ";
            if (loaiBang.getId() == 1)
                query += "where A1 = 1 and IdNhomCauHoi = ";
            else if (loaiBang.getId() == 2)
                query += "where A2 = 1 and IdNhomCauHoi = ";
            else if (loaiBang.getId() == 3 || loaiBang.getId() == 4)
                query += "where A3A4 = 1 and IdNhomCauHoi = ";
            else if (loaiBang.getId() == 5)
                query += "where B1 = 1 and IdNhomCauHoi = ";
            else {
                query = "select * from CauHoi where IdNhomCauHoi = ";
            }
            lstCauHoi = new ArrayList<>();
            List<NhomCauHoi> lstNhomCauHoi = new NhomCauHoiDB(BaiThiActivity.this).getAll();
            for (int i = 0; i < lstNhomCauHoi.size(); i++) {
                List<CauHoi> temp = new CauHoiDB(BaiThiActivity.this).getCauHoi(query + (i + 1));
                int soCau = new QuyTacRaDeDB(BaiThiActivity.this).getSoCau(loaiBang.getId() + "", (i + 1) + "");
                if (soCau != 0) {
                    for (int j = 1; j <= soCau; j++) {
                        CauHoi cauHoi = temp.get(new Random().nextInt(temp.size()));
                        lstCauHoi.add(cauHoi);
                    }
                }
            }
            return null;
        }
    }
}
