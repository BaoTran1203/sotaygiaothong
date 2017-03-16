package com.trangiabao.giaothong.sathach.cauhoi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.ex.MyMethod;
import com.trangiabao.giaothong.sathach.cauhoi.db.CauHoiDB;
import com.trangiabao.giaothong.sathach.cauhoi.db.LoaiBangDB;
import com.trangiabao.giaothong.sathach.cauhoi.db.NhomCauHoiDB;
import com.trangiabao.giaothong.sathach.cauhoi.db.QuyTacRaDeDB;
import com.trangiabao.giaothong.sathach.cauhoi.model.CauHoi;
import com.trangiabao.giaothong.sathach.cauhoi.model.CauTraLoi;
import com.trangiabao.giaothong.sathach.cauhoi.model.HinhCauHoi;
import com.trangiabao.giaothong.sathach.cauhoi.model.LoaiBang;
import com.trangiabao.giaothong.sathach.cauhoi.model.NhomCauHoi;
import com.trangiabao.giaothong.sathach.cauhoi.model.QuyTacRaDe;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BaiThiActivity extends AppCompatActivity {

    // controls
    private Context context = BaiThiActivity.this;
    private GestureDetector gestureDetector;
    private Toolbar toolbar;
    private ViewGroup container;
    private TextView txtThoiGian;
    private Button btnTruoc, btnSau, btnKetThuc;
    private MaterialIconView imgTamDung, imgMucLuc;
    private CountDown countDown;
    private AdView adView;

    // datas
    private int index = 1;
    private boolean isFisinshed = false;
    private List<CauHoi> lstCauHoi;
    private CauHoi cauHoi;
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
        btnKetThuc = (Button) findViewById(R.id.btnKetThuc);
        imgMucLuc = (MaterialIconView) findViewById(R.id.imgMucLuc);
        imgTamDung = (MaterialIconView) findViewById(R.id.imgTamDung);
        txtThoiGian = (TextView) findViewById(R.id.txtThoiGian);

        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(context.getString(R.string.test_device_id))
                .build();
        adView.loadAd(adRequest);
    }

    private void hienThiCauHoi(int flag) {
        int soCauHoi = lstCauHoi.size();
        cauHoi = lstCauHoi.get(flag - 1);

        if (flag == 1) {
            btnTruoc.setVisibility(View.GONE);
            btnSau.setVisibility(View.VISIBLE);
        } else if (flag == soCauHoi) {
            btnTruoc.setVisibility(View.VISIBLE);
            btnSau.setVisibility(View.GONE);
        } else {
            btnTruoc.setVisibility(View.VISIBLE);
            btnSau.setVisibility(View.VISIBLE);
        }

        toolbar.setTitle("Câu " + flag + "/" + soCauHoi);
        TextView txtCauHoi = new TextView(context);
        txtCauHoi.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        txtCauHoi.setTypeface(txtCauHoi.getTypeface(), Typeface.BOLD);
        txtCauHoi.setText("Câu " + flag + ": " + cauHoi.getCauHoi());

        List<HinhCauHoi> lstHinhCauHoi = cauHoi.getLstHinhCauHoi();
        int soHinh = lstHinhCauHoi.size();
        LinearLayout layoutImage = new LinearLayout(context);
        layoutImage.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutImage.setOrientation(LinearLayout.HORIZONTAL);
        layoutImage.setWeightSum(soHinh);
        int width = soHinh == 1 ? LinearLayout.LayoutParams.MATCH_PARENT : MyMethod.getDip(context, 100);
        int height = soHinh == 1 ? MyMethod.getDip(context, 200) : MyMethod.getDip(context, 100);
        int margin = MyMethod.getDip(context, 5);
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

        List<CauTraLoi> lstCauTraLoi = cauHoi.getLstCauTraLoi();
        LinearLayout layoutCauTraLoi = new LinearLayout(context);
        layoutCauTraLoi.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        layoutCauTraLoi.setOrientation(LinearLayout.VERTICAL);
        lstCheckBoxCauTraLoi = new ArrayList<>();
        for (CauTraLoi cauTraLoi : lstCauTraLoi) {
            AppCompatCheckBox chk = new AppCompatCheckBox(context);
            chk.setText(cauTraLoi.getCauTraLoi());
            chk.setGravity(Gravity.TOP);
            int padding = MyMethod.getDip(context, 5);
            chk.setPadding(padding, padding, padding, padding);
            chk.setChecked(cauTraLoi.isChecked());
            if (isFisinshed) {
                if (cauTraLoi.isDapAn()) {
                    chk.setTextColor(Color.BLUE);
                    chk.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    chk.setPaintFlags(chk.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                chk.setEnabled(false);
            }
            lstCheckBoxCauTraLoi.add(chk);
            layoutCauTraLoi.addView(chk);
        }

        container.removeAllViews();
        container.addView(txtCauHoi);
        container.addView(layoutImage);
        container.addView(layoutCauTraLoi);

        addEvents();
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

        btnKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFisinshed) {
                    ketThuc();
                }
            }
        });

        imgMucLuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                luuTrangThai();

                FastItemAdapter<CauHoi> adapter = new FastItemAdapter<>();
                adapter.add(lstCauHoi);

                Drawable icon = MaterialDrawableBuilder.with(context)
                        .setIcon(MaterialDrawableBuilder.IconValue.VIEW_LIST)
                        .setColor(Color.parseColor("#1976D2"))
                        .build();

                MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
                builder.title("Danh sách câu hỏi");
                builder.icon(icon);
                builder.adapter(adapter, new GridLayoutManager(context, 4));
                if (!isFisinshed) {
                    builder.positiveText("Kết thúc bài thi");
                    builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (!isFisinshed) {
                                countDown.stop();
                                dialog.dismiss();
                                ketThuc();
                            }
                        }
                    });
                }

                final MaterialDialog dialog = builder.build();
                dialog.show();

                adapter.withOnClickListener(new FastAdapter.OnClickListener<CauHoi>() {
                    @Override
                    public boolean onClick(View v, IAdapter<CauHoi> adapter, CauHoi item, int position) {
                        index = position + 1;
                        hienThiCauHoi(index);
                        dialog.dismiss();
                        return false;
                    }
                });
            }
        });

        imgTamDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDown.pause();
                Drawable icon = MaterialDrawableBuilder.with(context)
                        .setIcon(MaterialDrawableBuilder.IconValue.PAUSE)
                        .setColor(Color.BLACK)
                        .build();

                new MaterialDialog.Builder(context)
                        .title("Tạm dừng")
                        .icon(icon)
                        .customView(R.layout.custom_dialog_pause, true)
                        .autoDismiss(false).cancelable(false).canceledOnTouchOutside(false)
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

    private void truoc() {
        if (index - 1 > 0) {
            luuTrangThai();
            hienThiCauHoi(--index);
        }
    }

    private void sau() {
        if (index + 1 <= lstCauHoi.size()) {
            luuTrangThai();
            hienThiCauHoi(++index);
        }
    }

    private void luuTrangThai() {
        for (int i = 0; i < lstCheckBoxCauTraLoi.size(); i++) {
            boolean isChecked = lstCheckBoxCauTraLoi.get(i).isChecked();
            cauHoi.getLstCauTraLoi().get(i).setChecked(isChecked);
        }

        boolean check = false;
        for (int i = 0; i < lstCheckBoxCauTraLoi.size(); i++) {
            if (lstCheckBoxCauTraLoi.get(i).isChecked())
                check = true;
        }
        cauHoi.setTraLoi(check);
    }

    private void ketThuc() {
        Drawable icon = MaterialDrawableBuilder.with(context)
                .setIcon(MaterialDrawableBuilder.IconValue.CHECK)
                .setColor(Color.parseColor("#1976D2"))
                .build();

        new MaterialDialog.Builder(context)
                .title("Kết thúc").icon(icon)
                .content("Khi kết thúc bạn sẽ không thể tiếp tục làm bài")
                .autoDismiss(false).cancelable(false).canceledOnTouchOutside(false)
                .positiveText("Tính điểm")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        tinhDiem();
                        dialog.dismiss();
                    }
                })
                .neutralText("Hủy")
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void tinhDiem() {
        countDown.stop();
        isFisinshed = true;
        imgTamDung.setEnabled(false);
        btnKetThuc.setVisibility(View.GONE);
        int soCauDung = 0;
        int soCauChuaTraLoi = 0;
        for (CauHoi cauHoi : lstCauHoi) {
            if (!cauHoi.isTraLoi()) {
                soCauChuaTraLoi++;
            } else {
                List<CauTraLoi> lstCauTraLoi = cauHoi.getLstCauTraLoi();
                boolean isCorrect = true;
                for (CauTraLoi cauTraLoi : lstCauTraLoi) {
                    if (cauTraLoi.isChecked() != cauTraLoi.isDapAn()) {
                        isCorrect = false;
                    }
                }
                if (isCorrect)
                    soCauDung++;
            }
        }

        String ketQua = "Số câu đúng: " + soCauDung +
                "\nChưa trả lời: " + soCauChuaTraLoi +
                "\nSố câu sai: " + (lstCauHoi.size() - soCauDung - soCauChuaTraLoi) +
                "\nKết quả: ";
        if (soCauDung >= loaiBang.getSoCauDatYeuCau())
            ketQua += "Đạt";
        else
            ketQua += "Chưa đạt";

        Drawable icon = MaterialDrawableBuilder.with(context)
                .setIcon(MaterialDrawableBuilder.IconValue.TROPHY)
                .setColor(Color.parseColor("#1976D2"))
                .build();

        new MaterialDialog.Builder(context)
                .title("Kết quả").icon(icon)
                .content(ketQua)
                .autoDismiss(false).cancelable(false).canceledOnTouchOutside(false)
                .positiveText("Quay về trang trước")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .neutralText("Xem lại bài thi")
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        index = 1;
                        hienThiCauHoi(index);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void troVeTrangTruoc() {
        if (isFisinshed)
            finish();
        else {
            Drawable icon = MaterialDrawableBuilder.with(context)
                    .setIcon(MaterialDrawableBuilder.IconValue.ALERT)
                    .setColor(Color.parseColor("#eeee00"))
                    .build();

            new MaterialDialog.Builder(context)
                    .title("Quay về trang trước").icon(icon)
                    .content("Nếu quay về trang trước bài thi sẽ hủy. Bạn có chắc chắn ?")
                    .positiveText("Đồng ý")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            finish();
                        }
                    })
                    .neutralText("Hủy")
                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
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
    public void onBackPressed() {
        troVeTrangTruoc();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            troVeTrangTruoc();
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

    class CountDown {

        private final static int COUNT_DOWN_INTERVAL = 1000;
        private long millisInFuture = 0;
        private long timeRemaining = 0;
        private CountDownTimer timer;

        CountDown(int min) {
            this.millisInFuture = min * 60 * 1000 + 1000;
            txtThoiGian.setText(min + ":00");
            runTimer();
        }

        private void runTimer() {
            timer = new CountDownTimer(millisInFuture, COUNT_DOWN_INTERVAL) {
                public void onTick(long millisUntilFinished) {
                    @SuppressLint("DefaultLocale")
                    String min = String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                    @SuppressLint("DefaultLocale")
                    String sec = String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
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

        void pause() {
            timer.cancel();
        }

        void stop() {
            timer.cancel();
        }

        void resume() {
            this.millisInFuture = this.timeRemaining;
            runTimer();
        }
    }

    class LoadDataTask extends AsyncTask<Void, Void, Void> {

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
            txtThoiGian.setText(loaiBang.getThoiGian() + ":00");
            Drawable icon = MaterialDrawableBuilder.with(context)
                    .setIcon(MaterialDrawableBuilder.IconValue.INFORMATION)
                    .setColor(Color.parseColor("#1976D2"))
                    .build();

            new MaterialDialog.Builder(context)
                    .title("Hướng dẫn").icon(icon)
                    .content(Html.fromHtml("- Bấm <b>Bắt đầu</b> để làm bài<br>" +
                            "- Bấm <b>Hủy</b> để quay về trang trước"))
                    .positiveText("Bắt đầu")
                    .neutralText("Hủy")
                    .autoDismiss(false).cancelable(false).canceledOnTouchOutside(false)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            hienThiCauHoi(index);
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
            loaiBang = new LoaiBangDB(context).getById(idLoaiBang);
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
            List<NhomCauHoi> lstNhomCauHoi = new NhomCauHoiDB(context).getAll();
            List<QuyTacRaDe> lstQuyTac = new QuyTacRaDeDB(context).getByIdLoaiBang2(loaiBang.getId() + "");
            int stt = 1;
            for (int i = 0; i < lstNhomCauHoi.size(); i++) {
                int soCau = lstQuyTac.get(i).getSoCauThi();
                String newQuery = query + (i + 1) + " ORDER BY RANDOM() LIMIT " + soCau;
                List<CauHoi> lstTemp = new CauHoiDB(context).getCauHoi(newQuery);
                for (CauHoi cauHoi : lstTemp) {
                    cauHoi.setStt(stt);
                    lstCauHoi.add(cauHoi);
                    stt++;
                }
            }
            return null;
        }
    }
}
