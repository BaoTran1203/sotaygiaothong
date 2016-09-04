package com.trangiabao.giaothong.lienhe;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.trangiabao.giaothong.R;

public class LienHeActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String GMAIL = "trangiabao1203@gmail.com";
    private final static String TEL_NUMBER = "+841206836908";
    private final static String GGPLUS_PROFILE = "111378855539426176297";
    private final static String FACEBOOK_ID = "nh0kba0";

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);

        addControls();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.layout_gmail).setOnClickListener(this);
        findViewById(R.id.layout_call).setOnClickListener(this);
        findViewById(R.id.layout_sms).setOnClickListener(this);
        findViewById(R.id.layout_ggplus).setOnClickListener(this);
        findViewById(R.id.layout_facebook).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_gmail:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, GMAIL);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Tiêu đề");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Nội dung");
                emailIntent.setType("message/rfc822");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Chọn ứng dụng để gửi mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(LienHeActivity.this, "Không tìm thấy ứng dụng mail trong thiết bị", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.layout_call:
                Uri uri = Uri.parse("tel:" + TEL_NUMBER);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(callIntent);
                break;
            case R.id.layout_sms:
                Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + TEL_NUMBER));
                smsIntent.putExtra("sms_body", "Nhập nội dung tin nhắn của bạn");
                startActivity(smsIntent);
                break;
            case R.id.layout_ggplus:
                try {
                    Intent ggplusIntent = new Intent(Intent.ACTION_VIEW);
                    ggplusIntent.setClassName("com.google.android.apps.plus",
                            "com.google.android.apps.plus.phone.UrlGatewayActivity");
                    ggplusIntent.putExtra("customAppUri", GGPLUS_PROFILE);
                    startActivity(ggplusIntent);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/" + GGPLUS_PROFILE + "/posts")));
                }
                break;
            case R.id.layout_facebook:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + FACEBOOK_ID));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/" + FACEBOOK_ID)));
                }
                break;
        }
    }
}
