package com.trangiabao.giaothong.tracuu;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.trangiabao.giaothong.R;
import com.trangiabao.giaothong.tracuu.bienbao.NhomBienBaoActivity;
import com.trangiabao.giaothong.tracuu.biensoxe.NhomBienSoXeActivity;
import com.trangiabao.giaothong.tracuu.hotline.NhomHotLineActivity;
import com.trangiabao.giaothong.tracuu.luat.VanBanActivity;
import com.trangiabao.giaothong.tracuu.xuphat.PhuongTienActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TraCuuFragment extends Fragment {

    private Context context;

    private FastItemAdapter<TraCuuAdapter> adapter;
    private RecyclerView rvTraCuu;
    private AdView adView;

    private static final String[][] PATH = {
            {"Luật giao thông", "image/icon/ic_luat.png"},
            {"Biển báo", "image/icon/ic_bien_bao.png"},
            {"Các mức phạt", "image/icon/ic_phat.png"},
            {"Biển số xe", "image/icon/ic_bang_so_xe.png"},
            {"Đường dây nóng", "image/icon/ic_hotline.png"}
    };

    public TraCuuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tra_cuu, container, false);
        context = getActivity();
        rvTraCuu = (RecyclerView) view.findViewById(R.id.rvTraCuu);

        adapter = new FastItemAdapter<>();
        adapter.setHasStableIds(true);
        adapter.withSelectable(true);
        adapter.add(createList());

        rvTraCuu.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvTraCuu.setHasFixedSize(true);
        rvTraCuu.setAdapter(adapter);

        adView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(context.getString(R.string.test_device_id))
                .build();
        adView.loadAd(adRequest);

        addEvents();
        return view;
    }

    private List<TraCuuAdapter> createList() {
        List<TraCuuAdapter> data = new ArrayList<>();
        for (String[] path : PATH) {
            data.add(new TraCuuAdapter(path[0], getDrawable(path[1])));
        }
        return data;
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

        adapter.withOnClickListener(new FastAdapter.OnClickListener<TraCuuAdapter>() {
            @Override
            public boolean onClick(View v, IAdapter<TraCuuAdapter> adapter, TraCuuAdapter item, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(context, VanBanActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(context, NhomBienBaoActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(context, PhuongTienActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(context, NhomBienSoXeActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(context, NhomHotLineActivity.class));
                        break;
                }
                return false;
            }
        });
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
