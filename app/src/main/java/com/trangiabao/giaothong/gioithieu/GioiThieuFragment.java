package com.trangiabao.giaothong.gioithieu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.trangiabao.giaothong.R;

import java.io.IOException;
import java.io.InputStream;

public class GioiThieuFragment extends Fragment {

    private Context context;

    private AdView adView;
    private ImageView imgIntro1, imgIntro2, imgIntro3;
    private ImageView imgIntro4, imgIntro5, imgIntro6;
    private ImageView imgIntro7, imgIntro8;
    private ImageView imgIntro9, imgIntro10, imgIntro11;

    private static final String[] PATH = {
            "image/intro/intro1.png",
            "image/intro/intro2.png",
            "image/intro/intro3.png",
            "image/intro/intro4.png",
            "image/intro/intro5.png",
            "image/intro/intro6.png",
            "image/intro/intro7.png",
            "image/intro/intro8.png",
            "image/intro/intro9.png",
            "image/intro/intro10.png",
            "image/intro/intro11.png"
    };

    public GioiThieuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gioi_thieu, container, false);
        context = getActivity();
        imgIntro1 = (ImageView) view.findViewById(R.id.imgIntro1);
        imgIntro2 = (ImageView) view.findViewById(R.id.imgIntro2);
        imgIntro3 = (ImageView) view.findViewById(R.id.imgIntro3);
        imgIntro4 = (ImageView) view.findViewById(R.id.imgIntro4);
        imgIntro5 = (ImageView) view.findViewById(R.id.imgIntro5);
        imgIntro6 = (ImageView) view.findViewById(R.id.imgIntro6);
        imgIntro7 = (ImageView) view.findViewById(R.id.imgIntro7);
        imgIntro8 = (ImageView) view.findViewById(R.id.imgIntro8);
        //imgIntro9 = (ImageView) view.findViewById(R.id.imgIntro9);
        //imgIntro10 = (ImageView) view.findViewById(R.id.imgIntro10);
        imgIntro11 = (ImageView) view.findViewById(R.id.imgIntro11);

        imgIntro1.setImageDrawable(getDrawable(PATH[0]));
        imgIntro2.setImageDrawable(getDrawable(PATH[1]));
        imgIntro3.setImageDrawable(getDrawable(PATH[2]));
        imgIntro4.setImageDrawable(getDrawable(PATH[3]));
        imgIntro5.setImageDrawable(getDrawable(PATH[4]));
        imgIntro6.setImageDrawable(getDrawable(PATH[5]));
        imgIntro7.setImageDrawable(getDrawable(PATH[6]));
        imgIntro8.setImageDrawable(getDrawable(PATH[7]));
        //imgIntro9.setImageDrawable(getDrawable(PATH[8]));
        //imgIntro10.setImageDrawable(getDrawable(PATH[9]));
        imgIntro11.setImageDrawable(getDrawable(PATH[10]));

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

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
            adView.setVisibility(View.VISIBLE);
        } else {
            adView.setVisibility(View.GONE);
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
