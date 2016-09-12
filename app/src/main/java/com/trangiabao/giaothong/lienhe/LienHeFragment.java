package com.trangiabao.giaothong.lienhe;


import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trangiabao.giaothong.R;

@SuppressLint("ValidFragment")
public class LienHeFragment extends Fragment implements View.OnClickListener {

    private final static String GMAIL = "trangiabao1203@gmail.com";
    private final static String TEL_NUMBER = "+841206836908";
    private final static String GGPLUS_PROFILE = "111378855539426176297";
    private final static String FACEBOOK_ID = "nh0kba0";

    private Context context;
    private CardView cardGmail;
    private CardView cardPhone;
    private CardView cardSms;
    private CardView cardGooglePlus;
    private CardView cardFacebook;
    private CardView cardMessager;

    public LienHeFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lien_he, container, false);

        view.findViewById(R.id.cardGmail).setOnClickListener(this);
        view.findViewById(R.id.cardPhone).setOnClickListener(this);
        view.findViewById(R.id.cardSms).setOnClickListener(this);
        view.findViewById(R.id.cardGooglePlus).setOnClickListener(this);
        view.findViewById(R.id.cardFacebook).setOnClickListener(this);
        view.findViewById(R.id.cardMessager).setOnClickListener(this);

        return view;
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
