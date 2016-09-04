package com.trangiabao.giaothong.gioithieu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.trangiabao.giaothong.MainActivity;
import com.trangiabao.giaothong.R;

public class GioiThieu2Activity extends AppIntro2 {

    @Override
    public void init(Bundle savedInstanceState) {

        addSlide(AppIntroFragment.newInstance(
                "Giới thiệu",
                "Ứng dụng sổ tay giao thông đường bộ Việt Nam",
                R.drawable.banner,
                Color.parseColor("#FF0000")));

        addSlide(AppIntroFragment.newInstance(
                "Sát hạch",
                "Ôn tập các câu hỏi\nLàm bài thi thử",
                R.drawable.banner,
                Color.parseColor("#FFC600")));

        addSlide(AppIntroFragment.newInstance(
                "Tra cứu thông tin",
                "Tra cứu về luật giao thông đường bộ, các nghị định khác\nTra cứu biển báo giao thông",
                R.drawable.banner,
                Color.parseColor("#0011A4")));

        addSlide(AppIntroFragment.newInstance(
                "Các mục khác",
                "Các thông tin khác về giao thông đường bộ",
                R.drawable.banner,
                Color.parseColor("#0FAD00")));

        showStatusBar(false);
        setCustomTransformer(new ZoomOutPageTransformer());
        setFlowAnimation();
    }

    private void loadMainActivity() {
        Intent intent = new Intent(GioiThieu2Activity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed() {
        loadMainActivity();
    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();
            if (position < -1) {
                view.setAlpha(0);
            } else if (position <= 1) {
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            } else {
                view.setAlpha(0);
            }
        }
    }
}
