package com.trangiabao.giaothong.khac;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.ex.MyMethod;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class FeedbackFragment extends Fragment {

    private Context context;
    private AdView adView;
    private TextView txtInternet;
    private EditText txtTieuDe, txtNoiDung, txtEmail;
    private Button btnGui;
    private TextInputLayout txtInputTieuDe, txtInputEmail, txtInputNoiDung;

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getActiveNetworkInfo() != null) {
                txtInternet.setVisibility(View.GONE);
                txtTieuDe.setEnabled(true);
                txtNoiDung.setEnabled(true);
                txtEmail.setEnabled(true);
                btnGui.setEnabled(true);
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            } else {
                txtInternet.setVisibility(View.VISIBLE);
                txtTieuDe.setEnabled(false);
                txtNoiDung.setEnabled(false);
                txtEmail.setEnabled(false);
                btnGui.setEnabled(false);
            }
        }
    };

    public FeedbackFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        context = getActivity();

        txtInternet = (TextView) view.findViewById(R.id.txtInternet);
        txtTieuDe = (EditText) view.findViewById(R.id.txtTieuDe);
        txtInputTieuDe = (TextInputLayout) view.findViewById(R.id.txtInputTieuDe);
        txtNoiDung = (EditText) view.findViewById(R.id.txtNoiDung);
        txtInputNoiDung = (TextInputLayout) view.findViewById(R.id.txtInputNoiDung);
        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        txtInputEmail = (TextInputLayout) view.findViewById(R.id.txtInputEmail);
        btnGui = (Button) view.findViewById(R.id.btnGui);

        adView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(context.getString(R.string.test_device_id))
                .build();
        adView.loadAd(adRequest);

        addEvents();
        return view;
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

        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tieuDe = txtTieuDe.getText().toString();
                String email = txtEmail.getText().toString();
                String noiDung = txtNoiDung.getText().toString();
                boolean checkTieuDe, checkEmail, checkNoiDung;

                if (tieuDe.equals("")) {
                    txtInputTieuDe.setErrorEnabled(true);
                    txtInputTieuDe.setError("Tiêu đề không được để trống");
                    checkTieuDe = false;
                } else {
                    txtInputTieuDe.setError(null);
                    txtInputTieuDe.setErrorEnabled(false);
                    checkTieuDe = true;
                }

                if (email.equals("")) {
                    txtInputEmail.setErrorEnabled(true);
                    txtInputEmail.setError("Email không được để trống");
                    checkEmail = false;
                } else {
                    if (!MyMethod.isEmailValid(email)) {
                        txtInputEmail.setErrorEnabled(true);
                        txtInputEmail.setError("Email không chính xác");
                        checkEmail = false;
                    } else {
                        txtInputEmail.setError(null);
                        txtInputEmail.setErrorEnabled(false);
                        checkEmail = true;
                    }
                }

                if (noiDung.equals("")) {
                    txtInputNoiDung.setErrorEnabled(true);
                    txtInputNoiDung.setError("Nội dung không được để trống");
                    checkNoiDung = false;
                } else {
                    txtInputNoiDung.setError(null);
                    txtInputNoiDung.setErrorEnabled(false);
                    checkNoiDung = true;
                }

                if (checkTieuDe && checkEmail && checkNoiDung) {
                    new SenderAsyncTask().execute(tieuDe, email, noiDung);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (adView != null) {
            adView.pause();
        }
        if (receiver != null)
            context.unregisterReceiver(receiver);
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    class SenderAsyncTask extends AsyncTask<String, Void, Boolean> {

        final static String LINK = "http://sotaygiaothong.890m.com/feedback.php";
        final static String LINK_TEST = "http://sotaygiaothong.890m.com/feedback.php1111";
        private MaterialDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Drawable icon = MaterialDrawableBuilder.with(context)
                    .setIcon(MaterialDrawableBuilder.IconValue.UPLOAD)
                    .setColor(Color.parseColor("#1976D2"))
                    .build();

            dialog = new MaterialDialog.Builder(context)
                    .title("Gửi thông tin").icon(icon)
                    .content("Hệ thống đang gửi thông tin. Xin vui lòng chờ giây lát.")
                    .cancelable(false).autoDismiss(false).canceledOnTouchOutside(false)
                    .progress(true, 0)
                    .show();
        }

        @Override
        protected void onPostExecute(Boolean response) {
            super.onPostExecute(response);
            dialog.dismiss();
            String title;
            String content;
            Drawable icon;
            if (response) {
                title = "Thành công";
                content = "Thông tin đã gửi thành công. " +
                        "Chúng tôi sẽ phản hồi trong thời gian sớm nhất. " +
                        "Xin cảm ơn sự đóng góp của bạn.";
                icon = MaterialDrawableBuilder.with(context)
                        .setIcon(MaterialDrawableBuilder.IconValue.INFORMATION)
                        .setColor(Color.parseColor("#1976D2"))
                        .build();
            } else {
                title = "Thất bại";
                content = "Có lỗi xảy ra trong quá trình gửi thông tin. " +
                        "Xin vui lòng thử lại sau hoặc " +
                        "liên hệ trực tiếp với nhà phát triển trong phần liên hệ";
                icon = MaterialDrawableBuilder.with(context)
                        .setIcon(MaterialDrawableBuilder.IconValue.ALERT_CIRCLE)
                        .setColor(Color.RED)
                        .build();
            }
            new MaterialDialog.Builder(context)
                    .title(title)
                    .content(content)
                    .icon(icon)
                    .positiveText("Đóng")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                // Kết nối HTML
                URL url = new URL(LINK);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(20000);
                conn.setReadTimeout(20000);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Tạo link
                JSONObject jo = new JSONObject();
                StringBuilder packedData = new StringBuilder();
                jo.put("action", "insertDB");
                jo.put("TieuDe", params[0]);
                jo.put("Email", params[1]);
                jo.put("NoiDung", params[2]);
                Boolean firstValue = true;
                Iterator it = jo.keys();
                do {
                    String key = it.next().toString();
                    String value = jo.get(key).toString();

                    if (firstValue) {
                        firstValue = false;
                    } else {
                        packedData.append("&");
                    }
                    packedData.append(URLEncoder.encode(key, "UTF-8"));
                    packedData.append("=");
                    packedData.append(URLEncoder.encode(value, "UTF-8"));

                } while (it.hasNext());

                // ghép link
                OutputStream os = conn.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                bw.write(packedData.toString());
                Log.d("packedData", packedData.toString());
                bw.flush();
                bw.close();
                os.close();

                // kiểm tra phản hồi
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line = br.readLine();
                    while (line != null) {
                        response.append(line);
                        line = br.readLine();
                    }
                    br.close();
                    return response.toString().equals("true");
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
