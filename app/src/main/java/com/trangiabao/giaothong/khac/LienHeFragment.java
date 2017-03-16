package com.trangiabao.giaothong.khac;

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
    private final static String SKYPE_ID = "baotran1203";

    private Context context;
    private View view;
    private AdView adView;

    private String version;

    public LienHeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lien_he, container, false);
        context = getActivity();
        TextView txtAppName = (TextView) view.findViewById(R.id.txtAppName);
        TextView txtAppVersion = (TextView) view.findViewById(R.id.txtAppVersion);
        LinearLayout layout_info_app = (LinearLayout) view.findViewById(R.id.layout_info_app);
        LinearLayout layout_profile = (LinearLayout) view.findViewById(R.id.layout_profile);
        LinearLayout layout_mail = (LinearLayout) view.findViewById(R.id.layout_mail);
        LinearLayout layout_phone = (LinearLayout) view.findViewById(R.id.layout_phone);
        LinearLayout layout_facebook = (LinearLayout) view.findViewById(R.id.layout_facebook);
        LinearLayout layout_skype = (LinearLayout) view.findViewById(R.id.layout_skype);
        LinearLayout layout_source = (LinearLayout) view.findViewById(R.id.layout_source);

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
        layout_source.setOnClickListener(this);

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
                        .positiveText("Đóng")
                        .show();
                break;
            case R.id.layout_profile:
                String content2 = "Trần Gia Bảo<br>" +
                        "<em>Lập trình viên di động Android</em><br>";

                Drawable icon2 = MaterialDrawableBuilder.with(context)
                        .setIcon(MaterialDrawableBuilder.IconValue.ACCOUNT_CARD_DETAILS)
                        .setColor(Color.parseColor("#1976D2"))
                        .build();

                new MaterialDialog.Builder(context)
                        .title("Thông tin nhà phát triển")
                        .icon(icon2)
                        .content(Html.fromHtml(content2))
                        .positiveText("Đóng")
                        .show();
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

            case R.id.layout_source:
                String content3 = "Nội dung và hình ảnh được sưu tầm từ các trang<br>" +
                        "- Carly.vn - Web oto cho người Việt<br>" +
                        "- Thuvienphapluat.vn - Tra cứu, nắm bắt Pháp luật VN<br>" +
                        "...<br>" +
                        "Và một số nguồn tìm kiếm từ Google, Youtube...";

                Drawable icon3 = MaterialDrawableBuilder.with(context)
                        .setIcon(MaterialDrawableBuilder.IconValue.COPYRIGHT)
                        .setColor(Color.parseColor("#1976D2"))
                        .build();

                new MaterialDialog.Builder(context)
                        .title("Nguồn tham khảo và bản quyền")
                        .icon(icon3)
                        .content(Html.fromHtml(content3))
                        .positiveText("Đóng")
                        .show();
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
