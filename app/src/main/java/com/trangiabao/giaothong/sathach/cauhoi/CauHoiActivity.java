package com.trangiabao.giaothong.sathach.cauhoi;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
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
import com.trangiabao.giaothong.sathach.cauhoi.db.CauHoiDB;
import com.trangiabao.giaothong.sathach.cauhoi.model.CauHoi;
import com.trangiabao.giaothong.sathach.cauhoi.model.CauTraLoi;
import com.trangiabao.giaothong.sathach.cauhoi.model.HinhCauHoi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CauHoiActivity extends AppCompatActivity {

    private Context context = CauHoiActivity.this;

    private GestureDetector gestureDetector;
    private Toolbar toolbar;
    private ViewGroup container;
    private TextSwitcher txtGiaiThich;
    private Button btnDapAn, btnTruoc, btnSau;

    private int index = 1;
    private List<CauHoi> lstCauHoi = new ArrayList<>();
    private CauHoi cauHoi;
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
        cauHoi = lstCauHoi.get(flag - 1);

        if (flag == 1) {
            btnTruoc.setVisibility(View.GONE);
        } else if (flag == soCauHoi) {
            btnSau.setVisibility(View.GONE);
        } else {
            btnTruoc.setVisibility(View.VISIBLE);
            btnSau.setVisibility(View.VISIBLE);
        }
        btnDapAn.setVisibility(View.VISIBLE);

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
            int padding = getDip(5);
            chk.setPadding(padding, padding, padding, padding);
            lstCheckBoxCauTraLoi.add(chk);
            layoutCauTraLoi.addView(chk);
        }

        txtGiaiThich = new TextSwitcher(context);
        txtGiaiThich.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        txtGiaiThich.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new TextView(context);
            }
        });

        container.removeAllViews();
        container.addView(txtCauHoi);
        container.addView(layoutImage);
        container.addView(layoutCauTraLoi);
        container.addView(btnDapAn);
        container.addView(txtGiaiThich);

        addEvents();
    }

    private void addEvents() {
        btnDapAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        for (int i = 0; i < lstCheckBoxCauTraLoi.size(); i++) {
            boolean isChecked = lstCheckBoxCauTraLoi.get(i).isChecked();
            cauHoi.getLstCauTraLoi().get(i).setChecked(isChecked);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setQueryHint("Nhập số thứ tự câu hỏi. Vd: 1,2,10...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int stt = Integer.parseInt(query);
                if (stt >= 1 && stt <= lstCauHoi.size()) {
                    index = stt;
                    hienThiCauHoi(index);
                } else {
                    new MaterialDialog.Builder(context)
                            .title("Không hợp lệ")
                            .content("Dữ liệu bạn nhập không hợp lệ. Vui lòng nhập lại")
                            .positiveText("Đóng")
                            .show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
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
            dialog = new MaterialDialog.Builder(context)
                    .title("Đang tải dữ liệu...")
                    .customView(R.layout.custom_dialog_loading, true)
                    .show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            hienThiCauHoi(index);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String query = getIntent().getExtras().getString("QUERY");
            lstCauHoi = new CauHoiDB(context).getCauHoi(query);
            return null;
        }
    }
}