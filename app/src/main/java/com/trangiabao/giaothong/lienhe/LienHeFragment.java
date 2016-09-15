package com.trangiabao.giaothong.lienhe;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.trangiabao.giaothong.R;

import java.io.IOException;
import java.io.InputStream;

@SuppressLint("ValidFragment")
public class LienHeFragment extends Fragment implements View.OnClickListener {

    private final static String GMAIL = "trangiabao1203@gmail.com";
    private final static String TEL_NUMBER = "+841206836908";
    private final static String GGPLUS_PROFILE = "111378855539426176297";
    private final static String FACEBOOK_ID = "nh0kba0";

    private Context context;
    private ImageView imgGmail;
    private ImageView imgCall;
    private ImageView imgSms;
    private ImageView imgGgplus;
    private ImageView imgFacebook;
    private ImageView imgMessager;

    private final static String PATH[] = {
            "image/icon/ic_gmail.png",
            "image/icon/ic_call.png",
            "image/icon/ic_sms.png",
            "image/icon/ic_ggplus.png",
            "image/icon/ic_facebook.png",
            "image/icon/ic_messenger.png"
    };

    public LienHeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lien_he, container, false);
        this.context = getActivity();

        view.findViewById(R.id.cardGmail).setOnClickListener(this);
        view.findViewById(R.id.cardPhone).setOnClickListener(this);
        view.findViewById(R.id.cardSms).setOnClickListener(this);
        view.findViewById(R.id.cardGooglePlus).setOnClickListener(this);
        view.findViewById(R.id.cardFacebook).setOnClickListener(this);
        view.findViewById(R.id.cardMessager).setOnClickListener(this);

        imgGmail = (ImageView) view.findViewById(R.id.imgGmail);
        imgGmail.setImageDrawable(getDrawable(PATH[0]));

        imgCall = (ImageView) view.findViewById(R.id.imgCall);
        imgCall.setImageDrawable(getDrawable(PATH[1]));

        imgSms = (ImageView) view.findViewById(R.id.imgSms);
        imgSms.setImageDrawable(getDrawable(PATH[2]));

        imgGgplus = (ImageView) view.findViewById(R.id.imgGgplus);
        imgGgplus.setImageDrawable(getDrawable(PATH[3]));

        imgFacebook = (ImageView) view.findViewById(R.id.imgFacebook);
        imgFacebook.setImageDrawable(getDrawable(PATH[4]));

        imgMessager = (ImageView) view.findViewById(R.id.imgMessager);
        imgMessager.setImageDrawable(getDrawable(PATH[5]));
        return view;
    }

    private Drawable getDrawable(String path) {
        Drawable drawable = null;
        try {
            InputStream is = this.context.getAssets().open(path);
            drawable = Drawable.createFromStream(is, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        /*switch (id) {
            case R.id.cardGmail:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, GMAIL);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Tiêu đề");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Nội dung");
                emailIntent.setType("message/rfc822");
                emailIntent.setPackage("com.google.android.gm");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Chọn ứng dụng để gửi mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "Không tìm thấy ứng dụng mail trong thiết bị", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cardPhone:
                Uri uri = Uri.parse("tel:" + TEL_NUMBER);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
                //callIntent.setPackage("com.asus.dial");
                startActivity(callIntent);
                break;
            case R.id.cardSms:
                Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + TEL_NUMBER));
                smsIntent.putExtra("sms_body", "Nhập nội dung tin nhắn của bạn");
                smsIntent.setPackage("com.asus.message");
                startActivity(smsIntent);
                break;
            case R.id.cardGooglePlus:
                try {
                    Intent ggplusIntent = new Intent(Intent.ACTION_VIEW);
                    ggplusIntent.putExtra("customAppUri", GGPLUS_PROFILE);
                    ggplusIntent.setPackage("com.google.android.apps.plus");
                    startActivity(ggplusIntent);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/" + GGPLUS_PROFILE + "/posts")));
                }
                break;
            case R.id.cardFacebook:
                try {
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + FACEBOOK_ID));
                    facebookIntent.setPackage("com.facebook.katana");
                    startActivity(facebookIntent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/" + FACEBOOK_ID)));
                }
                break;
            case R.id.cardMessager:
                break;
        }*/
    }
}
