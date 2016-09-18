package com.trangiabao.giaothong.lienhe;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.trangiabao.giaothong.R;

import java.io.IOException;
import java.io.InputStream;

@SuppressLint("ValidFragment")
public class LienHeFragment extends Fragment implements View.OnClickListener {

    private final static String GMAIL = "trangiabao1203@gmail.com";
    private final static String TEL_NUMBER = "+841206836908";
    private final static String FACEBOOK_ID = "nh0kba0";
    private final static String SKYPE_ID = "nh0kba0";

    private Context context;
    private View view;
    private TextView txtAppName, txtAppVersion;
    private LinearLayout layout_mail, layout_phone, layout_facebook, layout_skype;

    public LienHeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lien_he, container, false);
        context = getActivity();
        txtAppName = (TextView) view.findViewById(R.id.txtAppName);
        txtAppVersion = (TextView) view.findViewById(R.id.txtAppVersion);
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
        String version = pInfo.versionName;

        txtAppVersion.setText("Version " + version);
        txtAppName.setText("Ứng dụng " + context.getString(R.string.app_name));

        layout_skype.setOnClickListener(this);
        layout_facebook.setOnClickListener(this);
        layout_mail.setOnClickListener(this);
        layout_phone.setOnClickListener(this);

        return view;
    }

    private Drawable getDrawable(String path) {
        Drawable drawable = null;
        try {
            InputStream is = context.getAssets().open(path);
            drawable = Drawable.createFromStream(is, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
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
}
