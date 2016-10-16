package com.trangiabao.giaothong.lienhe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.trangiabao.giaothong.R;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

@SuppressLint("ValidFragment")
public class LienHeFragment extends Fragment implements View.OnClickListener {

    private final static String GMAIL = "trangiabao1203@gmail.com";
    private final static String TEL_NUMBER = "+841206836908";
    private final static String FACEBOOK_ID = "baotran1203";
    private final static String SKYPE_ID = "nh0kba0";

    private Context context;
    private View view;
    private TextView txtAppName, txtAppVersion;
    private LinearLayout layout_info_app;
    private LinearLayout layout_profile;
    private LinearLayout layout_mail;
    private LinearLayout layout_phone;
    private LinearLayout layout_facebook;
    private LinearLayout layout_skype;
    private AdView adView;

    private String version;

    public LienHeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lien_he, container, false);
        context = getActivity();
        txtAppName = (TextView) view.findViewById(R.id.txtAppName);
        txtAppVersion = (TextView) view.findViewById(R.id.txtAppVersion);
        layout_info_app = (LinearLayout) view.findViewById(R.id.layout_info_app);
        layout_profile = (LinearLayout) view.findViewById(R.id.layout_profile);
        layout_mail = (LinearLayout) view.findViewById(R.id.layout_mail);
        layout_phone = (LinearLayout) view.findViewById(R.id.layout_phone);
        layout_facebook = (LinearLayout) view.findViewById(R.id.layout_facebook);
        layout_skype = (LinearLayout) view.findViewById(R.id.layout_skype);

        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version = pInfo.versionName;

        txtAppVersion.setText("Phiên bản " + version);
        txtAppName.setText("Ứng dụng " + context.getString(R.string.app_name));

        layout_info_app.setOnClickListener(this);
        layout_profile.setOnClickListener(this);
        layout_skype.setOnClickListener(this);
        layout_facebook.setOnClickListener(this);
        layout_mail.setOnClickListener(this);
        layout_phone.setOnClickListener(this);

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
    }

    private boolean checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null) {
            Snackbar.make(view, "Kiểm tra kết nối Internet", Snackbar.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.layout_info_app:
                String content = "Phiên bản " + version + " bao gồm các tính năng:<br>" +
                        "<strong>Sát hạch lái xe</strong><br>" +
                        "- Ngân hàng câu hỏi lý thuyết<br>" +
                        "- Làm bài thi sát hạch<br>" +
                        "- Hướng dẫn thi thực hành lái xe<br>" +
                        "- Mẹo ghi nhớ<br>" +
                        "<strong>Tra cứu thông tin</strong><br>" +
                        "- Luật giao thông<br>" +
                        "- Biển báo<br>" +
                        "- Các mức phạt<br>" +
                        "- Biển số xe<br>" +
                        "- Đường dây nóng<br><br>" +
                        "<em>Mọi ý kiến đóng góp về lỗi, nội dung ứng dụng xin gửi thông tin cho nhà phát triển theo thông tin liên hệ bên dưới</em>";

                Drawable icon = MaterialDrawableBuilder.with(context)
                        .setIcon(MaterialDrawableBuilder.IconValue.INFORMATION)
                        .setColor(Color.parseColor("#1976D2"))
                        .build();

                new MaterialDialog.Builder(context)
                        .title("Thông tin ứng dụng")
                        .icon(icon)
                        .content(Html.fromHtml(content))
                        .show();
                break;
            case R.id.layout_profile:

                break;
            case R.id.layout_mail:
                if (checkInternet()) {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, GMAIL);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Tiêu đề");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Nội dung");
                    emailIntent.setType("message/rfc822");
                    try {
                        startActivity(Intent.createChooser(emailIntent, "Chọn ứng dụng để gửi mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(context, "Không tìm thấy ứng dụng mail trong thiết bị", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.layout_phone:
                Uri uri = Uri.parse("tel:" + TEL_NUMBER);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(callIntent);
                break;
            case R.id.layout_facebook:
                if (checkInternet()) {
                    try {
                        Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + FACEBOOK_ID));
                        facebookIntent.setPackage("com.facebook.katana");
                        startActivity(facebookIntent);
                    } catch (Exception e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/" + FACEBOOK_ID)));
                    }
                }
                break;
            case R.id.layout_skype:
                if (checkInternet()) {
                    Intent skypeIntent = new Intent("android.intent.action.VIEW");
                    skypeIntent.setClassName("com.skype.raider", "com.skype.raider.Main");
                    skypeIntent.setData(Uri.parse("skype:" + SKYPE_ID));
                    try {
                        startActivity(skypeIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(context, "Không tìm thấy ứng dụng Skype trong thiết bị", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
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
}
